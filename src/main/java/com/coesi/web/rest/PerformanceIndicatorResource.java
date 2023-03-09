package com.coesi.web.rest;

import com.coesi.domain.PerformanceIndicator;
import com.coesi.security.SecurityUtils;
import com.coesi.service.PerformanceIndicatorService;
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
 * REST controller for managing {@link com.coesi.domain.PerformanceIndicator}.
 */
@RestController
@RequestMapping("/api")
public class PerformanceIndicatorResource {

    private final Logger log = LoggerFactory.getLogger(PerformanceIndicatorResource.class);

    private static final String ENTITY_NAME = "performanceIndicator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PerformanceIndicatorService performanceIndicatorService;

    public PerformanceIndicatorResource(PerformanceIndicatorService performanceIndicatorService) {
        this.performanceIndicatorService = performanceIndicatorService;
    }

    /**
     * {@code POST  /performance-indicators} : Create a new performanceIndicator.
     *
     * @param performanceIndicator the performanceIndicator to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new performanceIndicator, or with status
     * {@code 400 (Bad Request)} if the performanceIndicator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/performance-indicators")
    public ResponseEntity<PerformanceIndicator> createPerformanceIndicator(@Valid @RequestBody PerformanceIndicator performanceIndicator) throws URISyntaxException {
        log.debug("REST request to save PerformanceIndicator : {}", performanceIndicator);
        if (performanceIndicator.getId() != null) {
            throw new BadRequestAlertException("A new performanceIndicator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PerformanceIndicator result = performanceIndicatorService.save(performanceIndicator);
        return ResponseEntity.created(new URI("/api/performance-indicators/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /performance-indicators} : Updates an existing
     * performanceIndicator.
     *
     * @param performanceIndicator the performanceIndicator to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated performanceIndicator, or with status
     * {@code 400 (Bad Request)} if the performanceIndicator is not valid, or
     * with status {@code 500 (Internal Server Error)} if the
     * performanceIndicator couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/performance-indicators")
    public ResponseEntity<PerformanceIndicator> updatePerformanceIndicator(@Valid @RequestBody PerformanceIndicator performanceIndicator) throws URISyntaxException {
        log.debug("REST request to update PerformanceIndicator : {}", performanceIndicator);
        if (performanceIndicator.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PerformanceIndicator result = performanceIndicatorService.save(performanceIndicator);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, performanceIndicator.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /performance-indicators} : get all the performanceIndicators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of performanceIndicators in body.
     */
    @GetMapping("/performance-indicators")
    public ResponseEntity<List<PerformanceIndicator>> getAllPerformanceIndicators(Pageable pageable) {
        log.debug("REST request to get a page of PerformanceIndicators");
        Page<PerformanceIndicator> page = performanceIndicatorService.findAll(SecurityUtils.getUserDTO(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /performance-indicators/:id} : get the "id"
     * performanceIndicator.
     *
     * @param id the id of the performanceIndicator to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the performanceIndicator, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/performance-indicators/{id}")
    public ResponseEntity<PerformanceIndicator> getPerformanceIndicator(@PathVariable Long id) {
        log.debug("REST request to get PerformanceIndicator : {}", id);
        Optional<PerformanceIndicator> performanceIndicator = performanceIndicatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(performanceIndicator);
    }

    /**
     * {@code DELETE  /performance-indicators/:id} : delete the "id"
     * performanceIndicator.
     *
     * @param id the id of the performanceIndicator to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/performance-indicators/{id}")
    public ResponseEntity<Void> deletePerformanceIndicator(@PathVariable Long id) {
        log.debug("REST request to delete PerformanceIndicator : {}", id);
        performanceIndicatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/performance-indicators/list")
    public List<PerformanceIndicator> getAllPerformanceIndicatorsList() {
        return performanceIndicatorService.getAllPerformanceIndicatorsList();
    }
}
