package com.coesi.repository;

import com.coesi.domain.GroupStudent;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the GroupStudent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, Long> {

    @Query("SELECT gs FROM GroupStudent gs"
            + " WHERE gs.nGroup.id = :nGroupId ORDER BY gs.student.user.lastName")
    List<GroupStudent> findAllByNGroupId(@Param("nGroupId") Long nGroupId);

    @Query("SELECT gs FROM GroupStudent gs"
            + " WHERE gs.nGroup.id = :nGroupId"
            + " AND gs.student.user.login = :login")
    GroupStudent findByNGroupIdAndLogin(@Param("nGroupId") Long nGroupId, @Param("login") String login);
}
