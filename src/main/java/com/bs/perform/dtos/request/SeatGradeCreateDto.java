package com.bs.perform.dtos.request;

import com.bs.perform.models.Grade;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Seat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
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
public class SeatGradeCreateDto {

    @Positive(message = "Seat Id must be positive")
    @NotNull(message = "Seat Id must not be null")
    private Long seatId;

    @Positive(message = "Grade Id must be positive")
    @NotNull(message = "Grade Id must not be null")
    private Long gradeId;

    public SeatGrade toEntity(Seat seat, Grade grade) {
        return SeatGrade.builder()
            .seat(seat)
            .grade(grade)
            .build();
    }
}
