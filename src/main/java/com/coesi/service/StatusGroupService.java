package com.coesi.service;

import com.coesi.domain.StatusGroup;
import com.coesi.repository.StatusGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link StatusGroup}.
 */
@Service
@Transactional
public class StatusGroupService {

    private final Logger log = LoggerFactory.getLogger(StatusGroupService.class);

    private final StatusGroupRepository statusGroupRepository;

    public StatusGroupService(StatusGroupRepository statusGroupRepository) {
        this.statusGroupRepository = statusGroupRepository;
    }

    /**
     * Save a statusGroup.
     *
     * @param statusGroup the entity to save.
     * @return the persisted entity.
     */
    public StatusGroup save(StatusGroup statusGroup) {
        log.debug("Request to save StatusGroup : {}", statusGroup);
        return statusGroupRepository.save(statusGroup);
    }

    /**
     * Get all the statusGroups.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<StatusGroup> findAll() {
        log.debug("Request to get all StatusGroups");
        return statusGroupRepository.findAll();
    }

    /**
     * Get one statusGroup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<StatusGroup> findOne(Long id) {
        log.debug("Request to get StatusGroup : {}", id);
        return statusGroupRepository.findById(id);
    }

    /**
     * Delete the statusGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete StatusGroup : {}", id);
        statusGroupRepository.deleteById(id);
    }
}
