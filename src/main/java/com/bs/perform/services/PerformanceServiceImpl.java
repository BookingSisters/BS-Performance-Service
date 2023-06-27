package com.bs.perform.services;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
        performanceRepository.createPerformance(performance);
    }

    @Override
    public void updatePerformance(final String id, final PerformanceUpdateDto performanceDto) {

        log.info("Updating performance with PerformanceUpdateDto: {}", performanceDto);
        Performance performance = performanceDto.toEntity();
        performanceRepository.updatePerformance(id, performance);
    }

    @Override
    public PerformanceGetResponseDto getPerformanceById(final String id) {

        log.info("GetPerformance with ID: {}", id);
        Performance performance = performanceRepository.getPerformanceById(id);
        if (performance == null) {
            throw new ResourceNotFoundException(id);
        }
        return modelMapper.map(performance, PerformanceGetResponseDto.class);
    }

}
