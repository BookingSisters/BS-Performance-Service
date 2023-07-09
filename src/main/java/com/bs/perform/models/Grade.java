package com.bs.perform.models;

import com.bs.perform.enums.GradeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Grade extends BaseEntity {

    @Id
    @Column(name = "grade_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GradeType gradeType;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Builder
    public Grade(GradeType gradeType, BigDecimal price,
        Performance performance) {
        this.gradeType = Objects.requireNonNull(gradeType, "grade must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
        this.performance = Objects.requireNonNull(performance, "performance must not be null");
    }

    public void updateGrade(GradeType gradeType, BigDecimal price) {
        this.gradeType = Objects.requireNonNull(gradeType, "grade must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
    }

    public void deleteGrade() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }
}
