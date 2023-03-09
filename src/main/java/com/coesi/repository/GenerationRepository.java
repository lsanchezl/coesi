package com.coesi.repository;

import com.coesi.domain.Generation;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Generation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenerationRepository extends JpaRepository<Generation, Long> {

}
