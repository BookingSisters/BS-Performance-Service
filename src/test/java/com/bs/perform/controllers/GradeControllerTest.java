package com.bs.perform.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.request.GradeCreateDto;
import com.bs.perform.dtos.request.GradeUpdateDto;
import com.bs.perform.dtos.response.GradeGetResponseDto;
import com.bs.perform.enums.GradeType;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Venue;
import com.bs.perform.services.interfaces.GradeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
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
class GradeControllerTest {

    @MockBean
    private GradeService gradeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long performanceId;
    Long gradeId;
    Grade grade;

    @BeforeEach
    public void setup() {
        performanceId = 123L;
        gradeId = 123L;
        grade = getGrade();
    }

    @Test
    @DisplayName("유효한 데이터로 좌석 등급 생성 API 호출 시 성공")
    void createPerformanceTest() throws Exception {

        GradeCreateDto dto = GradeCreateDto.builder()
            .gradeType(GradeType.VIP)
            .price(new BigDecimal(200000))
            .performanceId(performanceId)
            .build();

        doNothing().when(gradeService).createGrade(dto);

        mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 좌석 등급 생성 API 호출 시 실패")
    void createPerformanceFailureTest() throws Exception {

        GradeCreateDto createDto = GradeCreateDto.builder().build();

        doThrow(NullPointerException.class).when(gradeService).createGrade(createDto);

        mockMvc.perform(post("/grades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 데이터로 좌석 등급 업데이트 API 호출 시 성공")
    void updatePerformanceTest() throws Exception {

        GradeUpdateDto UpdateDto = GradeUpdateDto.builder()
            .gradeType(GradeType.VIP)
            .price(new BigDecimal(200000))
            .build();

        doNothing().when(gradeService).updateGrade(gradeId, UpdateDto);

        mockMvc.perform(put("/grades/" + gradeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdateDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 데이터로 좌석 등급 업데이트 API 호출 시 실패")
    void updatePerformanceFailureTest() throws Exception {

        GradeUpdateDto UpdateDto = GradeUpdateDto.builder().build();

        doThrow(NullPointerException.class).when(gradeService).updateGrade(gradeId, UpdateDto);

        mockMvc.perform(put("/grades/" + gradeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(UpdateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 좌석 등급 아이디로 조회 API 호출 시 성공")
    void getPerformanceTest() throws Exception {

        GradeGetResponseDto responseDto = GradeGetResponseDto.builder()
            .gradeType(GradeType.VIP)
            .price(new BigDecimal(200000))
            .performanceId(performanceId)
            .build();

        doReturn(responseDto).when(gradeService).getGradeById(gradeId);

        mockMvc.perform(get("/grades/" + gradeId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.price").value(responseDto.getPrice()));
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 좌석 등급 조회 API 호출 시 실패")
    void getPerformanceFailureTest() throws Exception {

        doThrow(ResourceNotFoundException.class).when(gradeService)
            .getGradeById(gradeId);

        mockMvc.perform(get("/grades/" + gradeId)
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

    private Grade getGrade() {
        Performance performance = getPerformance();

        return Grade.builder()
            .gradeType(GradeType.VIP)
            .price(new BigDecimal(200000))
            .performance(performance)
            .build();
    }
}