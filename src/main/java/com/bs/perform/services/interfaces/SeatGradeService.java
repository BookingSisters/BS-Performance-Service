package com.bs.perform.services.interfaces;

import com.bs.perform.dtos.request.SeatGradeCreateDto;
import com.bs.perform.dtos.response.SeatGradeGetResponseDto;

public interface SeatGradeService {

    void createSeatGrade(final SeatGradeCreateDto gradeDto);
    void deleteSeatGrade(final Long id);
    SeatGradeGetResponseDto getSeatGradeById(final Long id);
}
