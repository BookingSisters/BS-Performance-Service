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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping("")
    public ResponseEntity<PerformanceCreateResponseDto> createPerformance(
        @RequestBody @Valid final PerformanceCreateDto performanceCreateDto) {
        performanceService.createPerformance(performanceCreateDto);
        return new ResponseEntity<>(
            new PerformanceCreateResponseDto(HttpStatus.CREATED.toString(), "successfully created"),
            HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerformanceUpdateResponseDto> updatePerformance(
        @PathVariable final String id,
        @RequestBody @Valid final PerformanceUpdateDto performanceUpdateDto) {
        performanceService.updatePerformance(id, performanceUpdateDto);
        return new ResponseEntity<>(
            new PerformanceUpdateResponseDto(HttpStatus.OK.toString(), "successfully updated"),
            HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceGetResponseDto> getPerformance(@PathVariable final String id) {
        PerformanceGetResponseDto performanceGetResponseDto = performanceService.getPerformanceById(
            id);
        return new ResponseEntity<>(performanceGetResponseDto, HttpStatus.OK);
    }

}
