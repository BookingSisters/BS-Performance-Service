package com.bs.perform.services;

import com.bs.perform.dtos.PerformanceCreateDto;
import com.bs.perform.dtos.PerformanceGetResponseDto;
import com.bs.perform.dtos.PerformanceUpdateDto;
import com.bs.perform.enums.Grade;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import com.bs.perform.repository.PerformanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerformanceServiceImplTest {

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    @Mock
    private PerformanceRepository performanceRepository;

    List<SeatGrade> seatGradeList;
    List<Session> sessionList;

    @BeforeEach
    public void setup() {
        seatGradeList = getSeatGradeList();
        sessionList = getSessionList();
    }


    @Test
    @DisplayName("공연 생성 테스트")
    void createPerformanceTest() {

        PerformanceCreateDto performanceCreateDto = getPerformanceCreateDto();

        doNothing().when(performanceRepository).createPerformance(any(Performance.class));

        performanceService.createPerformance(performanceCreateDto);

        verify(performanceRepository, times(1)).createPerformance(any(Performance.class));
    }

    @Test
    @DisplayName("공연 수정 테스트")
    void updatePerformanceTest() {

        String id = "123";
        PerformanceUpdateDto performanceUpdateDto = getPerformanceUpdateDto();

        doNothing().when(performanceRepository).updatePerformance(eq(id), any(Performance.class));

        performanceService.updatePerformance(id, performanceUpdateDto);

        verify(performanceRepository, times(1)).updatePerformance(eq(id), any(Performance.class));
    }

    @Test
    @DisplayName("공연 조회 성공 테스트")
    void getPerformanceByIdTest_found() {

        String id = "123";
        Performance performance = getPerformance();

        doReturn(performance).when(performanceRepository).getPerformanceById(id);

        PerformanceGetResponseDto performanceById = performanceService.getPerformanceById(id);

        assertThat(performanceById.getTitle()).isEqualTo(performance.getTitle());
    }

    @Test
    @DisplayName("공연 조회 실패 테스트")
    void getPerformanceByIdTest_notFound() {
        String id = "123";

        doReturn(null).when(performanceRepository).getPerformanceById(id);

        assertThatThrownBy(() -> performanceService.getPerformanceById(id))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    private static List<SeatGrade> getSeatGradeList() {

        SeatGrade seatGradeVip = SeatGrade.builder().grade(Grade.VIP).price(new BigDecimal(200000)).seatCount(100).build();
        SeatGrade seatGradeR = SeatGrade.builder().grade(Grade.S).price(new BigDecimal(100000)).seatCount(200).build();
        return Arrays.asList(seatGradeVip, seatGradeR);
    }

    private static List<Session> getSessionList() {
        Session session1 = Session.builder().sessionDate(LocalDate.of(2023,6,24)).sessionTime(LocalTime.of(13,0)).performers(Arrays.asList("ActorA", "ActorB")).build();
        Session session2 = Session.builder().sessionDate(LocalDate.of(2023,6,24)).sessionTime(LocalTime.of(17,0)).performers(Arrays.asList("ActorC", "ActorD")).build();
        return Arrays.asList(session1, session2);
    }

    private PerformanceCreateDto getPerformanceCreateDto() {
        return PerformanceCreateDto.builder()
                .title("BTS 2023 concert")
                .description("This is BTS 2023 concert")
                .runTime(100)
                .totalSeatCount(300)
                .reservationStartDate(LocalDate.of(2023, 6, 15))
                .reservationEndDate(LocalDate.of(2023, 7, 15))
                .performanceStartDate(LocalDate.of(2023, 7, 22))
                .performanceEndDate(LocalDate.of(2023, 7, 23))
                .location("Jamsil Sports Complex")
                .seatGradeList(seatGradeList)
                .sessionList(sessionList)
                .build();
    }

    private PerformanceUpdateDto getPerformanceUpdateDto() {
        return PerformanceUpdateDto.builder()
                .title("BTS 2023 concert")
                .description("This is BTS 2023 concert")
                .runTime(100)
                .totalSeatCount(300)
                .reservationStartDate(LocalDate.of(2023, 6, 15))
                .reservationEndDate(LocalDate.of(2023, 7, 15))
                .performanceStartDate(LocalDate.of(2023, 7, 22))
                .performanceEndDate(LocalDate.of(2023, 7, 23))
                .location("Jamsil Sports Complex")
                .seatGradeList(seatGradeList)
                .sessionList(sessionList)
                .build();
    }

    private Performance getPerformance() {
        return Performance.builder()
                .title("BTS 2023 concert")
                .description("This is BTS 2023 concert")
                .runTime(100)
                .totalSeatCount(300)
                .reservationStartDate(LocalDate.of(2023, 6, 15))
                .reservationEndDate(LocalDate.of(2023, 7, 15))
                .performanceStartDate(LocalDate.of(2023, 7, 22))
                .performanceEndDate(LocalDate.of(2023, 7, 23))
                .location("Jamsil Sports Complex")
                .seatGradeList(seatGradeList)
                .sessionList(sessionList)
                .build();
    }

}