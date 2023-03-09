package com.coesi.repository;

import com.coesi.domain.StudentEvaluation;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the StudentEvaluation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StudentEvaluationRepository extends JpaRepository<StudentEvaluation, Long> {

    @Query("SELECT se FROM StudentEvaluation se"
            + " WHERE se.groupStudent.nGroup.id = :nGroupId ORDER BY se.groupStudent.student.user.lastName")
    List<StudentEvaluation> findAllByGroup(@Param("nGroupId") Long nGroupId);

    @Query("SELECT se FROM StudentEvaluation se"
            + " WHERE se.groupStudent.id = :groupStudentId ORDER BY se.groupStudent.student.user.lastName")
    List<StudentEvaluation> findAllByGroupStudent(@Param("groupStudentId") Long groupStudentId);

    @Query("SELECT se FROM StudentEvaluation se"
            + " WHERE se.groupStudent.nGroup = ("
            + "     SELECT gs.nGroup FROM GroupStudent gs"
            + "     WHERE gs.id = :groupStudentId"
            + ")")
    List<StudentEvaluation> findAllEvaluationsGroups(@Param("groupStudentId") Long groupStudentId);

    @Query("SELECT se FROM StudentEvaluation se"
            + " WHERE se.groupStudent.id = :groupStudentId AND se.evaluationCriteria.id = :evaluationCriteriaId")
    StudentEvaluation findByGroupStudentAndEvaluationCriteria(@Param("groupStudentId") Long groupStudentId,
            @Param("evaluationCriteriaId") Long evaluationCriteriaId);
}
