package ro.hiringsystem.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.InterviewConferenceRoomMapper;
import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantDto;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;
import ro.hiringsystem.repository.InterviewConferenceRoomRepository;
import ro.hiringsystem.service.InterviewConferenceRoomService;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewConferenceRoomServiceImpl implements InterviewConferenceRoomService {
    private final InterviewConferenceRoomRepository interviewConferenceRoomRepository;
    private final InterviewConferenceRoomMapper interviewConferenceRoomMapper;
    private final EntityManager entityManager;

    @Override
    public InterviewConferenceRoomDto getById(UUID id) {
        return interviewConferenceRoomMapper.toDto(interviewConferenceRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview conference room not found!")));
    }

    @Override
    public InterviewConferenceRoomDto getByIdFullyLoaded(UUID id) {
        InterviewConferenceRoom room = interviewConferenceRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview conference room not found!"));

        //for(InterviewParticipant participant : room.getParticipants())
        //    participant.getUser(); //lazy loading

        return interviewConferenceRoomMapper.toDtoFullyLoaded(room);
    }

    @Override
    public InterviewConferenceRoomDto create(InterviewConferenceRoomDto interviewConferenceRoomDto) {
        interviewConferenceRoomDto.setCreationDate(LocalDateTime.now());

        if(interviewConferenceRoomDto.getId()==null)
            interviewConferenceRoomDto.setId(UUID.randomUUID());

        for(InterviewParticipantDto participantDto : interviewConferenceRoomDto.getParticipants()){
            participantDto.setInterviewRoomId(interviewConferenceRoomDto.getId());
        }

        InterviewConferenceRoom roomEntity = interviewConferenceRoomMapper.toEntity(interviewConferenceRoomDto);
        interviewConferenceRoomRepository.save(roomEntity);
        return interviewConferenceRoomMapper.toDto(roomEntity);
    }

    @Override
    public void saveElement(InterviewConferenceRoomDto interviewConferenceRoomDto) {
        InterviewConferenceRoom roomEntity = interviewConferenceRoomMapper.toEntity(interviewConferenceRoomDto);
        interviewConferenceRoomRepository.save(roomEntity);
    }

    @Override
    public boolean deleteById(UUID id) {
        if(interviewConferenceRoomRepository.findById(id).isEmpty())
            return false;

        interviewConferenceRoomRepository.deleteById(id);
        return true;
    }
}
