package com.coesi.web.rest;

import com.coesi.domain.Attendance;
import com.coesi.model.GeneralParam;
import com.coesi.model.AttendanceMatrix;
import com.coesi.service.AttendanceService;
import com.coesi.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.coesi.domain.Attendance}.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

    private static final String ENTITY_NAME = "attendance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendanceService attendanceService;

    public AttendanceResource(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * {@code POST  /attendances} : Create a new attendance.
     *
     * @param attendance the attendance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new attendance, or with status {@code 400 (Bad Request)} if
     * the attendance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendances")
    public ResponseEntity<Attendance> createAttendance(@Valid @RequestBody Attendance attendance) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendance);
        if (attendance.getId() != null) {
            throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Attendance result = attendanceService.save(attendance);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * Recibe una fecha y un grupo, e inserta las asistencias para todos los
     * alumnos de ese grupo en esa fecha.
     *
     * @param generalParam
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/attendances/n-group")
    public ResponseEntity<List<Attendance>> createByGroup(@RequestBody GeneralParam generalParam)
            throws URISyntaxException {
        log.debug("REST request to save Group Attendance : {}", generalParam.getId());
        List<Attendance> result = attendanceService.createByGroup(generalParam);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Recibe un attendance_id y type_attendance_id.
     *
     * @param generalParam
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/attendances")
    public ResponseEntity<Attendance> updateAttendance(@RequestBody GeneralParam generalParam)
            throws URISyntaxException {
        log.debug("REST request to update Attendance : {} - {}", generalParam.getId(), generalParam.getLong());
        Attendance result = attendanceService.update(generalParam);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, generalParam.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /attendances} : get all the attendances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of attendances in body.
     */
    @GetMapping("/attendances")
    public ResponseEntity<List<Attendance>> getAllAttendances(Pageable pageable) {
        log.debug("REST request to get a page of Attendances");
        Page<Attendance> page = attendanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Busca y ordena en una matriz las asistencias del grupo indicado.
     *
     * @param id
     * @return
     */
    @GetMapping("/attendances/n-group/{id}")
    public ResponseEntity<AttendanceMatrix> findByGroupMatrix(@PathVariable Long id) {
        log.debug("REST request to get a page of Attendances Matrix for groupId: {}", id);
        return ResponseUtil.wrapOrNotFound(attendanceService.findByGroupMatrix(id));
    }

    /**
     * {@code GET  /attendances/:id} : get the "id" attendance.
     *
     * @param id the id of the attendance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the attendance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendances/{id}")
    public ResponseEntity<Attendance> getAttendance(@PathVariable Long id) {
        log.debug("REST request to get Attendance : {}", id);
        Optional<Attendance> attendance = attendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendance);
    }

    /**
     * {@code DELETE  /attendances/:id} : delete the "id" attendance.
     *
     * @param id the id of the attendance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendances/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        attendanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
