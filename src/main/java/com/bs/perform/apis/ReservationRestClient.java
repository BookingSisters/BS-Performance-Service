package com.bs.perform.apis;

import com.bs.perform.dtos.response.CommonResponseDto;
import com.bs.perform.dtos.response.SessionSeatRequestDto;
import com.bs.perform.exceptions.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationRestClient {

    private final RestTemplate restTemplate;

    @Value("${external.service.reservation.url}")
    private String externalServiceReservationUrl;

    public void sendSessionSeatGradeToReservation(final Long performanceId, final SessionSeatRequestDto requestDto) {

        log.info("sendSessionSeatGradeToReservation with ID: {}, SessionSeatRequestDto: {}", performanceId, requestDto);

        String url = externalServiceReservationUrl + "/seats/batch";

        ResponseEntity<CommonResponseDto> response = restTemplate.postForEntity(url, requestDto, CommonResponseDto.class);
        validateResponse(response);

    }

    private static void validateResponse(ResponseEntity<CommonResponseDto> response) {
        if(response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            throw new ExternalServiceException("Sessions seats not created. HTTP Status: " + response.getStatusCode());
        }
    }

}
