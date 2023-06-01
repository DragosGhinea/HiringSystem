package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;

import java.util.UUID;

public interface InterviewConferenceRoomService {

    InterviewConferenceRoomDto getById(UUID id);

    InterviewConferenceRoomDto getByIdFullyLoaded(UUID id);

    InterviewConferenceRoomDto create(InterviewConferenceRoomDto interviewConferenceRoomDto);

    void saveElement(InterviewConferenceRoomDto interviewConferenceRoomDto);

    boolean deleteById(UUID id);
}
