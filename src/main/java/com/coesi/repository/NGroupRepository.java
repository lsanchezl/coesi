package com.coesi.repository;

import com.coesi.domain.NGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the NGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NGroupRepository extends JpaRepository<NGroup, Long> {

    @Query("SELECT ng FROM NGroup ng"
            + " WHERE (ng.nClass.career.id = :careerId OR -1 = :careerId)"
            + " AND (ng.schoolCycle.id = :schoolCycleId OR -1 = :schoolCycleId)"
            + " AND (ng.room.id = :roomId OR -1 = :roomId)"
            + " ORDER BY ng.startDate")
    Page<NGroup> findFiltered(@Param("careerId") Long careerId, @Param("schoolCycleId") Long schoolCycleId,
            @Param("roomId") Long roomId, Pageable pageable);

    @Query("SELECT ng FROM NGroup ng"
            + " WHERE (ng.nClass.career.id = :careerId OR -1 = :careerId)"
            + " AND (ng.schoolCycle.id = :schoolCycleId OR -1 = :schoolCycleId)"
            + " AND (ng.room.id = :roomId OR -1 = :roomId)"
            + " AND ng.statusGroup.keyStatus <> 'CREATED'"
            + " AND ng.teacher.user.login = :login"
            + " ORDER BY ng.startDate")
    Page<NGroup> findFilteredTeacher(@Param("login") String login, @Param("careerId") Long careerId, @Param("schoolCycleId") Long schoolCycleId,
            @Param("roomId") Long roomId, Pageable pageable);

    @Query("SELECT ng FROM NGroup ng"
            + " WHERE (ng.nClass.career.id = :careerId OR -1 = :careerId)"
            + " AND (ng.schoolCycle.id = :schoolCycleId OR -1 = :schoolCycleId)"
            + " AND (ng.room.id = :roomId OR -1 = :roomId)"
            + " AND ng IN ("
            + "     SELECT gs.nGroup FROM GroupStudent gs"
            + "     WHERE gs.student.user.login = :login"
            + "     AND gs.nGroup.endDate <= ("
            + "         SELECT max(i.applicationDate) FROM Income i"
            + "         WHERE i.user = gs.student.user"
            + "         AND i.conceptIncome.keyConcept = 'MENSUALIDAD'"
            + "     )"
            + " )"
            + " ORDER BY ng.startDate")
    Page<NGroup> findFilteredStudent(@Param("login") String login, @Param("careerId") Long careerId, @Param("schoolCycleId") Long schoolCycleId,
            @Param("roomId") Long roomId, Pageable pageable);
}
