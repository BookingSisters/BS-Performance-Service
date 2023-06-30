package com.bs.perform.services;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.exceptions.CustomDatabaseException;

public interface PerformanceService {

    void createPerformance(PerformanceCreateDto performanceDto) throws CustomDatabaseException;

    void updatePerformance(String id, PerformanceUpdateDto performanceDto);

    PerformanceGetResponseDto getPerformanceById(String id);
}
