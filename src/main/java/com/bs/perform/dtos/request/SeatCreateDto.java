package com.bs.perform.dtos.request;

import com.bs.perform.models.Seat;
import com.bs.perform.models.Venue;
import jakarta.validation.constraints.NotBlank;
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
public class SeatCreateDto {

    @NotBlank(message = "Row must not be blank")
    private String row;

    @PositiveOrZero(message = "Column number must be positive or zero")
    private int col;

    @Positive(message = "Venue Id must be positive")
    private Long venueId;

    public Seat toEntity(Venue venue) {
        return Seat.builder()
            .row(this.row)
            .col(this.col)
            .venue(venue)
            .build();
    }
}
