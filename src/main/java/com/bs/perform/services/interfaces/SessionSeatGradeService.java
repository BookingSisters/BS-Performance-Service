package com.bs.perform.services.interfaces;

import com.bs.perform.dtos.response.SessionSeatDto;
import java.util.List;

public interface SessionSeatGradeService {

    List<SessionSeatDto> getSessionSeatGradeById(final Long performanceId);
}
