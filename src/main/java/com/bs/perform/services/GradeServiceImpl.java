package com.bs.perform.services;

import com.bs.perform.dtos.request.GradeCreateDto;
import com.bs.perform.dtos.request.GradeUpdateDto;
import com.bs.perform.dtos.response.GradeGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.repositories.GradeRepository;
import com.bs.perform.repositories.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class GradeServiceImpl implements GradeService {

    private final ModelMapper modelMapper;
    private final GradeRepository gradeRepository;
    private final PerformanceRepository performanceRepository;

    @Override
    @Transactional
    public void createGrade(GradeCreateDto gradeDto) {

        log.info("Creating grade with gradeDto: {}", gradeDto);

        Performance performance = getPerformance(gradeDto.getPerformanceId());
        Grade grade = gradeDto.toEntity(performance);

        gradeRepository.save(grade);
    }

    @Override
    @Transactional
    public void updateGrade(Long id, GradeUpdateDto gradeDto) {

        log.info("Updating grade with ID: {},  GradeUpdateDto: {}", id, gradeDto);

        Grade grade = getGrade(id);

        grade.updateGrade(gradeDto.getGradeType(), gradeDto.getPrice());

    }

    @Override
    @Transactional
    public void deleteGrade(Long id) {

        log.info("deleteGrade with ID: {}", id);

        Grade grade = getGrade(id);

        grade.deleteGrade();
    }

    @Override
    public GradeGetResponseDto getGradeById(Long id) {

        log.info("getGradeById with ID: {}", id);

        Grade grade = getGrade(id);

        return modelMapper.map(grade, GradeGetResponseDto.class);
    }

    private Grade getGrade(Long gradeId) {
        return gradeRepository.findByIdAndIsDeletedFalse(gradeId)
            .orElseThrow(() -> new ResourceNotFoundException("Grade", String.valueOf(gradeId)));
    }

    private Performance getPerformance(Long performanceId) {
        return performanceRepository.findByIdAndIsDeletedFalse(performanceId)
            .orElseThrow(() -> new ResourceNotFoundException("Performance", String.valueOf(performanceId)));
    }

}
