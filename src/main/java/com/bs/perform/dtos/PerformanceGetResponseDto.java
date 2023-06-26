package com.bs.perform.dtos;

import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class PerformanceGetResponseDto {

    private final String id;
    private final String title;
    private final String description;
    private final int runTime;
    private final int totalSeatCount;
    private final LocalDate performanceStartDate;
    private final LocalDate performanceEndDate;
    private final LocalDate reservationStartDate;
    private final LocalDate reservationEndDate;
    private final String location;
    private final List<SeatGrade> seatGradeList;
    private final List<Session> sessionList;

}