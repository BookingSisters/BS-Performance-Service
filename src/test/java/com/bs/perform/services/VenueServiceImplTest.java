package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.VenueRepository;
import com.bs.perform.services.VenueServiceImpl;
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
class VenueServiceImplTest {

    @InjectMocks
    private VenueServiceImpl venueService;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ModelMapper modelMapper;

    Long venueId;
    Venue venue;

    @BeforeEach
    public void setUp() {
        venueId = 10023L;
        venue = getVenue();
    }

    @Test
    @DisplayName("유효한 VenueCreateDto가 주어졌을 때, 정상적으로 공연장을 저장")
    void createVenueTest() {

        VenueCreateDto venueCreateDto = VenueCreateDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        doReturn(venue).when(venueRepository).save(any(Venue.class));

        venueService.createVenue(venueCreateDto);

        verify(venueRepository, times(1)).save(any(Venue.class));

    }

    @Test
    @DisplayName("유효하지 않은 VenueCreateDto가 주어졌을 때, NullPointerException이 발생")
    void createVenueFailureTest() {

        VenueCreateDto venueCreateDto = VenueCreateDto.builder().build();

        assertThatThrownBy(() -> venueService.createVenue(venueCreateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("유효한 VenueUpdateDto가 주어졌을 때, 정상적으로 공연장을 업데이트")
    void updateVenueTest() {

        VenueUpdateDto venueUpdateDto = VenueUpdateDto.builder()
            .name("Apollo Theater222")
            .location("253 W 125th St, New York, NY 10027, United States22")
            .build();

        doReturn(Optional.of(venue)).when(venueRepository).findByIdAndIsDeletedFalse(venueId);

        venueService.updateVenue(venueId, venueUpdateDto);

        verify(venueRepository, times(1)).findByIdAndIsDeletedFalse(venueId);

    }

    @Test
    @DisplayName("유효하지 않은 VenueUpdateDto가 주어졌을 때, NullPointerException이 발생")
    void updateVenueFailureTest() {

        VenueUpdateDto venueUpdateDto = VenueUpdateDto.builder().build();

        doThrow(NullPointerException.class).when(venueRepository)
            .findByIdAndIsDeletedFalse(venueId);

        assertThatThrownBy(() -> venueService.updateVenue(venueId, venueUpdateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("기존에 존재하는 공연장 ID가 주어졌을 때, VenueGetResponseDto를 반환")
    void getVenueByIdTest() {

        VenueGetResponseDto venueGetResponseDto = VenueGetResponseDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        doReturn(Optional.of(venue)).when(venueRepository).findByIdAndIsDeletedFalse(venueId);
        doReturn(venueGetResponseDto).when(modelMapper).map(venue, VenueGetResponseDto.class);

        VenueGetResponseDto result = venueService.getVenueById(venueId);

        assertThat(result.getName()).isEqualTo(venue.getName());
    }

    @Test
    @DisplayName("존재하지 않는 공연장 ID가 주어졌을 때, ResourceNotFoundException 발생")
    void getVenueByIdFailureTest() {

        doThrow(ResourceNotFoundException.class).when(venueRepository)
            .findByIdAndIsDeletedFalse(venueId);

        assertThatThrownBy(() -> venueService.getVenueById(venueId))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

}