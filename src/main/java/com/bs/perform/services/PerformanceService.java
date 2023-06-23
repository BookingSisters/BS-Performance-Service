package com.bs.perform.services;

import com.bs.perform.dtos.PerformanceCreateRequestDto;
import com.bs.perform.dtos.PerformanceGetResponseDto;
import com.bs.perform.dtos.PerformanceUpdateRequestDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    public void createPerformance(PerformanceCreateRequestDto performanceDto) {

        Performance performance = Performance.builder()
                .title(performanceDto.getTitle())
                .description(performanceDto.getDescription())
                .runTime(performanceDto.getRunTime())
                .totalSeatCount(performanceDto.getTotalSeatCount())
                .performanceStartDate(performanceDto.getPerformanceStartDate())
                .performanceEndDate(performanceDto.getPerformanceEndDate())
                .reservationStartDate(performanceDto.getReservationStartDate())
                .reservationEndDate(performanceDto.getReservationEndDate())
                .location(performanceDto.getLocation())
                .seatGradeList(performanceDto.getSeatGradeList())
                .sessionList(performanceDto.getSessionList())
                .build();

        performanceRepository.createPerformance(performance);
    }

    public void updatePerformance(String id, PerformanceUpdateRequestDto performanceDto) {

        Performance performance = Performance.builder()
                .title(performanceDto.getTitle())
                .description(performanceDto.getDescription())
                .build();

        performanceRepository.updatePerformance(id, performance);
    }

    public PerformanceGetResponseDto getPerformanceById(String id) {

        Performance performance = performanceRepository.getPerformanceById(id);

        if (performance == null) {
            throw new ResourceNotFoundException(id);
        }

        return PerformanceGetResponseDto.builder()
                .id(performance.getId())
                .title(performance.getTitle())
                .description(performance.getDescription())
                .runTime(performance.getRunTime())
                .totalSeatCount(performance.getTotalSeatCount())
                .performanceStartDate(performance.getPerformanceStartDate())
                .performanceEndDate(performance.getPerformanceEndDate())
                .reservationStartDate(performance.getReservationStartDate())
                .reservationEndDate(performance.getReservationEndDate())
                .location(performance.getLocation())
                .seatGradeList(performance.getSeatGradeList())
                .sessionList(performance.getSessionList())
                .build();
    }
}
