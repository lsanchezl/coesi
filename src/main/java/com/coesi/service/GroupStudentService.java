package com.coesi.service;

import com.coesi.domain.GroupStudent;
import com.coesi.model.AttendanceMatrix;
import com.coesi.model.StudentGroupStats;
import com.coesi.repository.GroupStudentRepository;
import com.coesi.service.dto.UserDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Service Implementation for managing {@link GroupStudent}.
 */
@Service
@Transactional
public class GroupStudentService {

    private final Logger log = LoggerFactory.getLogger(GroupStudentService.class);

    @Autowired
    private StudentEvaluationService studentEvaluationService;

    @Autowired
    private AttendanceService attendanceService;

    private final GroupStudentRepository groupStudentRepository;

    public GroupStudentService(GroupStudentRepository groupStudentRepository) {
        this.groupStudentRepository = groupStudentRepository;
    }

    /**
     * Save a groupStudent.
     *
     * @param groupStudent the entity to save.
     * @return the persisted entity.
     */
    public GroupStudent save(GroupStudent groupStudent) {
        log.debug("Request to save GroupStudent : {}", groupStudent);
        return groupStudentRepository.save(groupStudent);
    }

    /**
     * Get all the groupStudents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<GroupStudent> findAll(Pageable pageable) {
        log.debug("Request to get all GroupStudents");
        return groupStudentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Optional<List<GroupStudent>> findAllByNGroupId(Long nGroupId) {
        log.debug("Request to get all GroupStudent id " + nGroupId);
        return Optional.of(groupStudentRepository.findAllByNGroupId(nGroupId));
    }

    /**
     * Get one groupStudent by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GroupStudent> findOne(Long id) {
        log.debug("Request to get GroupStudent : {}", id);
        return groupStudentRepository.findById(id);
    }

    /**
     * Delete the groupStudent by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GroupStudent : {}", id);
        groupStudentRepository.deleteById(id);
    }

    public Optional<StudentGroupStats> findStudentStatsGroup(UserDTO user, Long nGroupId) {
        StudentGroupStats studentGroupStats = new StudentGroupStats();

        Optional<String[][]> studentEvaluationMatrix = studentEvaluationService.findByGroupMatrix(nGroupId, user.getLogin());
        studentGroupStats.setEvaluation(studentEvaluationMatrix.get());

        Optional<AttendanceMatrix> attendanceMatrix = attendanceService.findByGroupAndLoginMatrix(user, nGroupId);
        studentGroupStats.setAttendanceMatrix(attendanceMatrix.get());

        return Optional.of(studentGroupStats);
    }
}
