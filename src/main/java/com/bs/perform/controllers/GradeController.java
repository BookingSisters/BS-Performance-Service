package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.request.GradeCreateDto;
import com.bs.perform.dtos.request.GradeUpdateDto;
import com.bs.perform.dtos.response.GradeGetResponseDto;
import com.bs.perform.services.interfaces.GradeService;
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
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseDto createGrade(
        @RequestBody @Valid final GradeCreateDto gradeCreateDto) {
        gradeService.createGrade(gradeCreateDto);
        return new CommonResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto updateGrade(
        @PathVariable final Long id,
        @RequestBody @Valid final GradeUpdateDto gradeUpdateDto) {
        gradeService.updateGrade(id, gradeUpdateDto);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto deleteGrade(
        @PathVariable final Long id) {
        gradeService.deleteGrade(id);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully deleted");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GradeGetResponseDto getGrade(@PathVariable final Long id) {
        return gradeService.getGradeById(id);
    }

}
