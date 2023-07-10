package com.bs.perform.services;

import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.SeatGradeRepository;
import com.bs.perform.repositories.SessionRepository;
import com.bs.perform.services.interfaces.SessionSeatGradeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SessionSeatGradeServiceImpl implements SessionSeatGradeService {

    private final PerformanceRepository performanceRepository;
    private final SessionRepository sessionRepository;
    private final SeatGradeRepository seatGradeRepository;


    @Override
    public List<SessionSeatDto> getSessionSeatGradeById(final Long performanceId) {

        log.info("getSessionSeatGradeById with ID: {}", performanceId);
        Performance performance = getPerformance(performanceId);

        List<Session> sessions = findSessions(performanceId);
        List<SeatGrade> seatGrades = findSeatGrades(performance);

        return sessions.stream()
            .flatMap(session -> seatGrades.stream()
                .map(seatGrade -> SessionSeatDto.builder()
                    .performanceId(performanceId)
                    .sessionId(session.getId())
                    .seatGradeId(seatGrade.getId())
                    .build()))
            .collect(Collectors.toList());
    }

    private Performance getPerformance(Long performanceId) {
        return performanceRepository.findByIdAndIsDeletedFalse(performanceId)
            .orElseThrow(
                () -> new ResourceNotFoundException("Performance", String.valueOf(performanceId)));
    }

    private List<Session> findSessions(Long performanceId) {
        List<Session> sessions = sessionRepository.findByPerformanceIdAndIsDeletedFalse(
            performanceId);
        log.info("Fetched {} sessions objects for performanceId: {}", sessions.size(),
            performanceId);
        if (sessions.isEmpty()) {
            throw new ResourceNotFoundException("sessions of performance",
                String.valueOf(performanceId));
        }
        return sessions;
    }

    private List<SeatGrade> findSeatGrades(Performance performance) {
        List<SeatGrade> seatGrades = seatGradeRepository.findGradeFetchJoinSeatGrade(performance);
        log.info("Fetched {} seatGrades objects for performanceId: {}", seatGrades.size(),
            performance.getId());
        if (seatGrades.isEmpty()) {
            throw new ResourceNotFoundException("seatGrades of performance",
                String.valueOf(performance.getId()));
        }
        return seatGrades;
    }

}
