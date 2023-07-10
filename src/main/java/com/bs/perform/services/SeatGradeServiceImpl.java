package com.bs.perform.services;

import com.bs.perform.dtos.request.SeatGradeCreateDto;
import com.bs.perform.dtos.response.SeatGradeGetResponseDto;
import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Grade;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Seat;
import com.bs.perform.repositories.GradeRepository;
import com.bs.perform.repositories.SeatGradeRepository;
import com.bs.perform.repositories.SeatRepository;
import com.bs.perform.services.interfaces.SeatGradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SeatGradeServiceImpl implements SeatGradeService {

    private final ModelMapper modelMapper;
    private final SeatGradeRepository seatGradeRepository;
    private final GradeRepository gradeRepository;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public void createSeatGrade(SeatGradeCreateDto gradeDto) {

        log.info("Creating seatGrade with gradeDto: {}", gradeDto);

        Seat seat = getSeat(gradeDto.getSeatId());
        Grade grade = getGrade(gradeDto.getGradeId());

        if (seatGradeRepository.existsBySeatAndGradeAndIsDeletedFalse(seat, grade)) {
            throw new IllegalArgumentException("SeatGrade already exists for given Seat and Grade.");
        }

        SeatGrade seatGrade = gradeDto.toEntity(seat, grade);

        seatGradeRepository.save(seatGrade);
    }

    @Override
    @Transactional
    public void deleteSeatGrade(Long id) {

        log.info("deleteSeatGrade with ID: {}", id);

        SeatGrade seatGrade = getSeatGrade(id);

        seatGrade.deleteGrade();
    }

    @Override
    public SeatGradeGetResponseDto getSeatGradeById(Long id) {

        log.info("getSeatGradeById with ID: {}", id);

        SeatGrade seatGrade = getSeatGrade(id);

        return modelMapper.map(seatGrade, SeatGradeGetResponseDto.class);
    }

    private SeatGrade getSeatGrade(Long seatGradeId) {
        return seatGradeRepository.findByIdAndIsDeletedFalse(seatGradeId)
            .orElseThrow(() -> new ResourceNotFoundException("SeatGrade", String.valueOf(seatGradeId)));
    }

    private Grade getGrade(Long gradeId) {
        return gradeRepository.findByIdAndIsDeletedFalse(gradeId)
            .orElseThrow(() -> new ResourceNotFoundException("Grade", String.valueOf(gradeId)));
    }

    private Seat getSeat(Long seatId) {
        return seatRepository.findByIdAndIsDeletedFalse(seatId)
            .orElseThrow(() -> new ResourceNotFoundException("Seat", String.valueOf(seatId)));
    }
}
