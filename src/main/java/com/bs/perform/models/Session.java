package com.bs.perform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Session extends BaseEntity {

    @Id
    @Column(name = "session_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDate sessionDate;

    @Column(nullable=false)
    private LocalTime sessionTime;

    @ManyToOne
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Builder
    public Session(LocalDate sessionDate, LocalTime sessionTime, Performance performance) {
        this.sessionDate = Objects.requireNonNull(sessionDate, "sessionDate must not be null");
        this.sessionTime = Objects.requireNonNull(sessionTime, "sessionTime must not be null");
        this.performance = Objects.requireNonNull(performance, "performance must not be null");
    }

    public void updateSession(LocalDate sessionDate, LocalTime sessionTime) {
        this.sessionDate = Objects.requireNonNull(sessionDate, "sessionDate must not be null");
        this.sessionTime = Objects.requireNonNull(sessionTime, "sessionTime must not be null");
    }

    public void deleteSession() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
