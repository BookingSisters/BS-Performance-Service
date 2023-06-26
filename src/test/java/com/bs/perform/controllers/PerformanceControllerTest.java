package com.bs.perform.controllers;

import com.bs.perform.dtos.PerformanceCreateDto;
import com.bs.perform.dtos.PerformanceGetResponseDto;
import com.bs.perform.dtos.PerformanceUpdateDto;
import com.bs.perform.enums.Grade;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import com.bs.perform.services.PerformanceService;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PerformanceControllerTest {

    @MockBean
    private PerformanceService performanceServiceImpl;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    List<SeatGrade> seatGradeList;
    List<Session> sessionList;

    @BeforeEach
    public void setup() {

        SeatGrade seatGradeVip = SeatGrade.builder().grade(Grade.VIP).price(new BigDecimal(200000)).seatCount(100).build();
        SeatGrade seatGradeR = SeatGrade.builder().grade(Grade.S).price(new BigDecimal(100000)).seatCount(200).build();
        seatGradeList = Arrays.asList(seatGradeVip, seatGradeR);

        Session session1 = Session.builder().sessionDate(LocalDate.of(2023,6,24)).sessionTime(LocalTime.of(13,0)).performers(Arrays.asList("ActorA", "ActorB")).build();
        Session session2 = Session.builder().sessionDate(LocalDate.of(2023,6,24)).sessionTime(LocalTime.of(17,0)).performers(Arrays.asList("ActorC", "ActorD")).build();
        sessionList = Arrays.asList(session1, session2);
    }

    @Test
    @DisplayName("공연 생성 API 테스트")
    void createPerformanceTest() throws Exception {

        PerformanceCreateDto performanceCreateDto = getPerformanceCreateDto();

        doNothing().when(performanceServiceImpl).createPerformance(any(PerformanceCreateDto.class));

        mockMvc.perform(put("/performance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(performanceCreateDto)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("공연 업데이트 API 테스트")
    void updatePerformanceTest() throws Exception {

        String id = "123";
        PerformanceUpdateDto performanceUpdateDto = getPerformanceUpdateDto();

        doNothing().when(performanceServiceImpl).updatePerformance(eq(id), any(PerformanceUpdateDto.class));

        mockMvc.perform(post("/performance/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(performanceUpdateDto)))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("공연 아이디로 조회 API 테스트")
    void getPerformanceTest() throws Exception {

        String id = "123";
        PerformanceGetResponseDto performanceGetResponseDto = getPerformanceGetResponseDto();

        doReturn(performanceGetResponseDto).when(performanceServiceImpl).getPerformanceById(id);

        mockMvc.perform(get("/performance/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(performanceGetResponseDto.getTitle()))
                .andExpect(jsonPath("$.description").value(performanceGetResponseDto.getDescription()));
    }

    private PerformanceCreateDto getPerformanceCreateDto() {
        return PerformanceCreateDto.builder()
                .title("BTS 2023 concert")
                .description("This is BTS 2023 concert")
                .runTime(100)
                .totalSeatCount(300)
                .reservationStartDate(LocalDate.of(2023, 6, 15))
                .reservationEndDate(LocalDate.of(2023, 7, 15))
                .performanceStartDate(LocalDate.of(2023, 7, 22))
                .performanceEndDate(LocalDate.of(2023, 7, 23))
                .location("Jamsil Sports Complex")
                .seatGradeList(seatGradeList)
                .sessionList(sessionList)
                .build();
    }

    private PerformanceUpdateDto getPerformanceUpdateDto() {
        return PerformanceUpdateDto.builder()
                .title("BTS 2023 concert")
                .description("This is BTS 2023 concert")
                .runTime(100)
                .totalSeatCount(300)
                .reservationStartDate(LocalDate.of(2023, 6, 15))
                .reservationEndDate(LocalDate.of(2023, 7, 15))
                .performanceStartDate(LocalDate.of(2023, 7, 22))
                .performanceEndDate(LocalDate.of(2023, 7, 23))
                .location("Jamsil Sports Complex")
                .seatGradeList(seatGradeList)
                .sessionList(sessionList)
                .build();
    }

    private PerformanceGetResponseDto getPerformanceGetResponseDto() {
        return PerformanceGetResponseDto.builder()
                .title("BTS 2023 concert")
                .description("This is BTS 2023 concert")
                .runTime(100)
                .totalSeatCount(300)
                .reservationStartDate(LocalDate.of(2023, 6,15))
                .reservationEndDate(LocalDate.of(2023, 7,15))
                .performanceStartDate(LocalDate.of(2023, 7,22))
                .performanceEndDate(LocalDate.of(2023, 7,23))
                .location("Jamsil Sports Complex")
                .seatGradeList(seatGradeList)
                .sessionList(sessionList)
                .build();
    }

}