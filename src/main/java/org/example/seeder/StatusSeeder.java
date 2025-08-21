package org.example.seeder;

import lombok.RequiredArgsConstructor;
import org.example.model.Status;
import org.example.repository.StatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatusSeeder implements CommandLineRunner {

    private final StatusRepository statusRepository;

    @Override
    public void run(String... args) {
        createStatusIfNotExists("ACTIVE", "active");
        createStatusIfNotExists("INACTIVE", "inactive");
        createStatusIfNotExists("PERMITTED", "permitted");
    }

    private void createStatusIfNotExists(String name, String description) {
        if (statusRepository.findByName(name).isEmpty()) {
            Status status = Status.builder()
                    .name(name)
                    .description(description)
                    .build();
            statusRepository.save(status);
        }
    }
}