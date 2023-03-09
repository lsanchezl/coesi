package com.coesi.repository;

import com.coesi.domain.Income;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Income entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("select income from Income income where income.user.login = ?#{principal.username}")
    List<Income> findByUserIsCurrentUser();

    @Query("select income from Income income where income.creationUser.login = ?#{principal.username}")
    List<Income> findByCreationUserIsCurrentUser();

    @Query("select income from Income income where income.modificationUser.login = ?#{principal.username}")
    List<Income> findByModificationUserIsCurrentUser();
}
