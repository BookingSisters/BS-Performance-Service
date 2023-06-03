package com.bs.perform.services;

import com.bs.perform.models.Performance;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private DynamoDbTable<Performance> performanceTable;

    @PostConstruct
    public void init() {
        this.performanceTable = this.dynamoDbEnhancedClient.table("BS-Performance", TableSchema.fromBean(Performance.class));
    }

    public Performance createPerformance(Performance performance) {

        try {
            performanceTable.putItem(performance);
        } catch (ResourceNotFoundException e) {
            log.error("Be sure that it exists and that you've typed its name correctly!");
        } catch (DynamoDbException e) {
            log.error(e.getMessage());
        }

        return performance;
    }

    public Performance getPerformance(String id) {
        Performance performance = performanceTable.getItem(Key.builder().partitionValue(id).build());
        return performance;
    }

}
