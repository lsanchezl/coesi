package com.coesi.repository;

import com.coesi.domain.ConceptIncome;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ConceptIncome entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConceptIncomeRepository extends JpaRepository<ConceptIncome, Long> {
}
