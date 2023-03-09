package com.coesi.repository;

import com.coesi.domain.EvaluationCriteria;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the EvaluationCriteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluationCriteriaRepository extends JpaRepository<EvaluationCriteria, Long> {

    Page<EvaluationCriteria> findAllBynGroupIdOrderByDescription(Long id, Pageable pageable);

    @Query("SELECT ec FROM EvaluationCriteria ec"
            + " WHERE ec.nGroup.id = :nGroupId ORDER BY ec.description")
    List<EvaluationCriteria> findAllByNGroup(@Param("nGroupId") Long nGroupId);
}
