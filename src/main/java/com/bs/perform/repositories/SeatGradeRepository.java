package com.bs.perform.repositories;

import com.bs.perform.models.Grade;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Seat;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    Optional<SeatGrade> findByIdAndIsDeletedFalse(Long id);

    boolean existsBySeatAndGradeAndIsDeletedFalse(Seat seat, Grade grade);

}
