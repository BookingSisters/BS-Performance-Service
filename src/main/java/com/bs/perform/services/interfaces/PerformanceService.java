package com.bs.perform.services.interfaces;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;

public interface PerformanceService {

    void createPerformance(final PerformanceCreateDto performanceDto);

    void updatePerformance(final Long id, final PerformanceUpdateDto performanceDto);

    void deletePerformance(final Long id);

    PerformanceGetResponseDto getPerformanceById(final Long id);
}
