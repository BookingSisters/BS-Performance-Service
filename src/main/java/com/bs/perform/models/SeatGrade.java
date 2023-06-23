package com.bs.perform.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class SeatGrade extends BaseEntity {

    @Id
    @Column(name = "seatGrade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @Builder
    public SeatGrade(Seat seat, Grade grade) {
        this.seat = Objects.requireNonNull(seat, "seat must not be null");
        this.grade = Objects.requireNonNull(grade, "grade must not be null");
    }

    public void deleteGrade() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
