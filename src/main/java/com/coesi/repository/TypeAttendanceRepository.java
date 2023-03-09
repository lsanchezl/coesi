package com.coesi.repository;

import com.coesi.domain.TypeAttendance;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the TypeAttendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeAttendanceRepository extends JpaRepository<TypeAttendance, Long> {

    TypeAttendance findByName(String name);
}
