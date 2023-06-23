package com.bs.perform.dtos.response;

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
public class SessionGetResponseDto {

    private LocalDate sessionDate;
    private LocalTime sessionTime;
    private Long performanceId;

}
