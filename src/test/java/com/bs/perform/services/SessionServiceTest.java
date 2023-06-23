package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.GradeCreateDto;
import com.bs.perform.dtos.request.GradeUpdateDto;
import com.bs.perform.dtos.request.SeatCreateDto;
import com.bs.perform.dtos.request.SeatUpdateDto;
import com.bs.perform.dtos.request.SessionCreateDto;
import com.bs.perform.dtos.request.SessionUpdateDto;
import com.bs.perform.dtos.response.GradeGetResponseDto;
import com.bs.perform.dtos.response.SeatGetResponseDto;
import com.bs.perform.dtos.response.SessionGetResponseDto;
import com.bs.perform.enums.GradeType;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Seat;
import com.bs.perform.models.Session;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.GradeRepository;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.SeatRepository;
import com.bs.perform.repositories.SessionRepository;
import com.bs.perform.repositories.VenueRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @InjectMocks
    SessionServiceImpl sessionService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    SessionRepository sessionRepository;
    @Mock
    PerformanceRepository performanceRepository;

    Venue venue;
    Long performanceId;
    Performance performance;
    Long sessionId;
    Session session;

    @BeforeEach
    public void setup() {
        venue = getVenue();
        performanceId = 123L;
        performance = getPerformance();
        sessionId = 123L;
        session = getSession();
    }

    @Nested
    @DisplayName("공연 회차 생성 테스트")
    class createTest {

        @Test
        @DisplayName("유효한 SessionCreateDto가 주어졌을 때, 정상적으로 생성")
        void createGradeTest() {

            SessionCreateDto createDto = SessionCreateDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .sessionTime(LocalTime.of(20,00,00))
                .performanceId(performanceId)
                .build();

            doReturn(Optional.of(performance)).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);
            doReturn(session).when(sessionRepository).save(any(Session.class));

            sessionService.createSession(createDto);

            verify(sessionRepository, times(1)).save(any(Session.class));
        }

        @Test
        @DisplayName("유효하지 않은 SessionCreateDto가 주어졌을 때, NullPointerException 발생")
        void createGradeFailureTest1() {

            SessionCreateDto createDto = SessionCreateDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .performanceId(performanceId)
                .build();

            doReturn(Optional.of(performance)).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);

            AssertionsForClassTypes.assertThatThrownBy(() -> sessionService.createSession(createDto))
                .isInstanceOf(NullPointerException.class);
        }

    }

    @Nested
    @DisplayName("공연 회차 수정 테스트")
    class updateTest {

        @Test
        @DisplayName("유효한 ID와 SessionUpdateDto가 주어졌을 때, 정상적으로 업데이트")
        void updatePerformanceTest() {

            SessionUpdateDto updateDto = SessionUpdateDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .sessionTime(LocalTime.of(20,00,00))
                .build();

            doReturn(Optional.of(session)).when(sessionRepository).findByIdAndIsDeletedFalse(sessionId);

            sessionService.updateSession(sessionId, updateDto);

            verify(sessionRepository, times(1)).findByIdAndIsDeletedFalse(sessionId);

        }

        @Test
        @DisplayName("유효하지 않은 SessionUpdateDto가 주어졌을 때, NullPointerException이 발생")
        void updatePerformanceFailTest() {

            SessionUpdateDto updateDto = SessionUpdateDto.builder().build();

            doReturn(Optional.of(session)).when(sessionRepository).findByIdAndIsDeletedFalse(sessionId);

            AssertionsForClassTypes.assertThatThrownBy(() -> sessionService.updateSession(sessionId, updateDto))
                .isInstanceOf(NullPointerException.class);
        }

    }

    @Nested
    @DisplayName("공연 회차 조회 테스트")
    class getTest {

        @Test
        @DisplayName("유효한 ID가 주어졌을 때, SessionGetResponseDto를 반환")
        void getPerformanceByIdTest_found() {

            SessionGetResponseDto responseDto = SessionGetResponseDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .sessionTime(LocalTime.of(20,00,00))
                .build();

            doReturn(Optional.of(session)).when(sessionRepository).findByIdAndIsDeletedFalse(sessionId);
            doReturn(responseDto).when(modelMapper).map(session, SessionGetResponseDto.class);

            SessionGetResponseDto result = sessionService.getSessionById(sessionId);

            assertThat(result.getSessionDate()).isEqualTo(session.getSessionDate());
        }

        @Test
        @DisplayName("존재하지 않는 공연 회차 ID가 주어졌을 때, ResourceNotFoundException이 발생")
        void getPerformanceByIdTest_notFound() {

            doThrow(ResourceNotFoundException.class).when(sessionRepository).findByIdAndIsDeletedFalse(sessionId);

            AssertionsForClassTypes.assertThatThrownBy(() -> sessionService.getSessionById(sessionId))
                .isInstanceOf(ResourceNotFoundException.class);
        }
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

    private Session getSession() {
        return Session.builder()
            .sessionDate(LocalDate.of(2023, 7, 15))
            .sessionTime(LocalTime.of(20,00,00))
            .performance(performance)
            .build();
    }
}