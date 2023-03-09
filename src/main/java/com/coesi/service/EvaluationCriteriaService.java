package com.coesi.service;

import com.coesi.domain.EvaluationCriteria;
import com.coesi.domain.NClass;
import com.coesi.repository.EvaluationCriteriaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EvaluationCriteria}.
 */
@Service
@Transactional
public class EvaluationCriteriaService {

    private final Logger log = LoggerFactory.getLogger(EvaluationCriteriaService.class);

    private final EvaluationCriteriaRepository evaluationCriteriaRepository;

    public EvaluationCriteriaService(EvaluationCriteriaRepository evaluationCriteriaRepository) {
        this.evaluationCriteriaRepository = evaluationCriteriaRepository;
    }

    /**
     * Save a evaluationCriteria.
     *
     * @param evaluationCriteria the entity to save.
     * @return the persisted entity.
     */
    public EvaluationCriteria save(EvaluationCriteria evaluationCriteria) {
        log.debug("Request to save EvaluationCriteria : {}", evaluationCriteria);
        return evaluationCriteriaRepository.save(evaluationCriteria);
    }

    /**
     * Get all the evaluationCriteria.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluationCriteria> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluationCriteria");
        return evaluationCriteriaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<EvaluationCriteria> findAllByNGroupId(Long nGroupId, Pageable pageable) {
        log.debug("Request to get all EvaluationCriteria");
        return evaluationCriteriaRepository.findAllBynGroupIdOrderByDescription(nGroupId, pageable);
    }

    /**
     * Get one evaluationCriteria by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EvaluationCriteria> findOne(Long id) {
        log.debug("Request to get EvaluationCriteria : {}", id);
        return evaluationCriteriaRepository.findById(id);
    }

    /**
     * Delete the evaluationCriteria by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EvaluationCriteria : {}", id);
        evaluationCriteriaRepository.deleteById(id);
    }
}
