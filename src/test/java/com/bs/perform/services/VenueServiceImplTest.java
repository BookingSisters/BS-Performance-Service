package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.venue.Seat;
import com.bs.perform.models.venue.Venue;
import com.bs.perform.repositories.VenueRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class VenueServiceImplTest {

    @InjectMocks
    private VenueServiceImpl venueService;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ModelMapper modelMapper;

    List<Seat> seatList = new ArrayList<>();
    Venue venue;

    @BeforeEach
    public void setUp(){
        seatList.add(Seat.builder().row('A').col(1).build());
        seatList.add(Seat.builder().row('A').col(2).build());
        seatList.add(Seat.builder().row('A').col(3).build());

        venue = getVenue();
    }

    @Test
    @DisplayName("유효한 VenueCreateDto가 주어졌을 때, 정상적으로 공연장을 저장")
    void createVenueTest() {

        VenueCreateDto venueCreateDto = getVenueCreateDto();

        doNothing().when(venueRepository).createVenue(any(Venue.class));

        venueService.createVenue(venueCreateDto);

        verify(venueRepository, times(1)).createVenue(any(Venue.class));

    }

    @Test
    @DisplayName("유효하지 않은 VenueCreateDto가 주어졌을 때, NullPointerException이 발생")
    void createVenueFailureTest() {

        VenueCreateDto venueCreateDto = getVenueCreateFailureDto();

        assertThatThrownBy(() -> venueService.createVenue(venueCreateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("유효한 VenueUpdateDto가 주어졌을 때, 정상적으로 공연장을 업데이트")
    void updateVenueTest() {

        String id = "123";
        VenueUpdateDto venueUpdateDto = getVenueUpdateDto();

        doNothing().when(venueRepository).updateVenue(eq(id), any(Venue.class));

        venueService.updateVenue(id, venueUpdateDto);

        verify(venueRepository, times(1)).updateVenue(eq(id), any(Venue.class));
    }

    @Test
    @DisplayName("유효하지 않은 VenueUpdateDto가 주어졌을 때, NullPointerException이 발생")
    void updateVenueFailureTest() {

        String id = "123";
        VenueUpdateDto venueUpdateDto = getVenueUpdateFailureDto();

        assertThatThrownBy(() -> venueService.updateVenue(id, venueUpdateDto))
            .isInstanceOf(NullPointerException.class);
    }
    
    @Test
    @DisplayName("기존에 존재하는 공연장 ID가 주어졌을 때, VenueGetResponseDto를 반환")
    void getVenueByIdTest() {

        String id = "123";
        VenueGetResponseDto venueGetResponseDto = getVenueGetResponseDto();

        doReturn(venue).when(venueRepository).getVenueById(id);
        doReturn(venueGetResponseDto).when(modelMapper).map(venue, VenueGetResponseDto.class);

        VenueGetResponseDto venueById = venueService.getVenueById(id);

        assertThat(venueById.getName()).isEqualTo(venue.getName());
    }


    @Test
    @DisplayName("존재하지 않는 공연장 ID가 주어졌을 때, ResourceNotFoundException이 발생")
    void getVenueByIdFailureTest() {

        String id = "123";

        doThrow(ResourceNotFoundException.class).when(venueRepository).getVenueById(id);

        assertThatThrownBy(() -> venueService.getVenueById(id))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .seatList(seatList)
            .build();
    }

    private VenueCreateDto getVenueCreateDto() {
        return VenueCreateDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .seatList(seatList)
            .build();
    }

    private VenueCreateDto getVenueCreateFailureDto() {
        return VenueCreateDto.builder()
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

    private VenueUpdateDto getVenueUpdateDto() {
        return VenueUpdateDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .seatList(seatList)
            .build();
    }

    private VenueUpdateDto getVenueUpdateFailureDto() {
        return VenueUpdateDto.builder()
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

    private VenueGetResponseDto getVenueGetResponseDto() {
        return VenueGetResponseDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .seatList(seatList)
            .build();
    }

}