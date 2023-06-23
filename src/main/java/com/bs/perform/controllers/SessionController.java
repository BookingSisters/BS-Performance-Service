package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.request.SessionCreateDto;
import com.bs.perform.dtos.request.SessionUpdateDto;
import com.bs.perform.dtos.response.SessionGetResponseDto;
import com.bs.perform.services.SessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessions")
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseDto createSession(
        @RequestBody @Valid final SessionCreateDto sessionCreateDto) {
        sessionService.createSession(sessionCreateDto);
        return new CommonResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto updateSession(
        @PathVariable final Long id,
        @RequestBody @Valid final SessionUpdateDto sessionUpdateDto) {
        sessionService.updateSession(id, sessionUpdateDto);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto deleteSession(
        @PathVariable final Long id) {
        sessionService.deleteSession(id);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully deleted");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SessionGetResponseDto getSession(@PathVariable final Long id) {
        return sessionService.getSessionById(id);
    }

}
