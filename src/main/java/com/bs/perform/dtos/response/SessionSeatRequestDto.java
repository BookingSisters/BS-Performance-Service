package com.bs.perform.dtos.response;

import java.util.List;

import lombok.*;


@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionSeatRequestDto {

    private List<SessionSeatDto> data;

    private String status;

    private String message;

}
