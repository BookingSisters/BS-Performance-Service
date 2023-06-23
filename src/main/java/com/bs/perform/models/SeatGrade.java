package com.bs.perform.models;

import com.bs.perform.enums.Grade;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.math.BigDecimal;
import java.util.Objects;

@DynamoDbBean
@NoArgsConstructor
@Data
public class SeatGrade {

    private Grade grade;
    private BigDecimal price;
    private int seatCount;

    @Builder
    public SeatGrade(Grade grade, BigDecimal price, int seatCount) {
        this.grade = Objects.requireNonNull(grade, "grade must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
        if (seatCount <= 0) {
            throw new IllegalArgumentException("seatCount must be greater than zero");
        }
        this.seatCount = seatCount;
    }
}
