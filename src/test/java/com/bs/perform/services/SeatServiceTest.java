package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.SeatCreateDto;
import com.bs.perform.dtos.request.SeatUpdateDto;
import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.response.SeatGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Seat;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.SeatRepository;
import com.bs.perform.repositories.VenueRepository;
import com.bs.perform.services.SeatServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @InjectMocks
    private SeatServiceImpl seatService;

    @Mock
    private SeatRepository seatRepository;
    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ModelMapper modelMapper;

    Long venueId;
    Venue venue;
    Long seatId;
    Seat seat;

    @BeforeEach
    public void setUp() {
        venueId = 10023L;
        venue = getVenue();
        seatId = 123L;
        seat = getSeat();
    }


    @Test
    @DisplayName("유효한 SeatCreateDto가 주어졌을 때, 정상적으로 좌석을 저장")
    void createSeatTest() {

        SeatCreateDto seatCreateDto = SeatCreateDto.builder()
            .row("A")
            .col(1)
            .venueId(venueId)
            .build();

        doReturn(Optional.of(venue)).when(venueRepository).findByIdAndIsDeletedFalse(venueId);
        doReturn(seat).when(seatRepository).save(any(Seat.class));

        seatService.createSeat(seatCreateDto);

        verify(seatRepository, times(1)).save(any(Seat.class));
    }

    @Test
    @DisplayName("유효하지 않은 SeatCreateDto가 주어졌을 때, NullPointerException이 발생")
    void createSeatFailureTest() {

        SeatCreateDto seatCreateDto = SeatCreateDto.builder().venueId(venueId).build();
        doReturn(Optional.of(venue)).when(venueRepository).findByIdAndIsDeletedFalse(venueId);

        assertThatThrownBy(() -> seatService.createSeat(seatCreateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("유효한 SeatUpdateDto가 주어졌을 때, 정상적으로 좌석을 업데이트")
    void updateSeatTest() {

        SeatUpdateDto seatUpdateDto = SeatUpdateDto.builder()
            .row("A")
            .col(1)
            .build();
        doReturn(Optional.of(seat)).when(seatRepository).findByIdAndIsDeletedFalse(seatId);

        seatService.updateSeat(seatId, seatUpdateDto);

        verify(seatRepository, times(1)).findByIdAndIsDeletedFalse(seatId);
    }

    @Test
    @DisplayName("유효하지 않은 SeatUpdateDto가 주어졌을 때, NullPointerException이 발생")
    void updateSeatFailureTest() {

        SeatUpdateDto seatUpdateDto = SeatUpdateDto.builder().build();
        doReturn(Optional.of(seat)).when(seatRepository).findByIdAndIsDeletedFalse(seatId);

        assertThatThrownBy(() -> seatService.updateSeat(seatId, seatUpdateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("기존에 존재하는 좌석 ID가 주어졌을 때, SeatGetResponseDto를 반환")
    void getSeatByIdTest() {

        SeatGetResponseDto seatGetResponseDto = SeatGetResponseDto.builder()
            .row("A")
            .col(1)
            .build();

        doReturn(Optional.of(seat)).when(seatRepository).findByIdAndIsDeletedFalse(seatId);
        doReturn(seatGetResponseDto).when(modelMapper).map(seat, SeatGetResponseDto.class);

        SeatGetResponseDto result = seatService.getSeatById(seatId);

        assertThat(result.getCol()).isEqualTo(seat.getCol());
        assertThat(result.getRow()).isEqualTo(seat.getRow());
    }

    @Test
    @DisplayName("존재하지 않는 좌석 ID가 주어졌을 때, ResourceNotFoundException 발생")
    void getSeatByIdFailureTest() {

        doThrow(ResourceNotFoundException.class).when(seatRepository)
            .findByIdAndIsDeletedFalse(seatId);

        assertThatThrownBy(() -> seatService.getSeatById(seatId))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

    private Seat getSeat() {
        return Seat.builder()
            .row("A")
            .col(1)
            .venue(venue)
            .build();
    }

}