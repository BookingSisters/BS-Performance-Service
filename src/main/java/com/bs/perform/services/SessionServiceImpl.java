package com.bs.perform.services;

import com.bs.perform.dtos.request.SessionCreateDto;
import com.bs.perform.dtos.request.SessionUpdateDto;
import com.bs.perform.dtos.response.SessionGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Session;
import com.bs.perform.repositories.PerformanceRepository;
import com.bs.perform.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SessionServiceImpl implements SessionService {

    private final ModelMapper modelMapper;
    private final SessionRepository sessionRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    @Transactional
    public void createSession(final SessionCreateDto sessionDto) {

        log.info("Creating session with SessionCreateDto: {}", sessionDto);

        Performance performance = getPerformance(sessionDto.getPerformanceId());
        Session session = sessionDto.toEntity(performance);

        sessionRepository.save(session);
    }

    @Override
    @Transactional
    public void updateSession(final Long id, final SessionUpdateDto sessionDto) {

        log.info("Updating session with SessionUpdateDto: {}", sessionDto);
        Session session = getSession(id);

        session.updateSession(sessionDto.getSessionDate(), sessionDto.getSessionTime(), sessionDto.getPerformers());
    }

    @Override
    @Transactional
    public void deleteSession(final Long id) {

        log.info("deleteSession with ID: {}", id);
        Session session = getSession(id);

        session.deleteSession();
    }

    @Override
    public SessionGetResponseDto getSessionById(final Long id) {

        log.info("GetSession with ID: {}", id);

        Session session = getSession(id);

        return modelMapper.map(session, SessionGetResponseDto.class);
    }

    private Performance getPerformance(Long id) {
        return performanceRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Performance", String.valueOf(id)));
    }

    private Session getSession(Long id) {
        return sessionRepository.findByIdAndIsDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("Session", String.valueOf(id)));
    }

}
