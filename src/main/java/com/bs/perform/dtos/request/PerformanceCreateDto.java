package com.bs.perform.dtos.request;

import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PerformanceCreateDto {

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

    public Performance toEntity(){
        return Performance.builder()
                .title(this.title)
                .description(this.description)
                .runTime(this.runTime)
                .totalSeatCount(this.totalSeatCount)
                .performanceStartDate(this.performanceStartDate)
                .performanceEndDate(this.performanceEndDate)
                .reservationStartDate(this.reservationStartDate)
                .reservationEndDate(this.reservationEndDate)
                .location(this.location)
                .seatGradeList(this.seatGradeList)
                .sessionList(this.sessionList)
                .build();
    }
}
