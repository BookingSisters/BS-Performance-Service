package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.dtos.response.SessionSeatRequestDto;
import com.bs.perform.services.interfaces.SessionSeatGradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performances")
public class SessionSeatController {

    private final SessionSeatGradeService sessionSeatGradeService;

    @GetMapping("/{id}/sessionSeats")
    @ResponseStatus(HttpStatus.OK)
    public List<SessionSeatDto> getSessionSeatGrade(@PathVariable final Long id) {

        return sessionSeatGradeService.getSessionSeatGradeById(id);
    }

    @PostMapping("/{id}/sessionSeats")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto sendSessionSeatGradeToReservation(@PathVariable final Long id) {

        SessionSeatRequestDto sessionSeatRequestDto = sessionSeatGradeService.sendSessionSeatGradeToReservation(
            id);

        if (!sessionSeatRequestDto.getStatus().equals("success")) {
            throw new RestClientException("Sessions seats not created");
        }

        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully sent");
    }

}
