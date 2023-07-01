package com.bs.perform.repositories;

import com.bs.perform.models.performance.Performance;

public interface PerformanceRepository {

    void createPerformance(Performance performance);

    void updatePerformance(String id, Performance performance);

    Performance getPerformanceById(String id);

}
