package com.coesi.repository;

import com.coesi.domain.Career;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Career entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    @Query("SELECT c FROM Career c"
            + " WHERE c IN (SELECT ng.nClass.career FROM NGroup ng WHERE ng.teacher.user.login = :login)")
    Page<Career> findFilteredTeacher(@Param("login") String login, Pageable pageable);

    @Query("SELECT c FROM Career c"
            + " WHERE c IN (SELECT gs.nGroup.nClass.career FROM GroupStudent gs WHERE gs.student.user.login = :login)")
    Page<Career> findFilteredStudent(@Param("login") String login, Pageable pageable);
}
