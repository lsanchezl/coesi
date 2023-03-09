package com.coesi.service;

import com.coesi.domain.Room;
import com.coesi.repository.RoomRepository;
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
 * Service Implementation for managing {@link Room}.
 */
@Service
@Transactional
public class RoomService {

    private final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Save a room.
     *
     * @param room the entity to save.
     * @return the persisted entity.
     */
    public Room save(Room room) {
        log.debug("Request to save Room : {}", room);
        return roomRepository.save(room);
    }

    /**
     * Get all the rooms.
     *
     * @param user
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Room> findAll(UserDTO user, Pageable pageable) {
        if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)
                || user.getAuthorities().contains(AuthoritiesConstants.TREASURER)) {
            return roomRepository.findAll(pageable);
        } else if (user.getAuthorities().contains(AuthoritiesConstants.TEACHER)) {
            return roomRepository.findFilteredTeacher(user.getLogin(), pageable);
        } else {
            return roomRepository.findFilteredStudent(user.getLogin(), pageable);
        }
    }

    /**
     * Get one room by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Room> findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        return roomRepository.findById(id);
    }

    /**
     * Delete the room by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);
        roomRepository.deleteById(id);
    }
}
