package com.coesi.service;

import com.coesi.domain.NClass;
import com.coesi.repository.NClassRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NClass}.
 */
@Service
@Transactional
public class NClassService {

    private final Logger log = LoggerFactory.getLogger(NClassService.class);

    private final NClassRepository nClassRepository;

    public NClassService(NClassRepository nClassRepository) {
        this.nClassRepository = nClassRepository;
    }

    /**
     * Save a nClass.
     *
     * @param nClass the entity to save.
     * @return the persisted entity.
     */
    public NClass save(NClass nClass) {
        log.debug("Request to save NClass : {}", nClass);
        return nClassRepository.save(nClass);
    }

    /**
     * Get all the nClasses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NClass> findAll(Pageable pageable) {
        log.debug("Request to get all NClasses");
        return nClassRepository.findAll(pageable);
    }

    /**
     * Get all the nClasses for the parameter careerId
     *
     * @param careerId
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NClass> findAllByCareerId(Long careerId, Pageable pageable) {
        log.debug("Request to get all NClasses");
        return nClassRepository.findAllByCareerId(careerId, pageable);
    }

    /**
     * Get one nClass by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NClass> findOne(Long id) {
        log.debug("Request to get NClass : {}", id);
        return nClassRepository.findById(id);
    }

    /**
     * Delete the nClass by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NClass : {}", id);
        nClassRepository.deleteById(id);
    }

    public List<NClass> findAllByCareerIdList(Long careerId) {
        return nClassRepository.findAllByCareerIdList(careerId);
    }
}
