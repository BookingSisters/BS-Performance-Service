package com.bs.perform.controllers;

import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.dtos.response.SessionSeatRequestDto;
import com.bs.perform.services.interfaces.SessionSeatGradeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionSeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private SessionSeatGradeService sessionSeatGradeService;

    private Long performanceId;
    private SessionSeatDto testDto;

    @BeforeEach
    public void setUp() {
        performanceId = 123L;

        testDto = new SessionSeatDto();
    }

    @Test
    @DisplayName("유효한 ID로 세션 좌석 등급 정보 조회 시 성공")
    void getSessionSeatGradeSuccessTest() throws Exception {
        List<SessionSeatDto> expectedResponse = Arrays.asList(testDto);
        doReturn(expectedResponse).when(sessionSeatGradeService).getSessionSeatGradeById(
            performanceId);

        mockMvc.perform(get("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        verify(sessionSeatGradeService, times(1)).getSessionSeatGradeById(performanceId);
    }

    @Test
    @DisplayName("유효하지 않은 ID로 세션 좌석 등급 정보 조회 시 실패")
    void getSessionSeatGradeFailureTest() throws Exception {
        doReturn(Collections.emptyList()).when(sessionSeatGradeService).getSessionSeatGradeById(
            performanceId);

        mockMvc.perform(get("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(0)));

        verify(sessionSeatGradeService, times(1)).getSessionSeatGradeById(performanceId);
    }

    @Test
    @DisplayName("유효한 ID로 세션 좌석 등급 정보 전송 시 성공")
    void sendSessionSeatGradeSuccessTest() throws Exception {
        List<SessionSeatDto> sessionSeatDto = Arrays.asList(testDto);
        String url = "/seats/" + performanceId;
        SessionSeatRequestDto sessionSeatRequestDto = new SessionSeatRequestDto(sessionSeatDto);

        doReturn(sessionSeatDto).when(sessionSeatGradeService).getSessionSeatGradeById(performanceId);
        doReturn(sessionSeatRequestDto).when(restTemplate).postForObject(url, sessionSeatRequestDto, SessionSeatRequestDto.class);

        mockMvc.perform(post("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}

