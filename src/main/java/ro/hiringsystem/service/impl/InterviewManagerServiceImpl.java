package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;
import ro.hiringsystem.service.InterviewConferenceRoomService;
import ro.hiringsystem.service.InterviewManagerService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class InterviewManagerServiceImpl implements InterviewManagerService {
    private final Map<UUID, Map<UUID, InterviewParticipantExtraUserInfoDto>> users = new ConcurrentHashMap<>();
    private final Map<UUID, UUID> usersToRooms = new ConcurrentHashMap<>();
    private final Map<UUID, InterviewConferenceRoomDto> rooms = new ConcurrentHashMap<>();

    private final InterviewConferenceRoomService interviewConferenceRoomService;

    @Override
    public Map<UUID, InterviewParticipantExtraUserInfoDto> getConnectedInterviewParticipants(UUID roomId) {
        return users.getOrDefault(roomId, null);
    }

    @Override
    public Long untilRoomAvailable(UUID roomId) {
        InterviewConferenceRoomDto interviewConferenceRoomDto = rooms.getOrDefault(roomId, null);
        if(interviewConferenceRoomDto==null) {
            interviewConferenceRoomDto = interviewConferenceRoomService.getById(roomId);
            if (interviewConferenceRoomDto == null)
                return null;
            rooms.put(roomId, interviewConferenceRoomDto);
        }

        return interviewConferenceRoomDto.getStartDate().atZone(ZoneId.systemDefault()).toEpochSecond() - LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    @Override
    public InterviewParticipantExtraUserInfoDto tryConnectToInterviewRoom(UUID roomId, UUID userId) {
        InterviewParticipantExtraUserInfoDto user = interviewConferenceRoomService.getParticipantInfo(roomId, userId);
        if(user==null)
            return null;
        usersToRooms.put(userId, roomId);

        users.compute(roomId, (key, value) -> {
            if(value == null){
                Map<UUID, InterviewParticipantExtraUserInfoDto> roomUsers = new HashMap<>();
                roomUsers.put(userId, user);
                return roomUsers;
            }
            else{
                value.put(userId, user);
                return value;
            }
        });
        return user;
    }

    @Override
    public Boolean leaveInterviewRoom(UUID roomId, UUID userId) {
        usersToRooms.remove(userId);
        users.compute(roomId, (key, value) -> {
            if(value == null)
                return null;
            else{
                value.remove(userId);
                return value;
            }
        });
        return true;
    }

    @Override
    public UUID getConnectedRoomId(UUID userId) {
        return usersToRooms.getOrDefault(userId, null);
    }

    @Override
    public Boolean closeInterviewRoom(UUID roomId) {
        rooms.remove(roomId);
        users.remove(roomId);
        usersToRooms.entrySet().removeIf(entry -> entry.getValue().equals(roomId));
        return true;
    }
}
