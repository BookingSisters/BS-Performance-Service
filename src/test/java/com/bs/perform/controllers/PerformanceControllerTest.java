package com.bs.perform.controllers;

import com.bs.perform.dtos.request.PerformanceCreateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.PerformanceGetResponseDto;
import com.bs.perform.enums.Grade;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.performance.SeatGrade;
import com.bs.perform.models.performance.Session;
import com.bs.perform.models.venue.Seat;
import com.bs.perform.services.PerformanceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value.Str;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
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
        seatGradeList = getSeatGradeList();
        sessionList = getSessionList();
    }

    @Test
    @DisplayName("유효한 데이터로 공연 생성 API 호출 시 성공")
    void createPerformanceTest() throws Exception {

        PerformanceCreateDto performanceCreateDto = getPerformanceCreateDto();

        doNothing().when(performanceServiceImpl).createPerformance(any(PerformanceCreateDto.class));

        mockMvc.perform(post("/performance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceCreateDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("잘못된 데이터로 공연 생성 API 호출 시 실패")
    void createPerformanceTest_Failure() throws Exception {

        PerformanceCreateDto performanceCreateDto = getInvalidPerformanceCreateDto();

        doThrow(NullPointerException.class).when(performanceServiceImpl)
            .createPerformance(any(PerformanceCreateDto.class));

        mockMvc.perform(post("/performance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceCreateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 데이터로 공연 업데이트 API 호출 시 성공")
    void updatePerformanceTest() throws Exception {

        String id = "123";
        PerformanceUpdateDto performanceUpdateDto = getPerformanceUpdateDto();

        doNothing().when(performanceServiceImpl)
            .updatePerformance(eq(id), any(PerformanceUpdateDto.class));

        mockMvc.perform(put("/performance/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceUpdateDto)))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("잘못된 데이터로 공연 업데이트 API 호출 시 실패")
    void updatePerformanceTest_Failure() throws Exception {

        String id = "123";
        PerformanceUpdateDto performanceUpdateDto = getInvalidPerformanceUpdateDto();

        doThrow(NullPointerException.class).when(performanceServiceImpl)
            .updatePerformance(eq(id), any(PerformanceUpdateDto.class));

        mockMvc.perform(put("/performance/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(performanceUpdateDto)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("유효한 공연 아이디로 조회 API 호출 시 성공")
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

    @Test
    @DisplayName("존재하지 않는 아이디로 공연 조회 API 호출 시 실패")
    void getPerformanceTest_Failure() throws Exception {

        String id = "123";

        doThrow(ResourceNotFoundException.class).when(performanceServiceImpl)
            .getPerformanceById(id);

        mockMvc.perform(get("/performance/" + id)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private List<SeatGrade> getSeatGradeList() {
        List<String> seatList = Arrays.asList("seat1", "seat2", "seat3");
        SeatGrade seatGradeVip = SeatGrade.builder().grade(Grade.VIP).price(new BigDecimal(200000))
            .seatCount(100).seatList(seatList).build();
        SeatGrade seatGradeR = SeatGrade.builder().grade(Grade.S).price(new BigDecimal(100000))
            .seatCount(200).seatList(seatList).build();
        return Arrays.asList(seatGradeVip, seatGradeR);
    }

    private static List<Session> getSessionList() {
        Session session1 = Session.builder().sessionDate(LocalDate.of(2023, 6, 24))
            .sessionTime(LocalTime.of(13, 0)).performers(Arrays.asList("ActorA", "ActorB")).build();
        Session session2 = Session.builder().sessionDate(LocalDate.of(2023, 6, 24))
            .sessionTime(LocalTime.of(17, 0)).performers(Arrays.asList("ActorC", "ActorD")).build();
        return Arrays.asList(session1, session2);
    }

    private PerformanceCreateDto getPerformanceCreateDto() {
        return PerformanceCreateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .totalSeatCount(300)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId("Jamsil Sports Complex")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
            .build();
    }

    private PerformanceCreateDto getInvalidPerformanceCreateDto() {
        return PerformanceCreateDto.builder()
            .description("This is BTS 2023 concert")
            .build();
    }

    private PerformanceUpdateDto getPerformanceUpdateDto() {
        return PerformanceUpdateDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .totalSeatCount(300)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId("Jamsil Sports Complex")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
            .build();
    }

    private PerformanceUpdateDto getInvalidPerformanceUpdateDto() {
        return PerformanceUpdateDto.builder()
            .description("This is BTS 2023 concert")
            .build();
    }

    private PerformanceGetResponseDto getPerformanceGetResponseDto() {
        return PerformanceGetResponseDto.builder()
            .title("BTS 2023 concert")
            .description("This is BTS 2023 concert")
            .runningTime(100)
            .totalSeatCount(300)
            .reservationStartDate(LocalDate.of(2023, 6, 15))
            .reservationEndDate(LocalDate.of(2023, 7, 15))
            .performanceStartDate(LocalDate.of(2023, 7, 22))
            .performanceEndDate(LocalDate.of(2023, 7, 23))
            .venueId("Jamsil Sports Complex")
            .seatGradeList(seatGradeList)
            .sessionList(sessionList)
            .build();
    }

}