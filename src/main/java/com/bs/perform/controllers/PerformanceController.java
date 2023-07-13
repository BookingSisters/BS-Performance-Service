package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.services.interfaces.PerformanceService;
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
@RequestMapping("/performances")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseDto createPerformance(
        @RequestBody @Valid final PerformanceCreateDto performanceCreateDto) {
        performanceService.createPerformance(performanceCreateDto);
        return new CommonResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto updatePerformance(
        @PathVariable final Long id,
        @RequestBody @Valid final PerformanceUpdateDto performanceUpdateDto) {
        performanceService.updatePerformance(id, performanceUpdateDto);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully updated");
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto deletePerformance(
        @PathVariable final Long id) {
        performanceService.deletePerformance(id);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully deleted");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerformanceGetResponseDto getPerformance(@PathVariable final Long id) {
        return performanceService.getPerformanceById(id);
    }

}
