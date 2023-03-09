package com.coesi.service;

import com.coesi.domain.PerformanceIndicator;
import com.coesi.repository.PerformanceIndicatorRepository;
import com.coesi.service.dto.UserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PerformanceIndicator}.
 */
@Service
@Transactional
public class PerformanceIndicatorService {

    private final Logger log = LoggerFactory.getLogger(PerformanceIndicatorService.class);

    private final PerformanceIndicatorRepository performanceIndicatorRepository;

    public PerformanceIndicatorService(PerformanceIndicatorRepository performanceIndicatorRepository) {
        this.performanceIndicatorRepository = performanceIndicatorRepository;
    }

    /**
     * Save a performanceIndicator.
     *
     * @param performanceIndicator the entity to save.
     * @return the persisted entity.
     */
    public PerformanceIndicator save(PerformanceIndicator performanceIndicator) {
        log.debug("Request to save PerformanceIndicator : {}", performanceIndicator);
        return performanceIndicatorRepository.save(performanceIndicator);
    }

    /**
     * Get all the performanceIndicators.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PerformanceIndicator> findAll(UserDTO user, Pageable pageable) {
        return performanceIndicatorRepository.findAll(pageable);
    }

    /**
     * Get one performanceIndicator by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PerformanceIndicator> findOne(Long id) {
        log.debug("Request to get PerformanceIndicator : {}", id);
        return performanceIndicatorRepository.findById(id);
    }

    /**
     * Delete the performanceIndicator by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PerformanceIndicator : {}", id);
        performanceIndicatorRepository.deleteById(id);
    }

    public List<PerformanceIndicator> getAllPerformanceIndicatorsList() {
        return performanceIndicatorRepository.findAll();
    }
}
