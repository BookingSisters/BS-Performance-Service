package com.bs.perform.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@DynamoDbBean
@NoArgsConstructor
@Data
public class Session {

    private LocalTime sessionTime;
    private List<String> performers;

    @Builder
    public Session(LocalTime sessionTime, List<String> performers) {
        this.sessionTime = Objects.requireNonNull(sessionTime, "sessionTime must not be null");
        this.performers = Objects.requireNonNull(performers, "performers must not be null");
        if (performers.isEmpty()) {
            throw new IllegalArgumentException("performers list must not be empty");
        }
    }

}
