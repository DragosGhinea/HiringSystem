package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.InterviewConferenceRoomDto;

import java.util.UUID;

public interface InterviewConferenceRoomService {

    InterviewConferenceRoomDto getById(UUID id);

    InterviewConferenceRoomDto create(InterviewConferenceRoomDto interviewConferenceRoomDto);
}
