package com.bs.perform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Performance extends BaseEntity {

    @Id
    @Column(name = "performance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Column(nullable = false)
    private int runningTime;

    @Column(nullable = false)
    private LocalDate performanceStartDate;

    @Column(nullable = false)
    private LocalDate performanceEndDate;

    @Column(nullable = false)
    private LocalDate reservationStartDate;

    @Column(nullable = false)
    private LocalDate reservationEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @Builder
    public Performance(String title, String description, int runningTime,
        LocalDate performanceStartDate, LocalDate performanceEndDate,
        LocalDate reservationStartDate, LocalDate reservationEndDate, Venue venue) {

        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.runningTime = runningTime;
        this.performanceStartDate = Objects.requireNonNull(performanceStartDate,
            "Performance Start Date cannot be null");
        this.performanceEndDate = Objects.requireNonNull(performanceEndDate,
            "Performance End Date cannot be null");
        this.reservationStartDate = Objects.requireNonNull(reservationStartDate,
            "Reservation Start Date cannot be null");
        this.reservationEndDate = Objects.requireNonNull(reservationEndDate,
            "Reservation End Date cannot be null");

        if (performanceStartDate.isAfter(performanceEndDate)) {
            throw new IllegalArgumentException(
                "Performance Start Date cannot be after Performance End Date");
        }
        if (reservationStartDate.isAfter(reservationEndDate)) {
            throw new IllegalArgumentException(
                "Reservation Start Date cannot be after Reservation End Date");
        }
        this.venue = Objects.requireNonNull(venue, "Venue cannot be null");
    }

    public void updatePerformance(String title, String description, int runningTime,
        LocalDate performanceStartDate, LocalDate performanceEndDate,
        LocalDate reservationStartDate, LocalDate reservationEndDate) {

        this.title = Objects.requireNonNull(title, "Title cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.runningTime = runningTime;
        this.performanceStartDate = Objects.requireNonNull(performanceStartDate,
            "Performance Start Date cannot be null");
        this.performanceEndDate = Objects.requireNonNull(performanceEndDate,
            "Performance End Date cannot be null");
        this.reservationStartDate = Objects.requireNonNull(reservationStartDate,
            "Reservation Start Date cannot be null");
        this.reservationEndDate = Objects.requireNonNull(reservationEndDate,
            "Reservation End Date cannot be null");

        if (performanceStartDate.isAfter(performanceEndDate)) {
            throw new IllegalArgumentException(
                "Performance Start Date cannot be after Performance End Date");
        }
        if (reservationStartDate.isAfter(reservationEndDate)) {
            throw new IllegalArgumentException(
                "Reservation Start Date cannot be after Reservation End Date");
        }
    }

    public void deletePerformance() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
