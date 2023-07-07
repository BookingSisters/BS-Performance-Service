package com.bs.perform.dtos.response;

import com.bs.perform.enums.GradeType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeGetResponseDto {

    private GradeType gradeType;
    private BigDecimal price;
    private Long performanceId;

}
