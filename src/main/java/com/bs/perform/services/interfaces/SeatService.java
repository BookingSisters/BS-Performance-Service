package com.bs.perform.services.interfaces;


import com.bs.perform.dtos.request.SeatCreateDto;
import com.bs.perform.dtos.request.SeatUpdateDto;
import com.bs.perform.dtos.response.SeatGetResponseDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import java.util.List;

public interface SeatService {

    void createSeat(final SeatCreateDto venueCreateDto);

    void updateSeat(final Long id, final SeatUpdateDto venueUpdateDto);

    void deleteSeat(final Long id);

    SeatGetResponseDto getSeatById(final Long id);

}
