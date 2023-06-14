package ro.hiringsystem.seeders;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "app.data.seeding.enabled", havingValue = "true")
@RequiredArgsConstructor
public class SeederManager {

    private final InterviewConferenceRoomSeeder interviewConferenceRoomSeeder;
    private final UserSeeder userSeeder;
    private final JobSeeder jobSeeder;
    private final JobApplicationSeeder jobApplicationSeeder;

    @PostConstruct
    public void seedData(){
        userSeeder.seedData();
        interviewConferenceRoomSeeder.seedData();
        jobSeeder.seedData();
        jobApplicationSeeder.seedData();
    }
}
