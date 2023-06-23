package com.bs.perform.controllers;

import com.bs.perform.dtos.*;
import com.bs.perform.services.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @PutMapping("")
    public ResponseEntity<PerformanceCreateResponseDto> createPerformance(@RequestBody PerformanceCreateRequestDto performanceCreateRequestDto) {
        performanceService.createPerformance(performanceCreateRequestDto);
        return new ResponseEntity<>(new PerformanceCreateResponseDto("200", "successfully created"), HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<PerformanceUpdateResponseDto> updatePerformance(@PathVariable String id, @RequestBody PerformanceUpdateRequestDto performanceUpdateRequestDto) {
        performanceService.updatePerformance(id, performanceUpdateRequestDto);
        return new ResponseEntity<>(new PerformanceUpdateResponseDto("200", "successfully updated"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceGetResponseDto> getPerformance(@PathVariable String id) {
        PerformanceGetResponseDto performanceGetResponseDto = performanceService.getPerformanceById(id);
        return new ResponseEntity<>(performanceGetResponseDto, HttpStatus.OK);
    }

}
