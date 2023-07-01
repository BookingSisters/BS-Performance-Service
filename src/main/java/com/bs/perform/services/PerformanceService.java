package com.bs.perform.services;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;

public interface PerformanceService {

    void createPerformance(final PerformanceCreateDto performanceDto);

    void updatePerformance(final String id, final PerformanceUpdateDto performanceDto);

    PerformanceGetResponseDto getPerformanceById(final String id);
}
