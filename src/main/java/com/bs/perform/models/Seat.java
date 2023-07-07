package com.bs.perform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Seat extends BaseEntity {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String row;

    @Column(nullable = false)
    private int col;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id", nullable = false)
    private Venue venue;

    @Builder
    public Seat(String row, int col, Venue venue) {
        this.row = Objects.requireNonNull(row, "row must not be null");
        this.col = col;
        this.venue = venue;
    }

    public void updateSeat(String row, int col) {
        this.row = Objects.requireNonNull(row, "row must not be null");
        this.col = col;
    }

    public void deleteSeat() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

}
