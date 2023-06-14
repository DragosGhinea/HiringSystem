package ro.hiringsystem.HiringSystem.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.hiringsystem.mapper.InterviewConferenceRoomMapper;
import ro.hiringsystem.mapper.InterviewParticipantMapper;
import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;
import ro.hiringsystem.model.entity.CandidateUser;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;
import ro.hiringsystem.model.entity.interview.InterviewParticipantId;
import ro.hiringsystem.repository.InterviewConferenceRoomRepository;
import ro.hiringsystem.repository.InterviewParticipantRepository;
import ro.hiringsystem.service.impl.InterviewConferenceRoomServiceImpl;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Interview Conference Room Service Tests")
public class InterviewConferenceRoomServiceImplTest {

    @Mock
    private InterviewConferenceRoomRepository interviewConferenceRoomRepository;

    @Mock
    private InterviewParticipantRepository interviewParticipantRepository;

    @Mock
    private InterviewConferenceRoomMapper interviewConferenceRoomMapper;

    @Mock
    private InterviewParticipantMapper interviewParticipantMapper;

    @InjectMocks
    private InterviewConferenceRoomServiceImpl interviewConferenceRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test getParticipantInfo should return participant info")
    void testGetParticipantInfo_ReturnsParticipantInfo() {
        UUID roomId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        InterviewParticipantId participantId = new InterviewParticipantId(userId, roomId);
        InterviewParticipant interviewParticipant = new InterviewParticipant();
        interviewParticipant.setUser(new CandidateUser());
        InterviewParticipantExtraUserInfoDto expectedInfoDto = new InterviewParticipantExtraUserInfoDto();

        when(interviewParticipantRepository.getReferenceById(participantId)).thenReturn(interviewParticipant);
        when(interviewParticipantMapper.toDtoExtraUserInfo(interviewParticipant)).thenReturn(expectedInfoDto);

        InterviewParticipantExtraUserInfoDto result = interviewConferenceRoomService.getParticipantInfo(roomId, userId);


        assertThat(result).isSameAs(expectedInfoDto);
        verify(interviewParticipantRepository).getReferenceById(participantId);
        verify(interviewParticipantMapper).toDtoExtraUserInfo(interviewParticipant);
    }

    @Test
    @DisplayName("Test getParticipantInfo should handle NullPointerException and return null")
    void testGetParticipantInfo_HandlesNullPointerExceptionAndReturnsNull() {
        UUID roomId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        InterviewParticipantId participantId = new InterviewParticipantId(userId, roomId);


        when(interviewParticipantRepository.getReferenceById(participantId)).thenThrow(NullPointerException.class);


        InterviewParticipantExtraUserInfoDto result = interviewConferenceRoomService.getParticipantInfo(roomId, userId);


        assertThat(result).isNull();
        verify(interviewParticipantRepository).getReferenceById(participantId);
    }

    @Test
    @DisplayName("Test getById should return conference room by ID")
    void testGetById_ReturnsConferenceRoomByID() {
        UUID roomId = UUID.randomUUID();
        InterviewConferenceRoomDto expectedDto = new InterviewConferenceRoomDto();
        InterviewConferenceRoom roomEntity = new InterviewConferenceRoom();


        when(interviewConferenceRoomRepository.findById(roomId)).thenReturn(Optional.of(roomEntity));
        when(interviewConferenceRoomMapper.toDto(roomEntity)).thenReturn(expectedDto);


        InterviewConferenceRoomDto result = interviewConferenceRoomService.getById(roomId);


        assertThat(result).isSameAs(expectedDto);
        verify(interviewConferenceRoomRepository).findById(roomId);
        verify(interviewConferenceRoomMapper).toDto(roomEntity);
    }

    @Test
    @DisplayName("Test getById should throw RuntimeException when conference room not found")
    void testGetById_ThrowsRuntimeExceptionWhenConferenceRoomNotFound() {
        UUID roomId = UUID.randomUUID();


        when(interviewConferenceRoomRepository.findById(roomId)).thenReturn(Optional.empty());


        Assertions.assertThatThrownBy(() -> interviewConferenceRoomService.getById(roomId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Interview conference room not found!");

        verify(interviewConferenceRoomRepository).findById(roomId);
    }

    @Test
    @DisplayName("Test getByIdFullyLoaded should return fully loaded conference room by ID")
    void testGetByIdFullyLoaded_ReturnsFullyLoadedConferenceRoomByID() {
        UUID roomId = UUID.randomUUID();
        InterviewConferenceRoomDto expectedDto = new InterviewConferenceRoomDto();
        InterviewConferenceRoom roomEntity = new InterviewConferenceRoom();


        when(interviewConferenceRoomRepository.findById(roomId)).thenReturn(Optional.of(roomEntity));
        when(interviewConferenceRoomMapper.toDtoFullyLoaded(roomEntity)).thenReturn(expectedDto);


        InterviewConferenceRoomDto result = interviewConferenceRoomService.getByIdFullyLoaded(roomId);


        assertThat(result).isSameAs(expectedDto);
        verify(interviewConferenceRoomRepository).findById(roomId);
        verify(interviewConferenceRoomMapper).toDtoFullyLoaded(roomEntity);
    }

    @Test
    @DisplayName("Test getByIdFullyLoaded should throw RuntimeException when conference room not found")
    void testGetByIdFullyLoaded_ThrowsRuntimeExceptionWhenConferenceRoomNotFound() {
        UUID roomId = UUID.randomUUID();


        when(interviewConferenceRoomRepository.findById(roomId)).thenReturn(Optional.empty());


        Assertions.assertThatThrownBy(() -> interviewConferenceRoomService.getByIdFullyLoaded(roomId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Interview conference room not found!");

        verify(interviewConferenceRoomRepository).findById(roomId);
    }

}
