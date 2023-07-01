package com.bs.perform.models.venue;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.services.dynamodb.endpoints.internal.Value.Str;

@DynamoDbBean
@Data
public class Seat {

    private String id;
    private char row;
    private int col;

    public Seat() {
        this.id = UUID.randomUUID().toString();
    }

    @Builder
    public Seat(char row, int col) {
        this.id = UUID.randomUUID().toString();
        this.row = row;
        this.col = col;
    }

}
