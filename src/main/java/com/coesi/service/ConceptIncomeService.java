package com.coesi.service;

import com.coesi.domain.ConceptIncome;
import com.coesi.repository.ConceptIncomeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ConceptIncome}.
 */
@Service
@Transactional
public class ConceptIncomeService {

    private final Logger log = LoggerFactory.getLogger(ConceptIncomeService.class);

    private final ConceptIncomeRepository conceptIncomeRepository;

    public ConceptIncomeService(ConceptIncomeRepository conceptIncomeRepository) {
        this.conceptIncomeRepository = conceptIncomeRepository;
    }

    /**
     * Save a conceptIncome.
     *
     * @param conceptIncome the entity to save.
     * @return the persisted entity.
     */
    public ConceptIncome save(ConceptIncome conceptIncome) {
        log.debug("Request to save ConceptIncome : {}", conceptIncome);
        return conceptIncomeRepository.save(conceptIncome);
    }

    /**
     * Get all the conceptIncomes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConceptIncome> findAll() {
        log.debug("Request to get all ConceptIncomes");
        return conceptIncomeRepository.findAll();
    }


    /**
     * Get one conceptIncome by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConceptIncome> findOne(Long id) {
        log.debug("Request to get ConceptIncome : {}", id);
        return conceptIncomeRepository.findById(id);
    }

    /**
     * Delete the conceptIncome by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConceptIncome : {}", id);
        conceptIncomeRepository.deleteById(id);
    }
}
