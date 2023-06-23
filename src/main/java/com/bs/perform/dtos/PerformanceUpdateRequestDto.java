package com.bs.perform.dtos;

import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class PerformanceUpdateRequestDto {

    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
    @NotNull
    private final int runTime;
    @NotNull
    private final int totalSeatCount;
    @NotNull
    private final LocalDate performanceStartDate;
    @NotNull
    private final LocalDate performanceEndDate;
    @NotNull
    private final LocalDate reservationStartDate;
    @NotNull
    private final LocalDate reservationEndDate;
    @NotBlank
    private final String location;
    @NotEmpty
    private final List<SeatGrade> seatGradeList;
    @NotEmpty
    private final List<Session> sessionList;

}
