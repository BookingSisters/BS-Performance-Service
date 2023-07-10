package com.bs.perform.services;

import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.SeatGradeRepository;
import com.bs.perform.repositories.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

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

    Long performanceId;
    private Performance testPerformance;
    private Session testSession;
    private SeatGrade testSeatGrade;

    @BeforeEach
    public void setUp() {
        performanceId = 123L;

        testPerformance = new Performance();
        testSession = new Session();
        testSeatGrade = new SeatGrade();
    }

    @Test
    @DisplayName("유효한 performanceId가 주어졌을 때, SessionSeatResponseDto 목록을 반환")
    void getSessionSeatGradeByIdSuccessTest() {
        doReturn(Optional.of(testPerformance)).when(performanceRepository).findByIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(testSession)).when(sessionRepository).findByPerformanceIdAndIsDeletedFalse(anyLong());
        doReturn(Collections.singletonList(testSeatGrade)).when(seatGradeRepository).findGradeFetchJoinSeatGrade(any());

        List<SessionSeatDto> result = sessionSeatGradeService.getSessionSeatGradeById(performanceId);

        verify(performanceRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
        verify(sessionRepository, times(1)).findByPerformanceIdAndIsDeletedFalse(anyLong());
        verify(seatGradeRepository, times(1)).findGradeFetchJoinSeatGrade(any());
    }

    @Test
    @DisplayName("잘못된 performanceId가 주어졌을 때, getSessionSeatGradeById는 예외를 발생")
    void getSessionSeatGradeByIdFailureTest() {
        doReturn(Optional.empty()).when(performanceRepository).findByIdAndIsDeletedFalse(anyLong());

        assertThatThrownBy(() -> sessionSeatGradeService.getSessionSeatGradeById(performanceId))
            .isInstanceOf(ResourceNotFoundException.class);

        verify(performanceRepository, times(1)).findByIdAndIsDeletedFalse(anyLong());
    }
}
