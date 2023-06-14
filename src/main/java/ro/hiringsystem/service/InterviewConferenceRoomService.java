package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;

import java.util.List;
import java.util.UUID;

public interface InterviewConferenceRoomService {

    InterviewConferenceRoomDto getById(UUID id);

    InterviewConferenceRoomDto getByIdFullyLoaded(UUID id);

    InterviewConferenceRoomDto create(InterviewConferenceRoomDto interviewConferenceRoomDto);

    InterviewParticipantExtraUserInfoDto getParticipantInfo(UUID roomId, UUID userId);

    void saveElement(InterviewConferenceRoomDto interviewConferenceRoomDto);

    boolean deleteById(UUID id);

    void cleanupOldRooms();

    List<InterviewConferenceRoomDto> getAllByUserId(UUID userId);

    List<InterviewConferenceRoomDto> getAll();
}
