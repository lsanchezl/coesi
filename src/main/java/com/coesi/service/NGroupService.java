package com.coesi.service;

import com.coesi.config.Constants;
import com.coesi.domain.NGroup;
import com.coesi.domain.StatusGroup;
import com.coesi.repository.NGroupRepository;
import com.coesi.repository.StatusGroupRepository;
import com.coesi.security.AuthoritiesConstants;
import com.coesi.service.dto.UserDTO;
import com.coesi.util.GlobalUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link NGroup}.
 */
@Service
@Transactional
public class NGroupService {

    private final Logger log = LoggerFactory.getLogger(NGroupService.class);

    private final NGroupRepository nGroupRepository;
    private final StatusGroupRepository statusGroupRepository;

    public NGroupService(NGroupRepository nGroupRepository, StatusGroupRepository statusGroupRepository) {
        this.nGroupRepository = nGroupRepository;
        this.statusGroupRepository = statusGroupRepository;
    }

    /**
     * Save a nGroup.
     *
     * @param nGroup the entity to save.
     * @return the persisted entity.
     */
    public NGroup save(NGroup nGroup) {
        if (nGroup.getId() == null) {
            StatusGroup created = statusGroupRepository.findByKeyStatus(Constants.STATUS_CREATED);
            nGroup.setStatusGroup(created);

            // Asignamos un nombre temporal y persistimos
            nGroup.setName("temp-name");
            nGroupRepository.save(nGroup);
        }

        //enviamos el id
        String name = GlobalUtils.createName(nGroup.getId().toString(), nGroup.getNClass().getName(), nGroup.getRoom().getName(),
                nGroup.getModality().getName());
        nGroup.setName(name);
        return nGroupRepository.save(nGroup);
    }

    /**
     * Get all the nGroups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<NGroup> findAll(Pageable pageable) {
        log.debug("Request to get all NGroups");
        return nGroupRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<NGroup> findFiltered(UserDTO user, Long careerId, Long schoolCycleId, Long roomId, Pageable pageable) {
        log.debug("Request to get all NGroups careerId[{}] - schoolCycleId[{}] - roomId[{}]", careerId, schoolCycleId, roomId);

        if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)
                || user.getAuthorities().contains(AuthoritiesConstants.TREASURER)) {
            return nGroupRepository.findFiltered(careerId, schoolCycleId, roomId, pageable);
        } else if (user.getAuthorities().contains(AuthoritiesConstants.TEACHER)) {
            return nGroupRepository.findFilteredTeacher(user.getLogin(), careerId, schoolCycleId, roomId, pageable);
        } else {
            return nGroupRepository.findFilteredStudent(user.getLogin(), careerId, schoolCycleId, roomId, pageable);
        }
    }

    /**
     * Get one nGroup by id.
     *
     * @param user
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NGroup> findOne(UserDTO user, Long id) {
        log.debug("Request to get NGroup : {}", id);
        Optional<NGroup> nGroup = nGroupRepository.findById(id);
        if (nGroup.isPresent()) {
            nGroup.get().setPreviousStatusGroup(findPreviousStatus(user, nGroup));
            nGroup.get().setNextStatusGroup(findNextStatus(user, nGroup));
        }
        return nGroup;
    }

    /**
     * @param user
     * @param nGroup
     * @return
     */
    private StatusGroup findPreviousStatus(UserDTO user, Optional<NGroup> nGroup) {
        StatusGroup statusGroup = null;
        switch (nGroup.get().getStatusGroup().getKeyStatus()) {
            case Constants.STATUS_CREATED:
                //salimos, no hay estatus previo
                break;
            case Constants.STATUS_IN_PROGRESS:
                if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                        || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)) {
                    statusGroup = statusGroupRepository.findByKeyStatus(Constants.STATUS_CREATED);
                }
                break;
            case Constants.STATUS_SENT:
                if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                        || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)) {
                    statusGroup = statusGroupRepository.findByKeyStatus(Constants.STATUS_IN_PROGRESS);
                }
                break;
            case Constants.STATUS_CLOSED:
                if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                        || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)) {
                    statusGroup = statusGroupRepository.findByKeyStatus(Constants.STATUS_SENT);
                }
                break;
        }
        return statusGroup;
    }

    /**
     * @param user
     * @param nGroup
     * @return
     */
    private StatusGroup findNextStatus(UserDTO user, Optional<NGroup> nGroup) {
        StatusGroup statusGroup = null;
        switch (nGroup.get().getStatusGroup().getKeyStatus()) {
            case Constants.STATUS_CREATED:
                if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                        || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)) {
                    statusGroup = statusGroupRepository.findByKeyStatus(Constants.STATUS_IN_PROGRESS);
                }
                break;
            case Constants.STATUS_IN_PROGRESS:
                statusGroup = statusGroupRepository.findByKeyStatus(Constants.STATUS_SENT);
                break;
            case Constants.STATUS_SENT:
                if (user.getAuthorities().contains(AuthoritiesConstants.PRINCIPAL)
                        || user.getAuthorities().contains(AuthoritiesConstants.SECRETARY)) {
                    statusGroup = statusGroupRepository.findByKeyStatus(Constants.STATUS_CLOSED);
                }
                break;
            case Constants.STATUS_CLOSED:
                //salimos, no hay estatus siguiente
                break;
        }
        return statusGroup;
    }

    /**
     * Delete the nGroup by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NGroup : {}", id);
        nGroupRepository.deleteById(id);
    }

//    public NGroup updateStatus(Long id, String status) {
//        NGroup group = findOne(id).get();
//        StatusGroup statusGroup = statusGroupRepository.findByKeyStatus(status);
//
//        group.setStatusGroup(statusGroup);
//        return save(group);
//    }
}
