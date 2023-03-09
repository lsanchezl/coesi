package com.coesi.web.rest;

import com.coesi.domain.TypeAttendance;
import com.coesi.service.TypeAttendanceService;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.coesi.domain.TypeAttendance}.
 */
@RestController
@RequestMapping("/api")
public class TypeAttendanceResource {

    private final Logger log = LoggerFactory.getLogger(TypeAttendanceResource.class);

    private static final String ENTITY_NAME = "typeAttendance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeAttendanceService typeAttendanceService;

    public TypeAttendanceResource(TypeAttendanceService typeAttendanceService) {
        this.typeAttendanceService = typeAttendanceService;
    }

    /**
     * {@code POST  /type-attendances} : Create a new typeAttendance.
     *
     * @param typeAttendance the typeAttendance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeAttendance, or with status {@code 400 (Bad Request)} if the typeAttendance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-attendances")
    public ResponseEntity<TypeAttendance> createTypeAttendance(@Valid @RequestBody TypeAttendance typeAttendance) throws URISyntaxException {
        log.debug("REST request to save TypeAttendance : {}", typeAttendance);
        if (typeAttendance.getId() != null) {
            throw new BadRequestAlertException("A new typeAttendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeAttendance result = typeAttendanceService.save(typeAttendance);
        return ResponseEntity.created(new URI("/api/type-attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-attendances} : Updates an existing typeAttendance.
     *
     * @param typeAttendance the typeAttendance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeAttendance,
     * or with status {@code 400 (Bad Request)} if the typeAttendance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeAttendance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-attendances")
    public ResponseEntity<TypeAttendance> updateTypeAttendance(@Valid @RequestBody TypeAttendance typeAttendance) throws URISyntaxException {
        log.debug("REST request to update TypeAttendance : {}", typeAttendance);
        if (typeAttendance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeAttendance result = typeAttendanceService.save(typeAttendance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeAttendance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-attendances} : get all the typeAttendances.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeAttendances in body.
     */
    @GetMapping("/type-attendances")
    public ResponseEntity<List<TypeAttendance>> getAllTypeAttendances(Pageable pageable) {
        log.debug("REST request to get a page of TypeAttendances");
        Page<TypeAttendance> page = typeAttendanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-attendances/:id} : get the "id" typeAttendance.
     *
     * @param id the id of the typeAttendance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeAttendance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-attendances/{id}")
    public ResponseEntity<TypeAttendance> getTypeAttendance(@PathVariable Long id) {
        log.debug("REST request to get TypeAttendance : {}", id);
        Optional<TypeAttendance> typeAttendance = typeAttendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeAttendance);
    }

    /**
     * {@code DELETE  /type-attendances/:id} : delete the "id" typeAttendance.
     *
     * @param id the id of the typeAttendance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-attendances/{id}")
    public ResponseEntity<Void> deleteTypeAttendance(@PathVariable Long id) {
        log.debug("REST request to delete TypeAttendance : {}", id);
        typeAttendanceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
