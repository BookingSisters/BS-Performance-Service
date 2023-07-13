package com.bs.perform.apis;

import com.bs.perform.dtos.response.SessionSeatRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationRestClient {

    private final RestTemplate restTemplate;

    @Value("${external.service.reservation.url}")
    private String externalServiceReservationUrl;

    public SessionSeatRequestDto sendSessionSeatGradeToReservation(final Long performanceId, final SessionSeatRequestDto requestDto) {

        log.info("sendSessionSeatGradeToReservation with ID: {}, SessionSeatRequestDto: {}", performanceId, requestDto);

        String url = externalServiceReservationUrl + "/seats/" + performanceId;
        return restTemplate.postForObject(url, requestDto, SessionSeatRequestDto.class);
    }

}
