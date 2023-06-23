package com.bs.perform.repository;

import com.bs.perform.models.Performance;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PerformanceRepository {


    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private DynamoDbTable<Performance> performanceTable;

    @PostConstruct
    public void init() {
        this.performanceTable = this.dynamoDbEnhancedClient.table("BS-Performance", TableSchema.fromBean(Performance.class));
    }

    public void createPerformance(Performance performance) {

        performanceTable.putItem(performance);
    }

    public void updatePerformance(String id, Performance performance) {

        Performance performanceForUpdate = performanceTable.getItem(Key.builder().partitionValue(id).build());
        performanceForUpdate.setTitle(performance.getTitle());
        performanceForUpdate.setDescription(performance.getDescription());
        performanceForUpdate.setLastModifiedDate(LocalDateTime.now());

        performanceTable.updateItem(performanceForUpdate);
    }

    public Performance getPerformanceById(String id) {

        return performanceTable.getItem(Key.builder().partitionValue(id).build());
    }
}
