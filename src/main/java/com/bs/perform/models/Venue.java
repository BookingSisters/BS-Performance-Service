package com.bs.perform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Venue extends BaseEntity {

    @Id
    @Column(name = "venue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    @Builder
    public Venue(String name, String location) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.location = Objects.requireNonNull(location, "location cannot be null");
    }

    public void updateVenue(String name, String location) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.location = Objects.requireNonNull(location, "location cannot be null");
    }

    public void deleteVenue() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

}
