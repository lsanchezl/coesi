package com.coesi.repository;

import com.coesi.domain.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the Room entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r"
            + " WHERE r IN (SELECT ng.room FROM NGroup ng WHERE ng.teacher.user.login = :login)")
    Page<Room> findFilteredTeacher(@Param("login") String login, Pageable pageable);

    @Query("SELECT r FROM Room r"
            + " WHERE r IN (SELECT gs.nGroup.room FROM GroupStudent gs WHERE gs.student.user.login = :login)")
    Page<Room> findFilteredStudent(@Param("login") String login, Pageable pageable);
}
