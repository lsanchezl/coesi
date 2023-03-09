package com.coesi.service;

import com.coesi.domain.TypeAttendance;
import com.coesi.repository.TypeAttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TypeAttendance}.
 */
@Service
@Transactional
public class TypeAttendanceService {

    private final Logger log = LoggerFactory.getLogger(TypeAttendanceService.class);

    private final TypeAttendanceRepository typeAttendanceRepository;

    public TypeAttendanceService(TypeAttendanceRepository typeAttendanceRepository) {
        this.typeAttendanceRepository = typeAttendanceRepository;
    }

    /**
     * Save a typeAttendance.
     *
     * @param typeAttendance the entity to save.
     * @return the persisted entity.
     */
    public TypeAttendance save(TypeAttendance typeAttendance) {
        log.debug("Request to save TypeAttendance : {}", typeAttendance);
        return typeAttendanceRepository.save(typeAttendance);
    }

    /**
     * Get all the typeAttendances.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TypeAttendance> findAll(Pageable pageable) {
        log.debug("Request to get all TypeAttendances");
        return typeAttendanceRepository.findAll(pageable);
    }


    /**
     * Get one typeAttendance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TypeAttendance> findOne(Long id) {
        log.debug("Request to get TypeAttendance : {}", id);
        return typeAttendanceRepository.findById(id);
    }

    /**
     * Delete the typeAttendance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TypeAttendance : {}", id);
        typeAttendanceRepository.deleteById(id);
    }
}
