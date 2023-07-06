package com.bs.perform.services;

import com.bs.perform.dtos.request.SeatCreateDto;
import com.bs.perform.dtos.request.SeatUpdateDto;
import com.bs.perform.dtos.response.SeatGetResponseDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Seat;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.SeatRepository;
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
public class SeatServiceImpl implements SeatService {

    private final ModelMapper modelMapper;
    private final SeatRepository seatRepository;
    private final VenueRepository venueRepository;

    @Override
    @Transactional
    public void createSeat(SeatCreateDto seatCreateDto) {

        log.info("Creating seat with seatCreateDto: {}", seatCreateDto);

        Venue venue = getVenue(seatCreateDto.getVenueId());
        Seat seat = seatCreateDto.toEntity(venue);

        seatRepository.save(seat);
    }

    @Override
    @Transactional
    public void updateSeat(Long id, SeatUpdateDto seatUpdateDto) {

        log.info("Updating venue with ID: {}, venueUpdateDto: {}", id, seatUpdateDto);

        Seat seat = getSeat(id);

        seat.updateSeat(seatUpdateDto.getRow(), seatUpdateDto.getCol());

    }

    @Override
    @Transactional
    public void deleteSeat(Long id) {

        log.info("getVenueById with ID: {}", id);

        Seat seat = getSeat(id);

        seat.deleteSeat();
    }

    @Override
    public SeatGetResponseDto getSeatById(Long id) {

        log.info("getSeatById with ID: {}", id);

        Seat seat = getSeat(id);

        return modelMapper.map(seat, SeatGetResponseDto.class);
    }

    private Venue getVenue(Long venueId) {
        return venueRepository.findByIdAndIsDeletedFalse(venueId)
            .orElseThrow(() -> new ResourceNotFoundException("Venue", String.valueOf(venueId)));
    }

    private Seat getSeat(Long seatId) {
        return seatRepository.findByIdAndIsDeletedFalse(seatId)
            .orElseThrow(() -> new ResourceNotFoundException("Seat", String.valueOf(seatId)));
    }
}
