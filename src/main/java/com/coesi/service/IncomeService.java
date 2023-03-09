package com.coesi.service;

import com.coesi.domain.Income;
import com.coesi.repository.IncomeRepository;
import com.coesi.repository.UserRepository;
import com.coesi.service.dto.UserDTO;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service Implementation for managing {@link Income}.
 */
@Service
@Transactional
public class IncomeService {

    private final Logger log = LoggerFactory.getLogger(IncomeService.class);

    private final IncomeRepository incomeRepository;

    private final UserRepository userRepository;

    public IncomeService(IncomeRepository incomeRepository, UserRepository userRepository) {
        this.incomeRepository = incomeRepository;
        this.userRepository = userRepository;
    }

    /**
     * Save a income.
     *
     * @param user
     * @param income the entity to save.
     * @return the persisted entity.
     */
    public Income save(UserDTO user, Income income) {
        log.debug("Request to save Income : {}", income);
        if (income.getId() == null) {
            income.setCreationDate(Instant.now());
            income.setCreationUser(userRepository.findOneByLogin(user.getLogin()).get());
        }
        income.setModificationDate(Instant.now());
        income.setModificationUser(userRepository.findOneByLogin(user.getLogin()).get());
        return incomeRepository.save(income);
    }

    /**
     * Get all the incomes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Income> findAll() {
        log.debug("Request to get all Incomes");
        return incomeRepository.findAll();
    }

    /**
     * Get one income by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Income> findOne(Long id) {
        log.debug("Request to get Income : {}", id);
        return incomeRepository.findById(id);
    }

    /**
     * Delete the income by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Income : {}", id);
        incomeRepository.deleteById(id);
    }
}
