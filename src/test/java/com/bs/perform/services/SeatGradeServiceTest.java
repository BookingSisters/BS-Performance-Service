package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.SeatGradeCreateDto;
import com.bs.perform.dtos.response.SeatGradeGetResponseDto;
import com.bs.perform.enums.GradeType;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Seat;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.GradeRepository;
import com.bs.perform.repositories.SeatGradeRepository;
import com.bs.perform.repositories.SeatRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class SeatGradeServiceTest {

    @InjectMocks
    private SeatGradeServiceImpl seatGradeService;
    @Mock
    private SeatGradeRepository seatGradeRepository;
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private GradeRepository gradeRepository;
    @Mock
    private ModelMapper modelMapper;

    Long seatId;
    Seat seat;
    Long gradeId;
    Grade grade;
    Long seatGradeId;
    SeatGrade seatGrade;

    @BeforeEach
    public void setUp() {
        seatId = 123L;
        seat = getSeat();
        gradeId = 123L;
        grade = getGrade();
        seatGradeId = 123L;
        seatGrade = getSeatGrade();
    }

    @Test
    @DisplayName("유효한 seatGradeCreateDto가 주어졌을 때, 정상적으로 좌석 등급을 저장")
    void createSeatGradeTest() {

        SeatGradeCreateDto createDto = SeatGradeCreateDto.builder()
            .gradeId(gradeId)
            .seatId(seatId)
            .build();

        doReturn(Optional.of(grade)).when(gradeRepository).findByIdAndIsDeletedFalse(gradeId);
        doReturn(Optional.of(seat)).when(seatRepository).findByIdAndIsDeletedFalse(seatId);
        doReturn(seatGrade).when(seatGradeRepository).save(any(SeatGrade.class));

        seatGradeService.createSeatGrade(createDto);

        verify(seatGradeRepository, times(1)).save(any(SeatGrade.class));
    }

    @Test
    @DisplayName("이미 존재하는 좌석과 등급을 생성하는 경우, IllegalArgumentException 발생")
    void createSeatGradeFailureTest() {

        SeatGradeCreateDto createDto = SeatGradeCreateDto.builder()
            .gradeId(gradeId)
            .seatId(seatId)
            .build();

        doReturn(Optional.of(grade)).when(gradeRepository).findByIdAndIsDeletedFalse(gradeId);
        doReturn(Optional.of(seat)).when(seatRepository).findByIdAndIsDeletedFalse(seatId);
        doReturn(true).when(seatGradeRepository).existsBySeatAndGradeAndIsDeletedFalse(seat, grade);

        assertThatThrownBy(() -> seatGradeService.createSeatGrade(createDto))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("기존에 존재하는 공연장 ID가 주어졌을 때, SeatGetResponseDto를 반환")
    void getSeatGradeByIdTest() {

        SeatGradeGetResponseDto getResponseDto = SeatGradeGetResponseDto.builder()
            .gradeId(gradeId)
            .seatId(seatId)
            .build();

        doReturn(Optional.of(seatGrade)).when(seatGradeRepository)
            .findByIdAndIsDeletedFalse(seatGradeId);
        doReturn(getResponseDto).when(modelMapper).map(seatGrade, SeatGradeGetResponseDto.class);

        SeatGradeGetResponseDto result = seatGradeService.getSeatGradeById(seatGradeId);

        assertThat(result.getGradeId()).isEqualTo(getResponseDto.getGradeId());
        assertThat(result.getSeatId()).isEqualTo(getResponseDto.getSeatId());
    }

    @Test
    @DisplayName("존재하지 않는 공연장 ID가 주어졌을 때, ResourceNotFoundException 발생")
    void getSeatGradeByIdFailureTest() {

        doThrow(ResourceNotFoundException.class).when(seatGradeRepository)
            .findByIdAndIsDeletedFalse(seatId);

        assertThatThrownBy(() -> seatGradeService.getSeatGradeById(seatId))
            .isInstanceOf(ResourceNotFoundException.class);
    }

    private Venue getVenue() {
        return Venue.builder()
            .name("Apollo Theater")
            .location("253 W 125th St, New York, NY 10027, United States")
            .build();
    }

    private Seat getSeat() {
        Venue venue = getVenue();

        return Seat.builder()
            .row("A")
            .col(1)
            .venue(venue)
            .build();
    }

    private Grade getGrade() {
        Venue venue = getVenue();

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
            .performers("배우1, 배우2, 배우3")
            .performance(performance)
            .build();
    }

    private SeatGrade getSeatGrade() {
        return SeatGrade.builder()
            .grade(grade)
            .seat(seat)
            .build();
    }

}