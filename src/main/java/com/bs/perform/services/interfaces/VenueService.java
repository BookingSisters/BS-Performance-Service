package com.bs.perform.services.interfaces;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import java.util.List;

public interface VenueService {

    void createVenue(final VenueCreateDto venueCreateDto);

    void updateVenue(final Long id, final VenueUpdateDto venueUpdateDto);

    void deleteVenue(final Long id);

    VenueGetResponseDto getVenueById(final Long id);

    List<VenueGetResponseDto> getAllVenues();
}
