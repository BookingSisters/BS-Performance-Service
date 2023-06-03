package com.bs.perform.controllers;

import com.bs.perform.models.Performance;
import com.bs.perform.services.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService dynamoDbService;

    @PostMapping("/performance")
    public String createPerformance(@RequestBody Performance performance) {
        dynamoDbService.createPerformance(performance);
        return "Successfully put person with id: " + performance.getId();
    }

    @GetMapping("/{id}")
    public Performance getPerformance(@PathVariable String id) {
        return dynamoDbService.getPerformance(id);
    }
}
