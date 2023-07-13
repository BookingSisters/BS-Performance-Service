package com.bs.perform.dtos.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
