package com.bs.perform.models.venue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@DynamoDbBean
@Data
public class Venue {

    private String id;
    private String name;
    private String location;
    private List<Seat> seatList;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private LocalDateTime deletedDate;

    @DynamoDbPartitionKey
    public String getId() {
        return id;
    }

    public Venue() {
        this.id = UUID.randomUUID().toString();
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    @Builder
    public Venue(String name, String location, List<Seat> seatList) {
        this.id = UUID.randomUUID().toString();
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.location = Objects.requireNonNull(location, "location cannot be null");
        this.seatList = Objects.requireNonNull(seatList, "seatList cannot be null");
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

}
