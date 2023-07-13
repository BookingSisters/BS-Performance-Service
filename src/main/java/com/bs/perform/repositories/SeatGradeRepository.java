package com.bs.perform.repositories;

import com.bs.perform.models.Grade;
import com.bs.perform.models.Performance;
import com.bs.perform.models.SeatGrade;
import com.bs.perform.models.Seat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SeatGradeRepository extends JpaRepository<SeatGrade, Long> {

    Optional<SeatGrade> findByIdAndIsDeletedFalse(Long id);

    boolean existsBySeatAndGradeAndIsDeletedFalse(Seat seat, Grade grade);

    @Query(
        value = "SELECT sg "
            + "FROM SeatGrade sg "
            + "JOIN FETCH sg.grade g "
            + "WHERE g.performance = :performance"
    )
    List<SeatGrade> findGradeFetchJoinSeatGrade(@Param("performance") Performance performance);

}
