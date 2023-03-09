package com.coesi.repository;

import com.coesi.domain.SchoolCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the SchoolCycle entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SchoolCycleRepository extends JpaRepository<SchoolCycle, Long> {

    @Query("SELECT sc FROM SchoolCycle sc"
            + " WHERE sc IN (SELECT ng.schoolCycle FROM NGroup ng WHERE ng.teacher.user.login = :login)")
    Page<SchoolCycle> findFilteredTeacher(@Param("login") String login, Pageable pageable);

    @Query("SELECT sc FROM SchoolCycle sc"
            + " WHERE sc IN (SELECT gs.nGroup.schoolCycle FROM GroupStudent gs WHERE gs.student.user.login = :login)")
    Page<SchoolCycle> findFilteredStudent(@Param("login") String login, Pageable pageable);
}
