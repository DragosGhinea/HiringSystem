package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.hiringsystem.mapper.InterviewConferenceRoomMapper;
import ro.hiringsystem.mapper.InterviewParticipantMapper;
import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;
import ro.hiringsystem.model.entity.interview.InterviewParticipantId;
import ro.hiringsystem.repository.InterviewConferenceRoomRepository;
import ro.hiringsystem.repository.InterviewParticipantRepository;
import ro.hiringsystem.service.InterviewConferenceRoomService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterviewConferenceRoomServiceImpl implements InterviewConferenceRoomService {
    private final InterviewConferenceRoomRepository interviewConferenceRoomRepository;
    private final InterviewParticipantRepository interviewParticipantRepository;
    private final InterviewConferenceRoomMapper interviewConferenceRoomMapper;
    private final InterviewParticipantMapper interviewParticipantMapper;

    /**
     * Cleans up old interview conference rooms.
     * Removes the conference rooms whose start date is before the current date minus one day.
     */
    @Override
    public void cleanupOldRooms() {
        interviewConferenceRoomRepository.deleteByStartDateBefore(LocalDateTime.now().minusDays(1));
    }

    /**
     * Retrieves the extra user information for a participant in an interview conference room.
     *
     * @param roomId the ID of the interview conference room
     * @param userId the ID of the user/participant
     * @return the extra user information DTO
     */
    @Override
    @Transactional
    public InterviewParticipantExtraUserInfoDto getParticipantInfo(UUID roomId, UUID userId) {
        try {
            InterviewParticipant interviewParticipant = interviewParticipantRepository.getReferenceById(new InterviewParticipantId(userId, roomId));
            interviewParticipant.getUser(); //lazy loading
            InterviewParticipantExtraUserInfoDto interviewParticipantExtraUserInfoDto = interviewParticipantMapper.toDtoExtraUserInfo(interviewParticipant);
            return interviewParticipantExtraUserInfoDto;
        } catch (NullPointerException x) {
            x.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieves an interview conference room by its ID.
     *
     * @param id the ID of the interview conference room
     * @return the interview conference room DTO
     * @throws RuntimeException if the interview conference room is not found
     */
    @Override
    public InterviewConferenceRoomDto getById(UUID id) {
        return interviewConferenceRoomMapper.toDto(interviewConferenceRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview conference room not found!")));
    }

    /**
     * Retrieves an interview conference room by its ID with all its participants fully loaded.
     *
     * @param id the ID of the interview conference room
     * @return the interview conference room DTO with fully loaded participants
     * @throws RuntimeException if the interview conference room is not found
     */
    @Override
    public InterviewConferenceRoomDto getByIdFullyLoaded(UUID id) {
        InterviewConferenceRoom room = interviewConferenceRoomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview conference room not found!"));

        //for(InterviewParticipant participant : room.getParticipants())
        //    participant.getUser(); //lazy loading

        return interviewConferenceRoomMapper.toDtoFullyLoaded(room);
    }

    /**
     * Creates a new interview conference room.
     *
     * @param interviewConferenceRoomDto the interview conference room DTO to create
     * @return the created interview conference room DTO
     */
    @Override
    public InterviewConferenceRoomDto create(InterviewConferenceRoomDto interviewConferenceRoomDto) {
        interviewConferenceRoomDto.setCreationDate(LocalDateTime.now());

        if (interviewConferenceRoomDto.getId() == null)
            interviewConferenceRoomDto.setId(UUID.randomUUID());

        for (InterviewParticipantDto participantDto : interviewConferenceRoomDto.getParticipants()) {
            participantDto.setInterviewRoomId(interviewConferenceRoomDto.getId());
        }

        InterviewConferenceRoom roomEntity = interviewConferenceRoomMapper.toEntity(interviewConferenceRoomDto);
        interviewConferenceRoomRepository.save(roomEntity);
        return interviewConferenceRoomMapper.toDto(roomEntity);
    }

    /**
     * Saves an interview conference room element.
     *
     * @param interviewConferenceRoomDto the interview conference room DTO to save
     */
    @Override
    public void saveElement(InterviewConferenceRoomDto interviewConferenceRoomDto) {
        InterviewConferenceRoom roomEntity = interviewConferenceRoomMapper.toEntity(interviewConferenceRoomDto);
        interviewConferenceRoomRepository.save(roomEntity);
    }

    /**
     * Deletes an interview conference room by its ID.
     *
     * @param id the ID of the interview conference room
     * @return true if the interview conference room is deleted successfully, false otherwise
     */
    @Override
    public boolean deleteById(UUID id) {
        if (interviewConferenceRoomRepository.findById(id).isEmpty())
            return false;

        interviewConferenceRoomRepository.deleteById(id);
        return true;
    }

    /**
     * Retrieves a list of interview conference rooms associated with a specific user.
     *
     * @param userId the ID of the user
     * @return a list of interview conference room DTOs
     */
    @Override
    public List<InterviewConferenceRoomDto> getAllByUserId(UUID userId) {
        List<InterviewConferenceRoom> rooms = interviewConferenceRoomRepository.getAllByUserId(userId);
        List<InterviewConferenceRoomDto> toReturn = new ArrayList<>();
        for (InterviewConferenceRoom room : rooms)
            toReturn.add(interviewConferenceRoomMapper.toDtoFullyLoaded(room));
        return toReturn;
    }

    /**
     * Retrieves a list of all interview conference rooms.
     *
     * @return a list of interview conference room DTOs
     */
    @Override
    public List<InterviewConferenceRoomDto> getAll() {
        return interviewConferenceRoomRepository.findAll().stream()
                .map(interviewConferenceRoomMapper::toDto).toList();
    }
}
