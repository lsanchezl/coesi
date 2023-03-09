package com.coesi.web.rest;

import com.coesi.domain.GroupStudent;
import com.coesi.model.StudentGroupStats;
import com.coesi.security.AuthoritiesConstants;
import com.coesi.security.SecurityUtils;
import com.coesi.service.GroupStudentService;
import com.coesi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * REST controller for managing {@link com.coesi.domain.GroupStudent}.
 */
@RestController
@RequestMapping("/api")
public class GroupStudentResource {

    private final Logger log = LoggerFactory.getLogger(GroupStudentResource.class);

    private static final String ENTITY_NAME = "groupStudent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupStudentService groupStudentService;

    public GroupStudentResource(GroupStudentService groupStudentService) {
        this.groupStudentService = groupStudentService;
    }

    /**
     * {@code POST  /group-students} : Create a new groupStudent.
     *
     * @param groupStudent the groupStudent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new groupStudent, or with status {@code 400 (Bad Request)}
     * if the groupStudent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-students")
    public ResponseEntity<GroupStudent> createGroupStudent(@Valid @RequestBody GroupStudent groupStudent) throws URISyntaxException {
        log.debug("REST request to save GroupStudent : {}", groupStudent);
        if (groupStudent.getId() != null) {
            throw new BadRequestAlertException("A new groupStudent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupStudent result = groupStudentService.save(groupStudent);
        return ResponseEntity.created(new URI("/api/group-students/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /group-students} : Updates an existing groupStudent.
     *
     * @param groupStudent
     * @param vGroupStudent the groupStudent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated groupStudent, or with status {@code 400 (Bad Request)}
     * if the groupStudent is not valid, or with status
     * {@code 500 (Internal Server Error)} if the groupStudent couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-students")
    public ResponseEntity<GroupStudent> updateGroupStudent(@Valid @RequestBody GroupStudent groupStudent) throws URISyntaxException {
        log.debug("REST request to update GroupStudent : {}", groupStudent);
        if (groupStudent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupStudent result = groupStudentService.save(groupStudent);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupStudent.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /group-students/n-group/:nGroupId} : get all the group
     * students for the parameter nGroupId
     *
     * @param nGroupId
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of evaluationCriteria in body.
     */
    @GetMapping("/group-students/n-group/{nGroupId}")
    @PreAuthorize("hasAnyAuthority('" + AuthoritiesConstants.PRINCIPAL + "','" + AuthoritiesConstants.SECRETARY
            + "','" + AuthoritiesConstants.TEACHER + "')")
    public ResponseEntity<List<GroupStudent>> findAllByNGroupId(@PathVariable Long nGroupId) {
        log.debug("REST request to get a page of GroupStudents for the parameter nGroupId: {}", nGroupId);
        return ResponseUtil.wrapOrNotFound(groupStudentService.findAllByNGroupId(nGroupId));
    }

    /**
     * {@code GET  /group-students/:id} : get the "id" groupStudent.
     *
     * @param id the id of the groupStudent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the groupStudent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-students/{id}")
    public ResponseEntity<GroupStudent> getGroupStudent(@PathVariable Long id) {
        log.debug("REST request to get GroupStudent : {}", id);
        Optional<GroupStudent> groupStudent = groupStudentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(groupStudent);
    }

    /**
     * {@code DELETE  /group-students/:id} : delete the "id" groupStudent.
     *
     * @param id the id of the groupStudent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-students/{id}")
    public ResponseEntity<Void> deleteGroupStudent(@PathVariable Long id) {
        log.debug("REST request to delete GroupStudent : {}", id);
        groupStudentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * Regresa las asistencias y el detalle de calificación de un alumno en el
     * grupo indicado.
     *
     * @param id
     * @return
     */
    @GetMapping("/group-students/student/stats/n-group/{id}")
    public ResponseEntity<StudentGroupStats> findStudentStatsGroup(@PathVariable Long id) {
        log.debug("REST request to get a page of Attendances Matrix for groupId: {}", id);
        return ResponseUtil.wrapOrNotFound(groupStudentService.findStudentStatsGroup(SecurityUtils.getUserDTO(), id));
    }
}
