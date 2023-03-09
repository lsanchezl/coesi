package com.coesi.service;

import com.coesi.domain.Generation;
import com.coesi.repository.GenerationRepository;
import com.coesi.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Generation}.
 */
@Service
@Transactional
public class GenerationService {

    private final Logger log = LoggerFactory.getLogger(GenerationService.class);

    private final GenerationRepository generationRepository;

    public GenerationService(GenerationRepository generationRepository) {
        this.generationRepository = generationRepository;
    }

    /**
     * Save a generation.
     *
     * @param generation the entity to save.
     * @return the persisted entity.
     */
    public Generation save(Generation generation) {
        log.debug("Request to save Generation : {}", generation);
        return generationRepository.save(generation);
    }

    /**
     * Get all the generations.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Generation> findAll(UserDTO user, Pageable pageable) {
        return generationRepository.findAll(pageable);
    }

    /**
     * Get one generation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Generation> findOne(Long id) {
        log.debug("Request to get Generation : {}", id);
        return generationRepository.findById(id);
    }

    /**
     * Delete the generation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Generation : {}", id);
        generationRepository.deleteById(id);
    }
}
