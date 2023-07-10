package com.bs.perform.services;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.VenueRepository;
import com.bs.perform.services.interfaces.PerformanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PerformanceServiceImpl implements PerformanceService {

    private final ModelMapper modelMapper;
    private final PerformanceRepository performanceRepository;
    private final VenueRepository venueRepository;

    @Override
    @Transactional
    public void createPerformance(final PerformanceCreateDto performanceDto) {

        log.info("Creating performance with PerformanceCreateDto: {}", performanceDto);

        Venue venue = getVenue(performanceDto.getVenueId());
        Performance performance = performanceDto.toEntity(venue);

        performanceRepository.save(performance);
    }

    @Override
    @Transactional
    public void updatePerformance(final Long id, final PerformanceUpdateDto performanceDto) {

        log.info("Updating performance with ID: {}, PerformanceUpdateDto: {}", id, performanceDto);
        Performance performance = getPerformance(id);

        performance.updatePerformance(performanceDto.getTitle(), performanceDto.getDescription(),
            performanceDto.getRunningTime(),
            performanceDto.getPerformanceStartDate(), performanceDto.getPerformanceEndDate(),
            performanceDto.getReservationStartDate(), performanceDto.getReservationEndDate());
    }

    @Override
    @Transactional
    public void deletePerformance(final Long id) {

        log.info("deletePerformance with ID: {}", id);
        Performance performance = getPerformance(id);

        performance.deletePerformance();
    }

    @Override
    public PerformanceGetResponseDto getPerformanceById(final Long id) {

        log.info("GetPerformance with ID: {}", id);

        Performance performance = getPerformance(id);

        return modelMapper.map(performance, PerformanceGetResponseDto.class);
    }

    private Venue getVenue(Long id) {
        return venueRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venue", String.valueOf(id)));
    }

    private Performance getPerformance(Long id) {
        return performanceRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Performance", String.valueOf(id)));
    }

}
