package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.apis.ReservationRestClient;
import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.dtos.response.SessionSeatRequestDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.SeatGradeRepository;
import com.bs.perform.repositories.SessionRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;

@ExtendWith(MockitoExtension.class)
public class SessionSeatGradeServiceImplTest {

    @InjectMocks
    private SessionSeatGradeServiceImpl sessionSeatGradeService;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SeatGradeRepository seatGradeRepository;

    @Mock
    private ReservationRestClient reservationRestClient;

    Long performanceId;
    private Performance performance;
    private Session session;
    private SeatGrade seatGrade;

    @BeforeEach
    public void setUp() {
        performanceId = 123L;

        performance = new Performance();
        session = new Session();
        seatGrade = new SeatGrade();
    }

    @Test
    @DisplayName("유효한 performanceId가 주어졌을 때, SessionSeatResponseDto 목록을 반환")
    void getSessionSeatGradeByIdSuccessTest() {

        doReturn(Optional.of(performance)).when(performanceRepository)
            .findByIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(session)).when(sessionRepository)
            .findByPerformanceIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(seatGrade)).when(seatGradeRepository)
            .findGradeFetchJoinSeatGrade(any());

        List<SessionSeatDto> result = sessionSeatGradeService.getSessionSeatGradeById(
            performanceId);

        verify(performanceRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
        verify(sessionRepository, times(1)).findByPerformanceIdAndIsDeletedFalse(anyLong());
        verify(seatGradeRepository, times(1)).findGradeFetchJoinSeatGrade(any());
    }

    @Test
    @DisplayName("잘못된 performanceId가 주어졌을 때, ResourceNotFoundException 예외를 발생")
    void getSessionSeatGradeByIdFailureTest() {

        doReturn(Optional.empty()).when(performanceRepository).findByIdAndIsDeletedFalse(anyLong());

        assertThatThrownBy(() -> sessionSeatGradeService.getSessionSeatGradeById(performanceId))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(performanceRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
    }

    @Test
    @DisplayName("유효한 performanceId가 주어졌을 때, SessionSeatResponseDto를 반환")
    void sendSessionSeatGradeToReservationSuccessTest() {

        SessionSeatDto sessionSeatDto = SessionSeatDto.builder().performanceId(1L).sessionId(1L)
            .seatGradeId(1L).build();
        List<SessionSeatDto> sessionSeatDtos = Arrays.asList(sessionSeatDto);
        SessionSeatRequestDto sessionSeatRequestDto = new SessionSeatRequestDto(sessionSeatDtos,
            "success", "");

        doReturn(Optional.of(performance)).when(performanceRepository)
            .findByIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(session)).when(sessionRepository)
            .findByPerformanceIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(seatGrade)).when(seatGradeRepository)
            .findGradeFetchJoinSeatGrade(any());
        doReturn(sessionSeatRequestDto).when(reservationRestClient)
            .sendSessionSeatGradeToReservation(anyLong(), any(SessionSeatRequestDto.class));

        SessionSeatRequestDto result = sessionSeatGradeService.sendSessionSeatGradeToReservation(
            performanceId);

        assertThat(result).isEqualTo(sessionSeatRequestDto);
        verify(reservationRestClient, times(1)).sendSessionSeatGradeToReservation(anyLong(),
            any(SessionSeatRequestDto.class));
    }

    @Test
    @DisplayName("좌석 등급 정보 전송 실패 시, RestClientException 예외 발생")
    void sendSessionSeatGradeToReservationFailureTest() {


        doReturn(Optional.of(performance)).when(performanceRepository)
            .findByIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(session)).when(sessionRepository)
            .findByPerformanceIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(seatGrade)).when(seatGradeRepository)
            .findGradeFetchJoinSeatGrade(any());
        doThrow(RestClientException.class).when(reservationRestClient)
            .sendSessionSeatGradeToReservation(anyLong(), any(SessionSeatRequestDto.class));

        assertThatThrownBy(
            () -> sessionSeatGradeService.sendSessionSeatGradeToReservation(performanceId))
            .isInstanceOf(RestClientException.class);
    }

}
