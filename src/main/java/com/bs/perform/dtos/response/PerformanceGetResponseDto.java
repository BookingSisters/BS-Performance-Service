package com.bs.perform.dtos.response;

import com.bs.perform.models.performance.SeatGrade;
import com.bs.perform.models.performance.Session;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceGetResponseDto {

    private String id;
    private String title;
    private String description;
    private int runningTime;
    private int totalSeatCount;
    private LocalDate performanceStartDate;
    private LocalDate performanceEndDate;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private String venueId;
    private List<SeatGrade> seatGradeList;
    private List<Session> sessionList;

}