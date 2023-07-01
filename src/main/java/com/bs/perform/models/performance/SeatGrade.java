package com.bs.perform.models.performance;

import com.bs.perform.enums.Grade;
import com.bs.perform.models.venue.Seat;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@DynamoDbBean
@Data
public class SeatGrade {

    private String id;
    private Grade grade;
    private BigDecimal price;
    private int seatCount;
    private List<String> seatList;

    public SeatGrade() {
        this.id = UUID.randomUUID().toString();
    }

    @Builder
    public SeatGrade(Grade grade, List<String> seatList, BigDecimal price, int seatCount) {
        this.id = UUID.randomUUID().toString();
        this.grade = Objects.requireNonNull(grade, "grade must not be null");
        this.seatList = Objects.requireNonNull(seatList, "seatList must not be null");
        this.price = Objects.requireNonNull(price, "price must not be null");
        if (seatCount <= 0) {
            throw new IllegalArgumentException("seatCount must be greater than zero");
        }
        this.seatCount = seatCount;
    }
}
