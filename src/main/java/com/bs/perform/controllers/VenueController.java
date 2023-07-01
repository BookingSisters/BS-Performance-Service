package com.bs.perform.controllers;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.PerformanceCreateResponseDto;
import com.bs.perform.dtos.response.PerformanceUpdateResponseDto;
import com.bs.perform.dtos.response.VenueCreateResponseDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.dtos.response.VenueUpdateResponseDto;
import com.bs.perform.services.VenueService;
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
@RequestMapping("/venue")
public class VenueController {

    private final VenueService venueService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public VenueCreateResponseDto createVenue(
        @RequestBody @Valid final VenueCreateDto venueCreateDto) {
        venueService.createVenue(venueCreateDto);
        return new VenueCreateResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VenueUpdateResponseDto updateVenue(
        @PathVariable final String id,
        @RequestBody @Valid final VenueUpdateDto venueUpdateDto) {
        venueService.updateVenue(id, venueUpdateDto);
        return new VenueUpdateResponseDto(HttpStatus.OK.toString(), "successfully updated");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VenueGetResponseDto getVenue(@PathVariable final String id) {
        return venueService.getVenueById(id);
    }

}
