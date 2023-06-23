package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.VenueRepository;
import com.bs.perform.services.PerformanceServiceImpl;
import java.time.LocalDate;
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
class PerformanceServiceImplTest {

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private VenueRepository venueRepository;

    @Mock
    private ModelMapper modelMapper;

    Long venueId;
    Venue venue;
    Long performanceId;
    Performance performance;

    @BeforeEach
    public void setUp() {
        venueId = 111L;
        venue = getVenue();
        performanceId = 123L;
        performance = getPerformance();
    }

    @Test
    @DisplayName("유효한 PerformanceCreateDto가 주어졌을 때, 정상적으로 공연을 저장")
    void createPerformanceTest() {

        PerformanceCreateDto performanceCreateDto = PerformanceCreateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId(venueId)
            .build();

        doReturn(Optional.of(venue)).when(venueRepository).findByIdAndIsDeletedFalse(venueId);
        doReturn(performance).when(performanceRepository).save(any(Performance.class));

        performanceService.createPerformance(performanceCreateDto);

        verify(performanceRepository, times(1)).save(any(Performance.class));
    }

    @Test
    @DisplayName("유효하지 않은 PerformanceCreateDto가 주어졌을 때, NullPointerException 발생")
    void createPerformanceFailureTest() {

        PerformanceCreateDto performanceCreateDto = PerformanceCreateDto.builder().venueId(venueId).build();

        doReturn(Optional.of(venue)).when(venueRepository).findByIdAndIsDeletedFalse(venueId);

        assertThatThrownBy(() -> performanceService.createPerformance(performanceCreateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("유효한 ID와 PerformanceUpdateDto가 주어졌을 때, 정상적으로 공연을 업데이트")
    void updatePerformanceTest() {

    }

    @Test
    @DisplayName("유효하지 않은 PerformanceUpdateDto가 주어졌을 때, NullPointerException 발생")
    void updatePerformanceFailureTest() {

        PerformanceUpdateDto performanceUpdateDto = PerformanceUpdateDto.builder().build();
        doReturn(Optional.of(performance)).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);

        assertThatThrownBy(() -> performanceService.updatePerformance(performanceId, performanceUpdateDto))
            .isInstanceOf(NullPointerException.class);
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

    private Performance getPerformance() {
        return Performance.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venue(venue)
            .build();
    }

    @Test
    @DisplayName("기존에 존재하는 공연 ID가 주어졌을 때, PerformanceGetResponseDto를 반환")
    void getPerformanceByIdTest() {

        PerformanceGetResponseDto performanceGetResponseDto = PerformanceGetResponseDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .build();

        doReturn(Optional.of(performance)).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);
        doReturn(performanceGetResponseDto).when(modelMapper).map(performance, PerformanceGetResponseDto.class);

        PerformanceGetResponseDto result = performanceService.getPerformanceById(performanceId);

        assertThat(result.getTitle()).isEqualTo(performance.getTitle());
    }

    @Test
    @DisplayName("존재하지 않는 공연 ID가 주어졌을 때, ResourceNotFoundException 발생")
    void getPerformanceByIdFailureTest() {

        doThrow(ResourceNotFoundException.class).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);

        assertThatThrownBy(() -> performanceService.getPerformanceById(performanceId))
            .isInstanceOf(ResourceNotFoundException.class);
    }

}