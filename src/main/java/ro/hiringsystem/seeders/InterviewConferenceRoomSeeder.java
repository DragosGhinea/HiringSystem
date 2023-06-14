package ro.hiringsystem.seeders;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;
import ro.hiringsystem.repository.InterviewConferenceRoomRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@ConditionalOnProperty(value = "app.data.seeding.enabled", havingValue = "true")
@RequiredArgsConstructor
public class InterviewConferenceRoomSeeder {
    private final InterviewConferenceRoomRepository interviewConferenceRoomRepository;

    public void seedData(){
        System.out.println("Starting InterviewConferenceRoom seeding...");

        UUID candidateId = UUID.fromString("38e57c39-38b0-4dfb-8f4f-0b8e6b3efbce");
        UUID interviewerId = UUID.fromString("a29c5c47-2b4e-483e-93f3-ae350a22a777");
        UUID interviewRoomId = UUID.fromString("ce2d2a11-7759-4d9c-88f2-9e2ef29b7853");
        InterviewConferenceRoom interviewRoom1 = InterviewConferenceRoom.builder()
                .id(interviewRoomId)
                .startDate(LocalDateTime.now().plusMinutes(2))
                .creationDate(LocalDateTime.now())
                .participants(List.of(
                        InterviewParticipant.builder()
                                .userId(candidateId)
                                .interviewRoomId(interviewRoomId)
                                .isRoomModerator(false)
                                .build(),
                        InterviewParticipant.builder()
                                .userId(interviewerId)
                                .interviewRoomId(interviewRoomId)
                                .isRoomModerator(true)
                                .build()
                ))
                .build();

        UUID candidateId2 = UUID.fromString("f84d7f9b-3961-4dcd-a5c7-1035db184a1a");
        UUID interviewerId2 = UUID.fromString("825d3b65-ba86-41fe-b5b2-e9c67c59f868");
        UUID interviewRoomId2 = UUID.fromString("fcf343d4-1026-43cc-95e1-09762d6c7f5e");

        InterviewConferenceRoom interviewRoom2 = InterviewConferenceRoom.builder()
                .id(interviewRoomId2)
                .startDate(LocalDateTime.now().plusMinutes(5))
                .creationDate(LocalDateTime.now())
                .participants(List.of(
                        InterviewParticipant.builder()
                                .userId(candidateId2)
                                .interviewRoomId(interviewRoomId2)
                                .isRoomModerator(false)
                                .build(),
                        InterviewParticipant.builder()
                                .userId(interviewerId2)
                                .interviewRoomId(interviewRoomId2)
                                .isRoomModerator(true)
                                .build()
                ))
                .build();

        interviewConferenceRoomRepository.saveAll(List.of(interviewRoom1, interviewRoom2));
        System.out.println("InterviewConferenceRoom seeding completed.");

    }
}
