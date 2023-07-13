package com.bs.perform.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.request.SessionCreateDto;
import com.bs.perform.dtos.request.SessionUpdateDto;
import com.bs.perform.dtos.response.SessionGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Session;
import com.bs.perform.models.Venue;
import com.bs.perform.services.interfaces.SessionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SessionControllerTest {

    @MockBean
    private SessionService sessionService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long performanceId;
    Performance performance;
    Long sessionId;
    Session session;

    @BeforeEach
    public void setup() {
        performanceId = 123L;
        performance = getPerformance();
        sessionId = 123L;
        session = getSession();
    }

    @Nested
    @DisplayName("공연 회차 생성 테스트")
    class createTest {

        @Test
        @DisplayName("유효한 데이터로 공연 회차 생성 API 호출 시 성공")
        void createSessionTest() throws Exception {

            SessionCreateDto createDto = SessionCreateDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .sessionTime(LocalTime.of(20, 00, 00))
                .performers("배우1, 배우2, 배우3")
                .performanceId(performanceId)
                .build();

            doNothing().when(sessionService).createSession(createDto);

            mockMvc.perform(post("/sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("잘못된 데이터로 공연 회차 생성 API 호출 시 실패")
        void createSessionFailureTest() throws Exception {

            SessionCreateDto createDto = SessionCreateDto.builder().build();

            doThrow(NullPointerException.class).when(sessionService).createSession(createDto);

            mockMvc.perform(post("/sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("공연 회차 수정 테스트")
    class updateTest {

        @Test
        @DisplayName("유효한 데이터로 공연 회차 업데이트 API 호출 시 성공")
        void updateSessionTest() throws Exception {

            SessionUpdateDto updateDto = SessionUpdateDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .sessionTime(LocalTime.of(20, 00, 00))
                .performers("배우1, 배우2, 배우3")
                .build();

            doNothing().when(sessionService).updateSession(sessionId, updateDto);

            mockMvc.perform(put("/sessions/" + sessionId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("잘못된 데이터로 공연 회차 업데이트 API 호출 시 실패")
        void updateSessionFailureTest() throws Exception {

            SessionUpdateDto updateDto = SessionUpdateDto.builder().build();

            doThrow(NullPointerException.class).when(sessionService)
                .updateSession(sessionId, updateDto);

            mockMvc.perform(put("/sessions/" + sessionId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("공연 회차 조회 테스트")
    class getTest {
        @Test
        @DisplayName("유효한 공연 아이디로 조회 API 호출 시 성공")
        void getSessionTest() throws Exception {

            SessionGetResponseDto responseDto = SessionGetResponseDto.builder()
                .sessionDate(LocalDate.of(2023, 7, 15))
                .sessionTime(LocalTime.of(20, 00, 00))
                .performers("배우1, 배우2, 배우3")
                .performanceId(performanceId)
                .build();

            doReturn(responseDto).when(sessionService).getSessionById(sessionId);

            mockMvc.perform(get("/sessions/" + sessionId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }

        @Test
        @DisplayName("존재하지 않는 아이디로 공연 조회 API 호출 시 실패")
        void getSessionFailureTest() throws Exception {

            doThrow(ResourceNotFoundException.class).when(sessionService)
                .getSessionById(sessionId);

            mockMvc.perform(get("/sessions/" + sessionId)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        }
    }

    private Performance getPerformance() {

        Venue venue = Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        return Performance.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venue(venue)
            .build();
    }

    private Session getSession() {
        return Session.builder()
            .sessionDate(LocalDate.of(2023, 7, 15))
            .sessionTime(LocalTime.of(20, 00, 00))
            .performers("배우1, 배우2, 배우3")
            .performance(performance)
            .build();
    }
}