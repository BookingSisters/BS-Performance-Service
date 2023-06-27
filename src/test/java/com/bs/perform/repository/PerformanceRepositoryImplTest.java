package com.bs.perform.repository;

import com.bs.perform.enums.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PerformanceRepositoryImplTest {

    @InjectMocks
    private PerformanceRepositoryImpl performanceRepository;

    @Mock
    private DynamoDbTable<Performance> performanceTable;

    Performance performance;
    List<SeatGrade> seatGradeList;
    List<Session> sessionList;

    @BeforeEach
    public void setup() {
        seatGradeList = getSeatGradeList();
        sessionList = getSessionList();
        performance = getPerformance();
    }

    @Test
    @DisplayName("공연 생성 테스트")
    void createPerformanceTest() {

        doNothing().when(performanceTable).putItem(performance);

        performanceRepository.createPerformance(performance);

        verify(performanceTable, times(1)).putItem(performance);
    }

    @Test
    @DisplayName("공연 업데이트 테스트")
    void updatePerformanceTest() {

        String id = performance.getId();
        doReturn(performance).when(performanceTable).getItem(Key.builder().partitionValue(id).build());
        doReturn(performance).when(performanceTable).updateItem(performance);

        performanceRepository.updatePerformance(id, performance);

        verify(performanceTable, times(1)).updateItem(performance);
    }

    @Test
    @DisplayName("공연 아이디로 조회 테스트")
    void getPerformanceByIdTest() {

        String id = performance.getId();
        doReturn(performance).when(performanceTable).getItem(Key.builder().partitionValue(id).build());

        Performance resultPerformance = performanceRepository.getPerformanceById(id);

        assertThat(resultPerformance).isEqualTo(performance);
    }

    private static List<SeatGrade> getSeatGradeList() {
        SeatGrade seatGradeVip = SeatGrade.builder().grade(Grade.VIP).price(new BigDecimal(200000)).seatCount(100).build();
        SeatGrade seatGradeR = SeatGrade.builder().grade(Grade.S).price(new BigDecimal(100000)).seatCount(200).build();
        return Arrays.asList(seatGradeVip, seatGradeR);
    }

    private static List<Session> getSessionList() {
        Session session1 = Session.builder().sessionDate(LocalDate.of(2023,6,24)).sessionTime(LocalTime.of(13,0)).performers(Arrays.asList("ActorA", "ActorB")).build();
        Session session2 = Session.builder().sessionDate(LocalDate.of(2023,6,24)).sessionTime(LocalTime.of(17,0)).performers(Arrays.asList("ActorC", "ActorD")).build();
        return Arrays.asList(session1, session2);
    }

    private Performance getPerformance() {
        return Performance.builder()
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

}