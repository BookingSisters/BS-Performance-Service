package com.bs.perform.dtos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class VenueCreateResponseDto {

    private final String status;
    private final String message;
}
