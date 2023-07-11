package com.bs.perform.services.interfaces;

import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.dtos.response.SessionSeatRequestDto;
import java.util.List;

public interface SessionSeatGradeService {

    List<SessionSeatDto> getSessionSeatGradeById(final Long performanceId);

    SessionSeatRequestDto sendSessionSeatGradeToReservation(final Long performanceId);
}
