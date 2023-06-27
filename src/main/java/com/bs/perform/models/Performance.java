package com.bs.perform.models;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@DynamoDbBean
@NoArgsConstructor
@Data
public class Performance {

    private String id;
    private String title;
    private String description;
    private int runTime;
    private int totalSeatCount;
    private LocalDate performanceStartDate;
    private LocalDate performanceEndDate;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private String location;
    private List<SeatGrade> seatGradeList;
    private List<Session> sessionList;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private boolean isDeleted;
    private LocalDateTime deletedDate;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    @Builder
    public Performance(String title, String description, int runTime, int totalSeatCount, LocalDate performanceStartDate, LocalDate performanceEndDate, LocalDate reservationStartDate, LocalDate reservationEndDate, String location, List<SeatGrade> seatGradeList, List<Session> sessionList) {
        this.id = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
        this.isDeleted = false;
        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.runTime = runTime;
        this.totalSeatCount = totalSeatCount;
        this.performanceStartDate = Objects.requireNonNull(performanceStartDate, "Performance Start Date cannot be null");
        this.performanceEndDate = Objects.requireNonNull(performanceEndDate, "Performance End Date cannot be null");
        this.reservationStartDate = Objects.requireNonNull(reservationStartDate, "Reservation Start Date cannot be null");
        this.reservationEndDate = Objects.requireNonNull(reservationEndDate, "Reservation End Date cannot be null");
        this.location = Objects.requireNonNull(location, "Location cannot be null");
        this.seatGradeList = Objects.requireNonNull(seatGradeList, "Seat Grade List cannot be null");
        this.sessionList = Objects.requireNonNull(sessionList, "Session List cannot be null");

        if(performanceStartDate.isAfter(performanceEndDate)) {
            throw new IllegalArgumentException("Performance Start Date cannot be after Performance End Date");
        }
        if(reservationStartDate.isAfter(reservationEndDate)) {
            throw new IllegalArgumentException("Reservation Start Date cannot be after Reservation End Date");
        }
    }

}
