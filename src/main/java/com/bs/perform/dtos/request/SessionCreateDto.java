package com.bs.perform.dtos.request;

import com.bs.perform.models.Performance;
import com.bs.perform.models.Session;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SessionCreateDto {

    @NotNull(message = "Session date must not be null")
    private LocalDate sessionDate;

    @NotNull(message = "Session time must not be null")
    private LocalTime sessionTime;

    @NotBlank(message = "Performers must not be blank")
    private String performers;

    @Positive(message = "Performance Id must be positive")
    @NotNull(message = "Performance Id must not be null")
    private Long performanceId;

    public Session toEntity(Performance performance) {
        return Session.builder()
            .sessionDate(this.sessionDate)
            .sessionTime(this.sessionTime)
            .performers(this.performers)
            .performance(performance)
            .build();
    }
}
