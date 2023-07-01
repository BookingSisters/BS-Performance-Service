package com.bs.perform.services;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.exceptions.CustomDatabaseException;
import com.bs.perform.models.venue.Venue;
import com.bs.perform.repositories.VenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;

@Service
@RequiredArgsConstructor
@Slf4j
public class VenueServiceImpl implements VenueService {

    private final ModelMapper modelMapper;
    private final VenueRepository venueRepository;

    @Override
    public void createVenue(final VenueCreateDto venueCreateDto) {

        log.info("Creating venue with venueCreateDto: {}", venueCreateDto);
        Venue venue = venueCreateDto.toEntity();

        try {
            venueRepository.createVenue(venue);
        } catch (DynamoDbException e) {
            log.error(String.valueOf(e));
            throw new CustomDatabaseException("Venue creation failed");
        }
    }

    @Override
    public void updateVenue(final String id, final VenueUpdateDto venueUpdateDto) {

        log.info("Updating venue with venueUpdateDto: {}", venueUpdateDto);
        Venue venue = venueUpdateDto.toEntity();

        try {
            venueRepository.updateVenue(id, venue);
        } catch (DynamoDbException e) {
            log.error(String.valueOf(e));
            throw new CustomDatabaseException("Venue update failed");
        }
    }

    @Override
    public VenueGetResponseDto getVenueById(final String id) {

        log.info("getVenueById with ID: {}", id);

        Venue venue;
        try {
            venue = venueRepository.getVenueById(id);
        } catch (DynamoDbException e) {
            log.error(String.valueOf(e));
            throw new CustomDatabaseException("Venue get failed");
        }

        return modelMapper.map(venue, VenueGetResponseDto.class);
    }

}