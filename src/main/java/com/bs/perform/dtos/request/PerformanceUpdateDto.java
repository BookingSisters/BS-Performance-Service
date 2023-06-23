package com.bs.perform.dtos.request;

import com.bs.perform.models.Performance;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.time.LocalDate;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PerformanceUpdateDto {

    @NotBlank(message = "Title field must not be empty")
    private final String title;

    @NotBlank(message = "Description field must not be empty")
    private final String description;

    @PositiveOrZero(message = "Running time must be zero or positive")
    private final int runningTime;

    @NotNull(message = "Performance start date field must not be null")
    private final LocalDate performanceStartDate;

    @NotNull(message = "Performance end date field must not be null")
    private final LocalDate performanceEndDate;

    @NotNull(message = "Reservation start date field must not be null")
    private final LocalDate reservationStartDate;

    @NotNull(message = "Reservation end date field must not be null")
    private final LocalDate reservationEndDate;


    public Performance toEntity() {
        return Performance.builder()
            .title(this.title)
            .description(this.description)
            .runningTime(this.runningTime)
            .performanceStartDate(this.performanceStartDate)
            .performanceEndDate(this.performanceEndDate)
            .reservationStartDate(this.reservationStartDate)
            .reservationEndDate(this.reservationEndDate)
            .build();
    }
}
