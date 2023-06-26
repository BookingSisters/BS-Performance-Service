package com.bs.perform.services;

import com.bs.perform.dtos.PerformanceCreateDto;
import com.bs.perform.dtos.PerformanceGetResponseDto;
import com.bs.perform.dtos.PerformanceUpdateDto;

public interface PerformanceService {
    void createPerformance(PerformanceCreateDto performanceDto);

    void updatePerformance(String id, PerformanceUpdateDto performanceDto);

    PerformanceGetResponseDto getPerformanceById(String id);
}
