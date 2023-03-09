package com.coesi.service;

import com.coesi.domain.Modality;
import com.coesi.repository.ModalityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Modality}.
 */
@Service
@Transactional
public class ModalityService {

    private final Logger log = LoggerFactory.getLogger(ModalityService.class);

    private final ModalityRepository modalityRepository;

    public ModalityService(ModalityRepository modalityRepository) {
        this.modalityRepository = modalityRepository;
    }

    /**
     * Save a modality.
     *
     * @param modality the entity to save.
     * @return the persisted entity.
     */
    public Modality save(Modality modality) {
        log.debug("Request to save Modality : {}", modality);
        return modalityRepository.save(modality);
    }

    /**
     * Get all the modalities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Modality> findAll(Pageable pageable) {
        log.debug("Request to get all Modalities");
        return modalityRepository.findAll(pageable);
    }


    /**
     * Get one modality by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Modality> findOne(Long id) {
        log.debug("Request to get Modality : {}", id);
        return modalityRepository.findById(id);
    }

    /**
     * Delete the modality by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Modality : {}", id);
        modalityRepository.deleteById(id);
    }
}
