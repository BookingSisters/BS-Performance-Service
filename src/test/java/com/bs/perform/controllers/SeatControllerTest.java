package com.bs.perform.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.request.SeatCreateDto;
import com.bs.perform.dtos.request.SeatUpdateDto;
import com.bs.perform.dtos.response.SeatGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Seat;
import com.bs.perform.models.Venue;
import com.bs.perform.services.SeatService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class SeatControllerTest {

    @MockBean
    private SeatService seatService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    Long venueId;
    Long seatId;
    Seat seat;

    @BeforeEach
    public void setUp() {
        venueId = 10023L;
        seatId = 123L;
        seat = getSeat();
    }

    @Test
    @DisplayName("유효한 데이터로 좌석 생성 API 호출 시 성공")
    void createSeatTest() throws Exception {

        SeatCreateDto seatCreateDto = SeatCreateDto.builder()
            .row("A")
            .col(1)
            .venueId(venueId)
            .build();

        doNothing().when(seatService).createSeat(seatCreateDto);

        mockMvc.perform(post("/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seatCreateDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 좌석 생성 API 호출 시 실패")
    void createSeatFailureTest() throws Exception {

        SeatCreateDto seatCreateDto = SeatCreateDto.builder()
            .venueId(venueId)
            .build();

        doThrow(NullPointerException.class).when(seatService).createSeat(seatCreateDto);

        mockMvc.perform(post("/seats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seatCreateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 데이터로 좌석 업데이트 API 호출 시 성공")
    void updateSeatTest() throws Exception {

        SeatUpdateDto seatUpdateDto = SeatUpdateDto.builder()
            .row("A")
            .col(1)
            .build();

        doNothing().when(seatService).updateSeat(seatId, seatUpdateDto);

        mockMvc.perform(put("/seats/" + seatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seatUpdateDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 데이터로 좌석 업데이트 API 호출 시 실패")
    void updateSeatFailureTest() throws Exception {

        SeatUpdateDto seatUpdateDto = SeatUpdateDto.builder().build();

        doThrow(NullPointerException.class).when(seatService).updateSeat(seatId, seatUpdateDto);

        mockMvc.perform(put("/seats/" + seatId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(seatUpdateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 좌석 아이디로 조회 API 호출 시 성공")
    void getSeatByIdTest() throws Exception {

        SeatGetResponseDto seatGetResponseDto = SeatGetResponseDto.builder()
            .row("A")
            .col(1)
            .build();

        doReturn(seatGetResponseDto).when(seatService).getSeatById(seatId);

        mockMvc.perform(get("/seats/" + seatId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.row").value(seatGetResponseDto.getRow()))
            .andExpect(jsonPath("$.col").value(seatGetResponseDto.getCol()));
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 좌석 조회 API 호출 시 실패")
    void getSeatByIdFailureTest() throws Exception {

        doThrow(ResourceNotFoundException.class).when(seatService).getSeatById(seatId);

        mockMvc.perform(get("/seats/" + seatId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
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
}