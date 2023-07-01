package com.bs.perform.services;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;

public interface VenueService {


    void createVenue(final VenueCreateDto venueCreateDto);

    void updateVenue(final String id, final VenueUpdateDto venueUpdateDto);

    VenueGetResponseDto getVenueById(final String id);

}
