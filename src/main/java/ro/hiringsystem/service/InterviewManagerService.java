package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;

import java.util.Map;
import java.util.UUID;

public interface InterviewManagerService {

    Map<UUID, InterviewParticipantExtraUserInfoDto> getConnectedInterviewParticipants(UUID roomId);

    /**
     * Retrieves the time until the room becomes available.
     *
     * @param roomId The unique identifier of the room.
     * @return The time until the room becomes available. Returns negative values if the room is available.
     *         Returns null if the room was not found (it either doesn't exist or has ended).
     */
    Long untilRoomAvailable(UUID roomId);

    //returns the participant that was connected to the room
    //returns null if the room is not available or the user is not allowed to connect
    //returns null also if user is already connected to a room
    InterviewParticipantExtraUserInfoDto tryConnectToInterviewRoom(UUID roomId, UUID userId);

    Boolean leaveInterviewRoom(UUID roomId, UUID userId);

    UUID getConnectedRoomId(UUID userId);

    Boolean closeInterviewRoom(UUID roomId);

    void clearCacheRoom(UUID roomId);
}
