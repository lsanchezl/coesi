package com.coesi.service;

import com.coesi.domain.SchoolCycle;
import com.coesi.repository.SchoolCycleRepository;
import com.coesi.security.AuthoritiesConstants;
import com.coesi.service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link SchoolCycle}.
 */
@Service
@Transactional
public class SchoolCycleService {

    private final Logger log = LoggerFactory.getLogger(SchoolCycleService.class);

    private final SchoolCycleRepository schoolCycleRepository;

    public SchoolCycleService(SchoolCycleRepository schoolCycleRepository) {
        this.schoolCycleRepository = schoolCycleRepository;
    }

    /**
     * Save a schoolCycle.
     *
     * @param schoolCycle the entity to save.
     * @return the persisted entity.
     */
    public SchoolCycle save(SchoolCycle schoolCycle) {
        log.debug("Request to save SchoolCycle : {}", schoolCycle);
        return schoolCycleRepository.save(schoolCycle);
    }

    /**
     * Get all the schoolCycles.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SchoolCycle> findAll(UserDTO user, Pageable pageable) {
        if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)
                || user.getAuthorities().contains(AuthoritiesConstants.TREASURER)) {
            return schoolCycleRepository.findAll(pageable);
        } else if (user.getAuthorities().contains(AuthoritiesConstants.TEACHER)) {
            return schoolCycleRepository.findFilteredTeacher(user.getLogin(), pageable);
        } else {
            return schoolCycleRepository.findFilteredStudent(user.getLogin(), pageable);
        }
    }

    /**
     * Get one schoolCycle by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SchoolCycle> findOne(Long id) {
        log.debug("Request to get SchoolCycle : {}", id);
        return schoolCycleRepository.findById(id);
    }

    /**
     * Delete the schoolCycle by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SchoolCycle : {}", id);
        schoolCycleRepository.deleteById(id);
    }
}
