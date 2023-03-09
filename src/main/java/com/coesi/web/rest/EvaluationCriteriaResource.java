package com.coesi.web.rest;

import com.coesi.domain.EvaluationCriteria;
import com.coesi.service.EvaluationCriteriaService;
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
 * REST controller for managing {@link com.coesi.domain.EvaluationCriteria}.
 */
@RestController
@RequestMapping("/api")
public class EvaluationCriteriaResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationCriteriaResource.class);

    private static final String ENTITY_NAME = "evaluationCriteria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluationCriteriaService evaluationCriteriaService;

    public EvaluationCriteriaResource(EvaluationCriteriaService evaluationCriteriaService) {
        this.evaluationCriteriaService = evaluationCriteriaService;
    }

    /**
     * {@code POST  /evaluation-criteria} : Create a new evaluationCriteria.
     *
     * @param evaluationCriteria the evaluationCriteria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new evaluationCriteria, or with status
     * {@code 400 (Bad Request)} if the evaluationCriteria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluation-criteria")
    public ResponseEntity<EvaluationCriteria> createEvaluationCriteria(@Valid @RequestBody EvaluationCriteria evaluationCriteria) throws URISyntaxException {
        log.debug("REST request to save EvaluationCriteria : {}", evaluationCriteria);
        if (evaluationCriteria.getId() != null) {
            throw new BadRequestAlertException("A new evaluationCriteria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationCriteria result = evaluationCriteriaService.save(evaluationCriteria);
        return ResponseEntity.created(new URI("/api/evaluation-criteria/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /evaluation-criteria} : Updates an existing
     * evaluationCriteria.
     *
     * @param evaluationCriteria the evaluationCriteria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated evaluationCriteria, or with status
     * {@code 400 (Bad Request)} if the evaluationCriteria is not valid, or with
     * status {@code 500 (Internal Server Error)} if the evaluationCriteria
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluation-criteria")
    public ResponseEntity<EvaluationCriteria> updateEvaluationCriteria(@Valid @RequestBody EvaluationCriteria evaluationCriteria) throws URISyntaxException {
        log.debug("REST request to update EvaluationCriteria : {}", evaluationCriteria);
        if (evaluationCriteria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluationCriteria result = evaluationCriteriaService.save(evaluationCriteria);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluationCriteria.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /evaluation-criteria} : get all the evaluationCriteria.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of evaluationCriteria in body.
     */
    @GetMapping("/evaluation-criteria")
    public ResponseEntity<List<EvaluationCriteria>> getAllEvaluationCriteria(Pageable pageable) {
        log.debug("REST request to get a page of EvaluationCriteria");
        Page<EvaluationCriteria> page = evaluationCriteriaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluation-criteria/n-group/:nGroupId} : get all the
     * evaluationCriteria for the parameter nGroupId
     *
     * @param nGroupId
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of evaluationCriteria in body.
     */
    @GetMapping("/evaluation-criteria/n-group")
    public ResponseEntity<List<EvaluationCriteria>> findAllByNGroupId(@RequestParam(value = "nGroupId") String nGroupId, Pageable pageable) {
        log.debug("REST request to get a page of EvaluationCriteria for the parameter nGroupId: {}", nGroupId);
        Page<EvaluationCriteria> page = evaluationCriteriaService.findAllByNGroupId(Long.parseLong(nGroupId), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluation-criteria/:id} : get the "id" evaluationCriteria.
     *
     * @param id the id of the evaluationCriteria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the evaluationCriteria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluation-criteria/{id}")
    public ResponseEntity<EvaluationCriteria> getEvaluationCriteria(@PathVariable Long id) {
        log.debug("REST request to get EvaluationCriteria : {}", id);
        Optional<EvaluationCriteria> evaluationCriteria = evaluationCriteriaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationCriteria);
    }

    /**
     * {@code DELETE  /evaluation-criteria/:id} : delete the "id"
     * evaluationCriteria.
     *
     * @param id the id of the evaluationCriteria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluation-criteria/{id}")
    public ResponseEntity<Void> deleteEvaluationCriteria(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationCriteria : {}", id);
        evaluationCriteriaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
