package com.bs.perform.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.response.SessionSeatDto;
import com.bs.perform.dtos.response.SessionSeatRequestDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.services.interfaces.SessionSeatGradeService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestClientException;

@SpringBootTest
@AutoConfigureMockMvc
class SessionSeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionSeatGradeService sessionSeatGradeService;

    private Long performanceId;
    private SessionSeatDto sessionSeatDto;

    @BeforeEach
    public void setUp() {

        performanceId = 123L;
        sessionSeatDto = new SessionSeatDto();
    }

    @Test
    @DisplayName("유효한 ID로 세션 좌석 등급 정보 조회 시 성공")
    void getSessionSeatGradeSuccessTest() throws Exception {

        List<SessionSeatDto> expectedResponse = Arrays.asList(sessionSeatDto);
        doReturn(expectedResponse).when(sessionSeatGradeService).getSessionSeatGradeById(
            performanceId);

        mockMvc.perform(get("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(1)));

        verify(sessionSeatGradeService, times(1)).getSessionSeatGradeById(performanceId);
    }

    @Test
    @DisplayName("유효하지 않은 ID로 세션 좌석 등급 정보 조회 실패 시 BadRequest 반환")
    void getSessionSeatGradeFailureTest() throws Exception {

        doThrow(ResourceNotFoundException.class).when(sessionSeatGradeService)
            .getSessionSeatGradeById(
                performanceId);

        mockMvc.perform(get("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verify(sessionSeatGradeService, times(1)).getSessionSeatGradeById(performanceId);
    }

    @Test
    @DisplayName("유효한 ID로 세션 좌석 등급 정보 전송 시 성공")
    void sendSessionSeatGradeSuccessTest() throws Exception {

        List<SessionSeatDto> sessionSeatDtos = Arrays.asList(this.sessionSeatDto);
        SessionSeatRequestDto sessionSeatRequestDto = new SessionSeatRequestDto(sessionSeatDtos,
            "success", "");

        doReturn(sessionSeatRequestDto).when(sessionSeatGradeService)
            .sendSessionSeatGradeToReservation(performanceId);

        mockMvc.perform(post("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("좌석 등급 정보 전송 실패 시 InternalServerError 반환")
    void sendSessionSeatGradeFailureTest() throws Exception {

        doThrow(RestClientException.class).when(sessionSeatGradeService)
            .sendSessionSeatGradeToReservation(performanceId);

        mockMvc.perform(post("/performances/" + performanceId + "/sessionSeats")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());
    }
}
