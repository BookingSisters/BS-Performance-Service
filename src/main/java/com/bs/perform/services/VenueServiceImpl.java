package com.bs.perform.services;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.VenueRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class VenueServiceImpl implements VenueService {

    private final ModelMapper modelMapper;
    private final VenueRepository venueRepository;

    @Override
    @Transactional
    public void createVenue(final VenueCreateDto venueCreateDto) {

        log.info("Creating venue with venueCreateDto: {}", venueCreateDto);
        Venue venue = venueCreateDto.toEntity();

        venueRepository.save(venue);
    }

    @Override
    @Transactional
    public void updateVenue(final Long id, final VenueUpdateDto venueUpdateDto) {

        log.info("Updating venue with ID: {}, venueUpdateDto: {}", id, venueUpdateDto);

        Venue venue = getVenue(id);

        venue.updateVenue(venueUpdateDto.getName(), venueUpdateDto.getLocation());
    }

    @Override
    @Transactional
    public void deleteVenue(final Long id) {

        log.info("getVenueById with ID: {}", id);

        Venue venue = getVenue(id);

        venue.deleteVenue();
    }

    @Override
    public VenueGetResponseDto getVenueById(final Long id) {

        log.info("getVenueById with ID: {}", id);

        Venue venue = getVenue(id);

        return modelMapper.map(venue, VenueGetResponseDto.class);
    }

    @Override
    public List<VenueGetResponseDto> getAllVenues() {
        log.info("Getting all venues");

        List<Venue> venues = venueRepository.findAllByIsDeletedFalse();

        return venues.stream()
            .map(venue -> modelMapper.map(venue, VenueGetResponseDto.class))
            .collect(Collectors.toList());
    }

    private Venue getVenue(Long id) {
        return venueRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Venue", String.valueOf(id)));
    }
}