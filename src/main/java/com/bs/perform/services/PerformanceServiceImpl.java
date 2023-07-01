package com.bs.perform.services;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.exceptions.CustomDatabaseException;
import com.bs.perform.models.performance.Performance;
import com.bs.perform.repositories.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceServiceImpl implements PerformanceService {

    private final ModelMapper modelMapper;
    private final PerformanceRepository performanceRepository;

    @Override
    public void createPerformance(final PerformanceCreateDto performanceDto) {

        log.info("Creating performance with PerformanceCreateDto: {}", performanceDto);
        Performance performance = performanceDto.toEntity();

        try {
            performanceRepository.createPerformance(performance);
        } catch (DynamoDbException e) {
            log.error(String.valueOf(e));
            throw new CustomDatabaseException("Performance creation failed");
        }
    }

    @Override
    public void updatePerformance(final String id, final PerformanceUpdateDto performanceDto) {

        log.info("Updating performance with PerformanceUpdateDto: {}", performanceDto);
        Performance performance = performanceDto.toEntity();

        try {
            performanceRepository.updatePerformance(id, performance);
        } catch (DynamoDbException e) {
            log.error(String.valueOf(e));
            throw new CustomDatabaseException("Performance update failed");
        }
    }

    @Override
    public PerformanceGetResponseDto getPerformanceById(final String id) {

        log.info("GetPerformance with ID: {}", id);

        Performance performance;
        try {
            performance = performanceRepository.getPerformanceById(id);
        } catch (DynamoDbException e) {
            log.error(String.valueOf(e));
            throw new CustomDatabaseException("Performance get failed");
        }

        return modelMapper.map(performance, PerformanceGetResponseDto.class);
    }

}
