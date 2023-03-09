package com.coesi.repository;

import com.coesi.domain.PerformanceIndicator;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the PerformanceIndicator entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PerformanceIndicatorRepository extends JpaRepository<PerformanceIndicator, Long> {

    @Query("SELECT pi FROM PerformanceIndicator pi ORDER BY pi.name")
    @Override
    List<PerformanceIndicator> findAll();
}
