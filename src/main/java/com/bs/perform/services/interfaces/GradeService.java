package com.bs.perform.services.interfaces;

import com.bs.perform.dtos.request.GradeCreateDto;
import com.bs.perform.dtos.request.GradeUpdateDto;
import com.bs.perform.dtos.response.GradeGetResponseDto;

public interface GradeService {

    void createGrade(final GradeCreateDto gradeDto);

    void updateGrade(final Long id, final GradeUpdateDto gradeDto);

    void deleteGrade(final Long id);

    GradeGetResponseDto getGradeById(final Long id);
}
