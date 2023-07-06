package com.bs.perform.controllers;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.services.VenueService;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping("/venues")
public class VenueController {

    private final VenueService venueService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CommonResponseDto createVenue(
        @RequestBody @Valid final VenueCreateDto venueCreateDto) {
        venueService.createVenue(venueCreateDto);
        return new CommonResponseDto(HttpStatus.CREATED.toString(), "successfully created");
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto updateVenue(
        @PathVariable final Long id,
        @RequestBody @Valid final VenueUpdateDto venueUpdateDto) {
        venueService.updateVenue(id, venueUpdateDto);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully updated");
    }

    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommonResponseDto deleteVenue(
        @PathVariable final Long id) {
        venueService.deleteVenue(id);
        return new CommonResponseDto(HttpStatus.OK.toString(), "successfully deleted");
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VenueGetResponseDto getVenue(@PathVariable final Long id) {
        return venueService.getVenueById(id);
    }

}
