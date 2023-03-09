package com.coesi.repository;

import com.coesi.domain.Attendance;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Attendance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT att FROM Attendance att"
            + " WHERE att.groupStudent.nGroup.id = :nGroupId ORDER BY att.groupStudent.student.user.lastName")
    List<Attendance> findAllByNGroup(@Param("nGroupId") Long nGroupId);

    @Query("SELECT att FROM Attendance att"
            + " WHERE att.groupStudent.student.user.login = :login"
            + " AND att.groupStudent.nGroup.id = :nGroupId")
    List<Attendance> findByNGroupIdAndLogin(@Param("nGroupId") Long nGroupId, @Param("login") String login);
}
