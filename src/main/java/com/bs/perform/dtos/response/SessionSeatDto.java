package com.bs.perform.dtos.response;

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
public class SessionSeatDto {

    private Long performanceId;

    private Long sessionId;

    private Long seatGradeId;

}
