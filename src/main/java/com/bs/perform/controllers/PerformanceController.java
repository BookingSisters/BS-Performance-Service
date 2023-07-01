package com.bs.perform.controllers;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceCreateResponseDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.dtos.response.PerformanceUpdateResponseDto;
import com.bs.perform.services.PerformanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public PerformanceCreateResponseDto createPerformance(
        @RequestBody @Valid final PerformanceCreateDto performanceCreateDto) {
        performanceService.createPerformance(performanceCreateDto);
        return new PerformanceCreateResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerformanceUpdateResponseDto updatePerformance(
        @PathVariable final String id,
        @RequestBody @Valid final PerformanceUpdateDto performanceUpdateDto) {
        performanceService.updatePerformance(id, performanceUpdateDto);
        return new PerformanceUpdateResponseDto(HttpStatus.OK.toString(), "successfully updated");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PerformanceGetResponseDto getPerformance(@PathVariable final String id) {
        return performanceService.getPerformanceById(id);
    }

}
