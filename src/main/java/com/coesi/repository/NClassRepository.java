package com.coesi.repository;

import com.coesi.domain.NClass;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the NClass entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NClassRepository extends JpaRepository<NClass, Long> {

    Page<NClass> findAllByCareerId(Long id, Pageable pageable);

    @Query("SELECT nc FROM NClass nc WHERE nc.career.id = :careerId")
    List<NClass> findAllByCareerIdList(@Param("careerId") Long careerId);
}
