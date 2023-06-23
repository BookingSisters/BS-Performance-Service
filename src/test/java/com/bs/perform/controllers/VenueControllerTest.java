package com.bs.perform.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bs.perform.dtos.request.VenueCreateDto;
import com.bs.perform.dtos.request.VenueUpdateDto;
import com.bs.perform.dtos.response.VenueGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Seat;
import com.bs.perform.models.Venue;
import com.bs.perform.services.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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

@SpringBootTest
@AutoConfigureMockMvc
class VenueControllerTest {

    @MockBean
    private VenueService venueService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<Seat> seatList = new ArrayList<>();
    Venue venue;
    Long venueId;

    @BeforeEach
    public void setUp(){

        venue = getVenue();
        venueId = 123L;
    }

    @Test
    @DisplayName("유효한 데이터로 공연장 생성 API 호출 시 성공")
    void createVenueTest() throws Exception {

        VenueCreateDto venueCreateDto = VenueCreateDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        doNothing().when(venueService).createVenue(venueCreateDto);

        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venueCreateDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 공연장 생성 API 호출 시 실패")
    void createVenueTest_Failure() throws Exception {

        VenueCreateDto venueCreateDto = VenueCreateDto.builder().build();

        doThrow(NullPointerException.class).when(venueService).createVenue(venueCreateDto);

        mockMvc.perform(post("/venues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venueCreateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 데이터로 공연장 업데이트 API 호출 시 성공")
    void updateVenueTest() throws Exception {

        VenueUpdateDto venueUpdateDto = VenueUpdateDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        doNothing().when(venueService).updateVenue(venueId, venueUpdateDto);

        mockMvc.perform(put("/venues/" + venueId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venueUpdateDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 데이터로 공연장 업데이트 API 호출 시 실패")
    void updateVenueTest_Failure() throws Exception {

        VenueUpdateDto venueUpdateDto = VenueUpdateDto.builder().build();

        doThrow(NullPointerException.class).when(venueService).updateVenue(venueId, venueUpdateDto);

        mockMvc.perform(put("/venues/" + venueId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venueUpdateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 공연장 아이디로 조회 API 호출 시 성공")
    void getVenueTest() throws Exception {

        VenueGetResponseDto venueGetResponseDto = VenueGetResponseDto.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();

        doReturn(venueGetResponseDto).when(venueService).getVenueById(venueId);

        mockMvc.perform(get("/venues/" + venueId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value(venueGetResponseDto.getName()))
            .andExpect(jsonPath("$.location").value(venueGetResponseDto.getLocation()));
    }

    @Test
    @DisplayName("존재하지 않는 아이디로 공연장 조회 API 호출 시 실패")
    void getVenueTest_Failure() throws Exception {

        doThrow(ResourceNotFoundException.class).when(venueService).getVenueById(venueId);

        mockMvc.perform(get("/venues/" + venueId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

}