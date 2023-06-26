package com.bs.perform.repository;

import com.bs.perform.models.Performance;

public interface PerformanceRepository {

    void createPerformance(Performance performance);
    void updatePerformance(String id, Performance performance);
    Performance getPerformanceById(String id);

}
