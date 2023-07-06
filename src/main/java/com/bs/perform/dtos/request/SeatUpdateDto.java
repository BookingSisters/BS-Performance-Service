package com.bs.perform.dtos.request;

import jakarta.validation.constraints.NotBlank;
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
public class SeatUpdateDto {

    @NotBlank(message = "Row must not be blank")
    private String row;

    @PositiveOrZero(message = "Column number must be positive or zero")
    private int col;

}
