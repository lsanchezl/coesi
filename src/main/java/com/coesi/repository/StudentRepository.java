package com.coesi.repository;

import com.coesi.domain.Student;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data repository for the Student entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


    @Query("SELECT s FROM Student s ORDER BY s.user.lastName, s.user.firstName")
    @Override
    List<Student> findAll();
}
