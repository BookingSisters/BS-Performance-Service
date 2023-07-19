package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.services.interfaces.SessionSeatGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        sessionSeatGradeService.sendSessionSeatGradeToReservation(id);

        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully sent");
    }

}
