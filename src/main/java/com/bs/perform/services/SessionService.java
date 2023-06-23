package com.bs.perform.services;

import com.bs.perform.dtos.request.SessionCreateDto;
import com.bs.perform.dtos.request.SessionUpdateDto;
import com.bs.perform.dtos.response.SessionGetResponseDto;

public interface SessionService {

    void createSession(final SessionCreateDto sessionDto);

    void updateSession(final Long id, final SessionUpdateDto sessionDto);

    void deleteSession(final Long id);

    SessionGetResponseDto getSessionById(final Long id);
}
