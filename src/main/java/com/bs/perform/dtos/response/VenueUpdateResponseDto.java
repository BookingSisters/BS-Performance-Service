package com.bs.perform.dtos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class VenueUpdateResponseDto {

    private final String status;
    private final String message;
}
