package com.bs.perform.repositories;

import com.bs.perform.models.Venue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    Optional<Venue> findByIdAndIsDeletedFalse(Long id);

    List<Venue> findAllByIsDeletedFalse();
}
