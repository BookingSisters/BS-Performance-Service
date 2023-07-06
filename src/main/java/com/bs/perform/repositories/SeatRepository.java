package com.bs.perform.repositories;

import com.bs.perform.models.Seat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findByIdAndIsDeletedFalse(Long id);

}
