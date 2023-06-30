package com.bs.perform.dtos.response;

import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
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
    private int runTime;
    private int totalSeatCount;
    private LocalDate performanceStartDate;
    private LocalDate performanceEndDate;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private String location;
    private List<SeatGrade> seatGradeList;
    private List<Session> sessionList;

}