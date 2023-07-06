package com.bs.perform.dtos.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResultResponseDto {

    private final String code;
    private final String message;
}