package com.bs.perform.dtos.response;

import lombok.*;


@ToString
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
