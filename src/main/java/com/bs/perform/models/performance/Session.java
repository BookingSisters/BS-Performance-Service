package com.bs.perform.models.performance;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@DynamoDbBean
@Data
public class Session {

    private String id;
    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private List<String> performers;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }

    @Builder
    public Session(LocalDate sessionDate, LocalTime sessionTime, List<String> performers) {
        this.id = UUID.randomUUID().toString();
        this.sessionDate = Objects.requireNonNull(sessionDate, "sessionDate must not be null");
        this.sessionTime = Objects.requireNonNull(sessionTime, "sessionTime must not be null");
        this.performers = Objects.requireNonNull(performers, "performers must not be null");
        if (performers.isEmpty()) {
            throw new IllegalArgumentException("performers list must not be empty");
        }
    }

}
