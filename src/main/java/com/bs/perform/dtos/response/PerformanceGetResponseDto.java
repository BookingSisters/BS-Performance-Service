package com.bs.perform.dtos.response;

import java.time.LocalDate;
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
public class PerformanceGetResponseDto {

    private Long id;
    private String title;
    private String description;
    private int runningTime;
    private LocalDate performanceStartDate;
    private LocalDate performanceEndDate;
    private LocalDate reservationStartDate;
    private LocalDate reservationEndDate;
    private Long venueId;

}