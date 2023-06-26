package com.bs.perform.repository;

import com.bs.perform.exceptions.ResourceNotFoundException;
import com.bs.perform.models.Performance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PerformanceRepositoryImpl implements PerformanceRepository {

    private final DynamoDbTable<Performance> performanceTable;

    @Override
    public void createPerformance(Performance performance) {

        performanceTable.putItem(performance);
    }

    @Override
    public void updatePerformance(final String id, final Performance performance) {

        Performance performanceForUpdate = performanceTable.getItem(Key.builder().partitionValue(id).build());

        if (performanceForUpdate == null) {
            throw new ResourceNotFoundException(id);
        }

        BeanUtils.copyProperties(performance, performanceForUpdate, "id");
        performanceForUpdate.setLastModifiedDate(LocalDateTime.now());

        performanceTable.updateItem(performanceForUpdate);
    }

    @Override
    public Performance getPerformanceById(final String id) {

        return performanceTable.getItem(Key.builder().partitionValue(id).build());
    }
}
