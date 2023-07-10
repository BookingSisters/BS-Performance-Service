package com.bs.perform.repositories;

import com.bs.perform.models.Session;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
    Optional<Session> findByIdAndIsDeletedFalse(Long id);

    List<Session> findByPerformanceIdAndIsDeletedFalse(Long performanceId);
}
