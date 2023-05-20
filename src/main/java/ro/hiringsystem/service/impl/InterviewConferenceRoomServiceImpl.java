package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.InterviewConferenceRoomMapper;
import ro.hiringsystem.model.dto.InterviewConferenceRoomDto;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;
import ro.hiringsystem.repository.InterviewConferenceRoomRepository;
import ro.hiringsystem.service.InterviewConferenceRoomService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewConferenceRoomServiceImpl implements InterviewConferenceRoomService {
    private final InterviewConferenceRoomRepository interviewConferenceRoomRepository;
    private final InterviewConferenceRoomMapper interviewConferenceRoomMapper;

    @Override
    public InterviewConferenceRoomDto getById(UUID id) {
        return interviewConferenceRoomMapper.toDto(interviewConferenceRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview conference room not found!")));
    }

    @Override
    public InterviewConferenceRoomDto create(InterviewConferenceRoomDto interviewConferenceRoomDto) {
        if(interviewConferenceRoomDto.getId()==null)
            interviewConferenceRoomDto.setId(UUID.randomUUID());

        InterviewConferenceRoom roomEntity = interviewConferenceRoomMapper.toEntity(interviewConferenceRoomDto);
        interviewConferenceRoomRepository.save(roomEntity);
        return interviewConferenceRoomMapper.toDto(roomEntity);
    }
}
