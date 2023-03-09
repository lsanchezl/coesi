package com.coesi.repository;

import com.coesi.domain.StatusGroup;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the StatusGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusGroupRepository extends JpaRepository<StatusGroup, Long> {

    StatusGroup findByKeyStatus(String keyStatus);
}
