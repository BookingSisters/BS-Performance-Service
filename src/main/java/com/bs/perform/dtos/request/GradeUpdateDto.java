package com.bs.perform.dtos.request;

import com.bs.perform.enums.GradeType;
import com.bs.perform.models.Grade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
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
public class GradeUpdateDto {

    @NotNull(message = "Grade type must not be null")
    private GradeType gradeType;

    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;

    @NotBlank(message = "Performers must not be blank")
    private String performers;

    public Grade toEntity() {
        return Grade.builder()
            .gradeType(this.gradeType)
            .price(this.price)
            .performers(this.performers)
            .build();
    }
}
