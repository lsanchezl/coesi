package com.coesi.service;

import com.coesi.domain.Career;
import com.coesi.repository.CareerRepository;
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
 * Service Implementation for managing {@link Career}.
 */
@Service
@Transactional
public class CareerService {

    private final Logger log = LoggerFactory.getLogger(CareerService.class);

    private final CareerRepository careerRepository;

    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    /**
     * Save a career.
     *
     * @param career the entity to save.
     * @return the persisted entity.
     */
    public Career save(Career career) {
        log.debug("Request to save Career : {}", career);
        return careerRepository.save(career);
    }

    /**
     * Get all the careers.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Career> findAll(UserDTO user, Pageable pageable) {
        if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)
                || user.getAuthorities().contains(AuthoritiesConstants.TREASURER)) {
            return careerRepository.findAll(pageable);
        } else if (user.getAuthorities().contains(AuthoritiesConstants.TEACHER)) {
            return careerRepository.findFilteredTeacher(user.getLogin(), pageable);
        } else {
            return careerRepository.findFilteredStudent(user.getLogin(), pageable);
        }
    }

    /**
     * Get one career by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Career> findOne(Long id
    ) {
        log.debug("Request to get Career : {}", id);
        return careerRepository.findById(id);
    }

    /**
     * Delete the career by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Career : {}", id);
        careerRepository.deleteById(id);
    }
}
