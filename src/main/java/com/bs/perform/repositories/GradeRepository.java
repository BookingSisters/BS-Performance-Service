package com.bs.perform.repositories;

import com.bs.perform.models.Grade;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    Optional<Grade> findByIdAndIsDeletedFalse(Long id);

}
