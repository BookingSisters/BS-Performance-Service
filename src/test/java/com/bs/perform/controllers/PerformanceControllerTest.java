package com.bs.perform.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Venue;
import com.bs.perform.services.interfaces.PerformanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PerformanceControllerTest {

    @MockBean
    private PerformanceService performanceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long venueId;
    Long performanceId;
    Performance performance;

    @BeforeEach
    public void setup() {
        venueId = 111L;
        performanceId = 123L;
        performance = getPerformance();
    }

    @Test
    @DisplayName("유효한 데이터로 공연 생성 API 호출 시 성공")
    void createPerformanceTest() throws Exception {

        PerformanceCreateDto performanceCreateDto = PerformanceCreateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId(venueId)
            .build();

        doNothing().when(performanceService).createPerformance(performanceCreateDto);

        mockMvc.perform(post("/performances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceCreateDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 공연 생성 API 호출 시 실패")
    void createPerformanceFailureTest() throws Exception {

        PerformanceCreateDto performanceCreateDto = PerformanceCreateDto.builder().venueId(venueId)
            .build();

        doThrow(NullPointerException.class).when(performanceService)
            .createPerformance(any(PerformanceCreateDto.class));

        mockMvc.perform(post("/performances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceCreateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 데이터로 공연 업데이트 API 호출 시 성공")
    void updatePerformanceTest() throws Exception {

        PerformanceUpdateDto performanceUpdateDto = PerformanceUpdateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .build();

        doNothing().when(performanceService).updatePerformance(performanceId, performanceUpdateDto);

        mockMvc.perform(put("/performances/" + performanceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceUpdateDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 데이터로 공연 업데이트 API 호출 시 실패")
    void updatePerformanceFailureTest() throws Exception {

        PerformanceUpdateDto performanceUpdateDto = PerformanceUpdateDto.builder().build();

        doThrow(NullPointerException.class).when(performanceService)
            .updatePerformance(performanceId, performanceUpdateDto);

        mockMvc.perform(put("/performances/" + performanceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceUpdateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 공연 아이디로 조회 API 호출 시 성공")
    void getPerformanceTest() throws Exception {

        PerformanceGetResponseDto performanceGetResponseDto = PerformanceGetResponseDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .build();

        doReturn(performanceGetResponseDto).when(performanceService)
            .getPerformanceById(performanceId);

        mockMvc.perform(get("/performances/" + performanceId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value(performanceGetResponseDto.getTitle()))
            .andExpect(jsonPath("$.description").value(performanceGetResponseDto.getDescription()));
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 공연 조회 API 호출 시 실패")
    void getPerformanceFailureTest() throws Exception {

        doThrow(ResourceNotFoundException.class).when(performanceService)
            .getPerformanceById(performanceId);

        mockMvc.perform(get("/performances/" + performanceId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
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

}