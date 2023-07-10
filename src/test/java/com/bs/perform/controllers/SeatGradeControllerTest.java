package com.bs.perform.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.request.SeatGradeCreateDto;
import com.bs.perform.dtos.response.SeatGradeGetResponseDto;
import com.bs.perform.enums.GradeType;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Seat;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Venue;
import com.bs.perform.services.interfaces.SeatGradeService;
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
class SeatGradeControllerTest {
    
    @MockBean
    private SeatGradeService seatGradeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Venue venue;
    Long seatId;
    Long gradeId;
    Long seatGradeId;
    SeatGrade seatGrade;

    @BeforeEach
    public void setUp() {
        venue = getVenue();
        seatId = 123L;
        gradeId = 123L;
        seatGradeId = 123L;
        seatGrade = getSeatGrade();
    }

    @Test
    @DisplayName("유효한 데이터로 좌석 등급 생성 API 호출 시 성공")
    void createVenueTest() throws Exception {

        SeatGradeCreateDto createDto = SeatGradeCreateDto.builder()
            .gradeId(gradeId)
            .seatId(seatId)
            .build();

        doNothing().when(seatGradeService).createSeatGrade(createDto);

        mockMvc.perform(post("/seatGrades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 좌석 등급 생성 API 호출 시 실패")
    void createVenueTest_Failure() throws Exception {

        SeatGradeCreateDto createDto = SeatGradeCreateDto.builder().build();

        mockMvc.perform(post("/seatGrades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 좌석 등급 아이디로 조회 API 호출 시 성공")
    void getVenueTest() throws Exception {

        SeatGradeGetResponseDto getResponseDto = SeatGradeGetResponseDto.builder()
            .gradeId(gradeId)
            .seatId(seatId)
            .build();

        doReturn(getResponseDto).when(seatGradeService).getSeatGradeById(seatGradeId);

        mockMvc.perform(get("/seatGrades/" + seatGradeId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.gradeId").value(getResponseDto.getGradeId()))
            .andExpect(jsonPath("$.seatId").value(getResponseDto.getSeatId()));
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 좌석 등급 조회 API 호출 시 실패")
    void getVenueTest_Failure() throws Exception {

        doThrow(ResourceNotFoundException.class).when(seatGradeService).getSeatGradeById(seatGradeId);

        mockMvc.perform(get("/seatGrades/" + seatGradeId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

    private Seat getSeat() {
        Venue venue = Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        return Seat.builder()
            .row("A")
            .col(1)
            .venue(venue)
            .build();
    }

    private Grade getGrade() {
        Performance performance = Performance.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venue(venue)
            .build();

        return Grade.builder()
            .gradeType(GradeType.VIP)
            .price(new BigDecimal(200000))
            .performance(performance)
            .build();
    }

    private SeatGrade getSeatGrade() {
        Seat seat = getSeat();
        Grade grade = getGrade();

        return SeatGrade.builder()
            .grade(grade)
            .seat(seat)
            .build();
    }
}