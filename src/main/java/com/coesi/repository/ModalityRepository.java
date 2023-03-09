package com.coesi.repository;

import com.coesi.domain.Modality;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Modality entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ModalityRepository extends JpaRepository<Modality, Long> {
}
