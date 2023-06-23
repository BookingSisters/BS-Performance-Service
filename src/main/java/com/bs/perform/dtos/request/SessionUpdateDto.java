package com.bs.perform.dtos.request;

import com.bs.perform.models.Performance;
import com.bs.perform.models.Session;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class SessionUpdateDto {

    @NotNull(message = "Session date must not be null")
    private LocalDate sessionDate;

    @NotNull(message = "Session time must not be null")
    private LocalTime sessionTime;

    public Session toEntity(Performance performance) {
        return Session.builder()
            .sessionDate(this.sessionDate)
            .sessionTime(this.sessionTime)
            .build();
    }
}
