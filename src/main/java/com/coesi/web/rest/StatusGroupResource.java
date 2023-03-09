package com.coesi.web.rest;

import com.coesi.domain.StatusGroup;
import com.coesi.service.StatusGroupService;
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

/**
 * REST controller for managing {@link com.coesi.domain.StatusGroup}.
 */
@RestController
@RequestMapping("/api")
public class StatusGroupResource {

    private final Logger log = LoggerFactory.getLogger(StatusGroupResource.class);

    private static final String ENTITY_NAME = "statusGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatusGroupService statusGroupService;

    public StatusGroupResource(StatusGroupService statusGroupService) {
        this.statusGroupService = statusGroupService;
    }

    /**
     * {@code POST  /status-groups} : Create a new statusGroup.
     *
     * @param statusGroup the statusGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new statusGroup, or with status {@code 400 (Bad Request)}
     * if the statusGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/status-groups")
    public ResponseEntity<StatusGroup> createStatusGroup(@Valid @RequestBody StatusGroup statusGroup) throws URISyntaxException {
        log.debug("REST request to save StatusGroup : {}", statusGroup);
        if (statusGroup.getId() != null) {
            throw new BadRequestAlertException("A new statusGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatusGroup result = statusGroupService.save(statusGroup);
        return ResponseEntity.created(new URI("/api/status-groups/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /status-groups} : Updates an existing statusGroup.
     *
     * @param statusGroup the statusGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated statusGroup, or with status {@code 400 (Bad Request)} if
     * the statusGroup is not valid, or with status
     * {@code 500 (Internal Server Error)} if the statusGroup couldn't be
     * updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/status-groups")
    public ResponseEntity<StatusGroup> updateStatusGroup(@Valid @RequestBody StatusGroup statusGroup) throws URISyntaxException {
        log.debug("REST request to update StatusGroup : {}", statusGroup);
        if (statusGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StatusGroup result = statusGroupService.save(statusGroup);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, statusGroup.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /status-groups} : get all the statusGroups.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of statusGroups in body.
     */
    @GetMapping("/status-groups")
    public List<StatusGroup> getAllStatusGroups() {
        log.debug("REST request to get all StatusGroups");
        return statusGroupService.findAll();
    }

    /**
     * {@code GET  /status-groups/:id} : get the "id" statusGroup.
     *
     * @param id the id of the statusGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the statusGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/status-groups/{id}")
    public ResponseEntity<StatusGroup> getStatusGroup(@PathVariable Long id) {
        log.debug("REST request to get StatusGroup : {}", id);
        Optional<StatusGroup> statusGroup = statusGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statusGroup);
    }

    /**
     * {@code DELETE  /status-groups/:id} : delete the "id" statusGroup.
     *
     * @param id the id of the statusGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/status-groups/{id}")
    public ResponseEntity<Void> deleteStatusGroup(@PathVariable Long id) {
        log.debug("REST request to delete StatusGroup : {}", id);
        statusGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
