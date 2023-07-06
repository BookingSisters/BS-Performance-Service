package com.bs.perform.repositories;

import com.bs.perform.models.Performance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Optional<Performance> findByIdAndIsDeletedFalse(Long id);

}
