package com.coesi.repository;

import com.coesi.domain.Teacher;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data repository for the Teacher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    @Query("SELECT t FROM Teacher t ORDER BY t.user.lastName, t.user.firstName")
    @Override
    List<Teacher> findAll();
}
