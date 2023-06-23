package com.bs.perform.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.bs.perform.dtos.request.GradeCreateDto;
import com.bs.perform.dtos.request.GradeUpdateDto;
import com.bs.perform.dtos.request.PerformanceUpdateDto;
import com.bs.perform.dtos.response.GradeGetResponseDto;
import com.bs.perform.enums.GradeType;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.Venue;
import com.bs.perform.repositories.GradeRepository;
import com.bs.perform.repositories.PerformanceRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class GradeServiceTest {

    @InjectMocks
    GradeServiceImpl gradeService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    GradeRepository gradeRepository;
    @Mock
    PerformanceRepository performanceRepository;

    Long performanceId;
    Performance performance;
    Long gradeId;
    Grade grade;

    @BeforeEach
    public void setup() {
        performanceId = 123L;
        performance = getPerformance();
        gradeId = 123L;
        grade = getGrade();
    }

    @Nested
    @DisplayName("등급 생성 테스트")
    class createTest {

        @Test
        @DisplayName("유효한 GradeCreateDto가 주어졌을 때, 정상적으로 공연을 저장")
        void createGradeTest() {

            GradeCreateDto createDto = GradeCreateDto.builder()
                .gradeType(GradeType.VIP)
                .price(new BigDecimal(200000))
                .performers("배우1, 배우2, 배우3")
                .performanceId(performanceId)
                .build();

            doReturn(Optional.of(performance)).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);
            doReturn(grade).when(gradeRepository).save(any(Grade.class));

            gradeService.createGrade(createDto);

            verify(gradeRepository, times(1)).save(any(Grade.class));
        }

        @Test
        @DisplayName("유효하지 않은 GradeCreateDto가 주어졌을 때, NullPointerException 발생")
        void createGradeFailureTest1() {

            GradeCreateDto createDto = GradeCreateDto.builder()
                .performanceId(performanceId)
                .build();

            doReturn(Optional.of(performance)).when(performanceRepository).findByIdAndIsDeletedFalse(performanceId);

            assertThatThrownBy(() -> gradeService.createGrade(createDto))
                .isInstanceOf(NullPointerException.class);
        }

        @Test
        @DisplayName("유효하지 않은 performanceId가 주어졌을 때, ResourceNotFoundException 발생")
        void createGradeFailureTest2() {

            GradeCreateDto createDto = GradeCreateDto.builder()
                .price(new BigDecimal(200000))
                .performers("배우1, 배우2, 배우3")
                .build();

            assertThatThrownBy(() -> gradeService.createGrade(createDto))
                .isInstanceOf(ResourceNotFoundException.class);
        }

    }

    @Nested
    @DisplayName("등급 수정 테스트")
    class updateTest {

        @Test
        @DisplayName("유효한 ID와 PerformanceUpdateDto가 주어졌을 때, 정상적으로 공연을 업데이트")
        void updatePerformanceTest() {

            GradeUpdateDto updateDto = GradeUpdateDto.builder()
                .gradeType(GradeType.VIP)
                .price(new BigDecimal(200000))
                .performers("배우1, 배우2, 배우3")
                .build();

            doReturn(Optional.of(grade)).when(gradeRepository).findByIdAndIsDeletedFalse(gradeId);

            gradeService.updateGrade(gradeId, updateDto);

            verify(gradeRepository, times(1)).findByIdAndIsDeletedFalse(gradeId);

        }

        @Test
        @DisplayName("유효하지 않은 PerformanceUpdateDto가 주어졌을 때, NullPointerException이 발생")
        void updatePerformanceFailTest() {

            GradeUpdateDto updateDto = GradeUpdateDto.builder().build();
            doReturn(Optional.of(grade)).when(gradeRepository).findByIdAndIsDeletedFalse(gradeId);

            assertThatThrownBy(() -> gradeService.updateGrade(performanceId, updateDto))
                .isInstanceOf(NullPointerException.class);
        }
        
    }

    @Nested
    @DisplayName("등급 조회 테스트")
    class getTest {
        
        @Test
        @DisplayName("기존에 존재하는 좌석 등급 ID가 주어졌을 때, PerformanceGetResponseDto를 반환")
        void getPerformanceByIdTest_found() {

            GradeGetResponseDto responseDto = GradeGetResponseDto.builder()
                .gradeType(GradeType.VIP)
                .price(new BigDecimal(200000))
                .performers("배우1, 배우2, 배우3")
                .performanceId(performanceId)
                .build();

            doReturn(Optional.of(grade)).when(gradeRepository).findByIdAndIsDeletedFalse(gradeId);
            doReturn(responseDto).when(modelMapper).map(grade, GradeGetResponseDto.class);

            GradeGetResponseDto result = gradeService.getGradeById(gradeId);

            assertThat(result.getPrice()).isEqualTo(grade.getPrice());
        }

        @Test
        @DisplayName("존재하지 않는 좌석 등급 ID가 주어졌을 때, ResourceNotFoundException이 발생")
        void getPerformanceByIdTest_notFound() {

            doThrow(ResourceNotFoundException.class).when(gradeRepository).findByIdAndIsDeletedFalse(gradeId);

            assertThatThrownBy(() -> gradeService.getGradeById(gradeId))
                .isInstanceOf(ResourceNotFoundException.class);
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

    private Grade getGrade() {
        return Grade.builder()
            .gradeType(GradeType.VIP)
            .price(new BigDecimal(200000))
            .performers("배우1, 배우2, 배우3")
            .performance(performance)
            .build();
    }

}