package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.request.SeatGradeCreateDto;
import com.bs.perform.dtos.response.SeatGradeGetResponseDto;
import com.bs.perform.services.interfaces.SeatGradeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seatGrades")
public class SeatGradeController {

    private final SeatGradeService seatGradeService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseDto createGrade(
        @RequestBody @Valid final SeatGradeCreateDto seatGradeCreateDto) {
        seatGradeService.createSeatGrade(seatGradeCreateDto);
        return new CommonResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto deleteGrade(
        @PathVariable final Long id) {
        seatGradeService.deleteSeatGrade(id);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully deleted");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SeatGradeGetResponseDto getGrade(@PathVariable final Long id) {
        return seatGradeService.getSeatGradeById(id);
    }

}
