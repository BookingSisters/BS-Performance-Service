package com.bs.perform.dtos.request;

import com.bs.perform.models.performance.Performance;
import com.bs.perform.models.performance.SeatGrade;
import com.bs.perform.models.performance.Session;
import com.bs.perform.models.venue.Venue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PerformanceUpdateDto {

    @NotBlank
    private final String title;
    @NotBlank
    private final String description;
    private final int runningTime;
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
    private final String venueId;
    @NotEmpty
    private final List<SeatGrade> seatGradeList;
    @NotEmpty
    private final List<Session> sessionList;

    public Performance toEntity() {
        return Performance.builder()
            .title(this.title)
            .description(this.description)
            .runningTime(this.runningTime)
            .performanceStartDate(this.performanceStartDate)
            .performanceEndDate(this.performanceEndDate)
            .reservationStartDate(this.reservationStartDate)
            .reservationEndDate(this.reservationEndDate)
            .venueId(this.venueId)
            .seatGradeList(this.seatGradeList)
            .sessionList(this.sessionList)
            .build();
    }
}
