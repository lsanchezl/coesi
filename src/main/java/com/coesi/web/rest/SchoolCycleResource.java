package com.coesi.web.rest;

import com.coesi.domain.SchoolCycle;
import com.coesi.security.SecurityUtils;
import com.coesi.service.SchoolCycleService;
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
 * REST controller for managing {@link com.coesi.domain.SchoolCycle}.
 */
@RestController
@RequestMapping("/api")
public class SchoolCycleResource {

    private final Logger log = LoggerFactory.getLogger(SchoolCycleResource.class);

    private static final String ENTITY_NAME = "schoolCycle";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SchoolCycleService schoolCycleService;

    public SchoolCycleResource(SchoolCycleService schoolCycleService) {
        this.schoolCycleService = schoolCycleService;
    }

    /**
     * {@code POST  /school-cycles} : Create a new schoolCycle.
     *
     * @param schoolCycle the schoolCycle to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new schoolCycle, or with status {@code 400 (Bad Request)}
     * if the schoolCycle has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/school-cycles")
    public ResponseEntity<SchoolCycle> createSchoolCycle(@Valid @RequestBody SchoolCycle schoolCycle) throws URISyntaxException {
        log.debug("REST request to save SchoolCycle : {}", schoolCycle);
        if (schoolCycle.getId() != null) {
            throw new BadRequestAlertException("A new schoolCycle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolCycle result = schoolCycleService.save(schoolCycle);
        return ResponseEntity.created(new URI("/api/school-cycles/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /school-cycles} : Updates an existing schoolCycle.
     *
     * @param schoolCycle the schoolCycle to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated schoolCycle, or with status {@code 400 (Bad Request)} if
     * the schoolCycle is not valid, or with status
     * {@code 500 (Internal Server Error)} if the schoolCycle couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/school-cycles")
    public ResponseEntity<SchoolCycle> updateSchoolCycle(@Valid @RequestBody SchoolCycle schoolCycle) throws URISyntaxException {
        log.debug("REST request to update SchoolCycle : {}", schoolCycle);
        if (schoolCycle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchoolCycle result = schoolCycleService.save(schoolCycle);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, schoolCycle.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /school-cycles} : get all the schoolCycles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of schoolCycles in body.
     */
    @GetMapping("/school-cycles")
    public ResponseEntity<List<SchoolCycle>> getAllSchoolCycles(Pageable pageable) {
        log.debug("REST request to get a page of SchoolCycles");
        Page<SchoolCycle> page = schoolCycleService.findAll(SecurityUtils.getUserDTO(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /school-cycles/:id} : get the "id" schoolCycle.
     *
     * @param id the id of the schoolCycle to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the schoolCycle, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/school-cycles/{id}")
    public ResponseEntity<SchoolCycle> getSchoolCycle(@PathVariable Long id) {
        log.debug("REST request to get SchoolCycle : {}", id);
        Optional<SchoolCycle> schoolCycle = schoolCycleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schoolCycle);
    }

    /**
     * {@code DELETE  /school-cycles/:id} : delete the "id" schoolCycle.
     *
     * @param id the id of the schoolCycle to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/school-cycles/{id}")
    public ResponseEntity<Void> deleteSchoolCycle(@PathVariable Long id) {
        log.debug("REST request to delete SchoolCycle : {}", id);
        schoolCycleService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
