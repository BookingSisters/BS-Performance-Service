package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.enums.Grade;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.performance.Performance;
import com.bs.perform.models.performance.SeatGrade;
import com.bs.perform.models.performance.Session;
import com.bs.perform.repositories.PerformanceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
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
class PerformanceServiceImplTest {

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private ModelMapper modelMapper;

    List<SeatGrade> seatGradeList;
    List<Session> sessionList;

    @BeforeEach
    public void setup() {
        seatGradeList = getSeatGradeList();
        sessionList = getSessionList();
    }

    @Test
    @DisplayName("유효한 PerformanceCreateDto가 주어졌을 때, 정상적으로 공연을 저장")
    void createPerformanceTest() {

        PerformanceCreateDto performanceCreateDto = getPerformanceCreateDto();

        doNothing().when(performanceRepository).createPerformance(any(Performance.class));

        performanceService.createPerformance(performanceCreateDto);

        verify(performanceRepository, times(1)).createPerformance(any(Performance.class));
    }

    @Test
    @DisplayName("유효하지 않은 PerformanceCreateDto가 주어졌을 때, NullPointerException이 발생")
    void createPerformanceFailTest() {

        PerformanceCreateDto performanceCreateDto = getPerformanceCreateFailDto();

        assertThatThrownBy(() -> performanceService.createPerformance(performanceCreateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("유효한 ID와 PerformanceUpdateDto가 주어졌을 때, 정상적으로 공연을 업데이트")
    void updatePerformanceTest() {

        String id = "123";
        PerformanceUpdateDto performanceUpdateDto = getPerformanceUpdateDto();

        doNothing().when(performanceRepository).updatePerformance(eq(id), any(Performance.class));

        performanceService.updatePerformance(id, performanceUpdateDto);

        verify(performanceRepository, times(1)).updatePerformance(eq(id), any(Performance.class));
    }

    @Test
    @DisplayName("유효하지 않은 PerformanceUpdateDto가 주어졌을 때, NullPointerException이 발생")
    void updatePerformanceFailTest() {

        String id = "123";
        PerformanceUpdateDto performanceUpdateDto = getPerformanceUpdateFailDto();

        assertThatThrownBy(() -> performanceService.updatePerformance(id, performanceUpdateDto))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("기존에 존재하는 공연 ID가 주어졌을 때, PerformanceGetResponseDto를 반환")
    void getPerformanceByIdTest_found() {

        String id = "123";
        Performance performance = getPerformance();
        PerformanceGetResponseDto performanceGetResponseDto = getPerformanceGetResponseDto();

        doReturn(performance).when(performanceRepository).getPerformanceById(id);
        doReturn(performanceGetResponseDto).when(modelMapper)
            .map(performance, PerformanceGetResponseDto.class);

        PerformanceGetResponseDto performanceById = performanceService.getPerformanceById(id);

        assertThat(performanceById.getTitle()).isEqualTo(performance.getTitle());
    }

    @Test
    @DisplayName("존재하지 않는 공연 ID가 주어졌을 때, ResourceNotFoundException이 발생")
    void getPerformanceByIdTest_notFound() {

        String id = "123";

        doThrow(ResourceNotFoundException.class).when(performanceRepository).getPerformanceById(id);

        assertThatThrownBy(() -> performanceService.getPerformanceById(id))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    private List<SeatGrade> getSeatGradeList() {
        List<String> seatList = Arrays.asList("seat1", "seat2", "seat3");
        SeatGrade seatGradeVip = SeatGrade.builder().grade(Grade.VIP).price(new BigDecimal(200000))
            .seatCount(100).seatList(seatList).build();
        SeatGrade seatGradeR = SeatGrade.builder().grade(Grade.S).price(new BigDecimal(100000))
            .seatCount(200).seatList(seatList).build();
        return Arrays.asList(seatGradeVip, seatGradeR);
    }

    private static List<Session> getSessionList() {
        Session session1 = Session.builder().sessionDate(LocalDate.of(2023, 6, 24))
            .sessionTime(LocalTime.of(13, 0)).performers(Arrays.asList("ActorA", "ActorB")).build();
        Session session2 = Session.builder().sessionDate(LocalDate.of(2023, 6, 24))
            .sessionTime(LocalTime.of(17, 0)).performers(Arrays.asList("ActorC", "ActorD")).build();
        return Arrays.asList(session1, session2);
    }

    private PerformanceCreateDto getPerformanceCreateDto() {
        return PerformanceCreateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .totalSeatCount(300)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId("Jamsil Sports Complex")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
            .build();
    }

    private PerformanceCreateDto getPerformanceCreateFailDto() {
        return PerformanceCreateDto.builder()
            .description("This is BTS 2023 concert")
            .build();
    }

    private PerformanceUpdateDto getPerformanceUpdateDto() {
        return PerformanceUpdateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .totalSeatCount(300)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId("1")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
            .build();
    }

    private PerformanceUpdateDto getPerformanceUpdateFailDto() {
        return PerformanceUpdateDto.builder()
            .description("This is BTS 2023 concert")
            .build();
    }

    private PerformanceGetResponseDto getPerformanceGetResponseDto() {
        return PerformanceGetResponseDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .totalSeatCount(300)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId("1")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
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
            .venueId("1")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
            .build();
    }

}