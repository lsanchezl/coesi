package com.coesi.web.rest;

import com.coesi.domain.StudentEvaluation;
import com.coesi.service.StudentEvaluationService;
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
 * REST controller for managing {@link com.coesi.domain.StudentEvaluation}.
 */
@RestController
@RequestMapping("/api")
public class StudentEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(StudentEvaluationResource.class);

    private static final String ENTITY_NAME = "studentEvaluation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StudentEvaluationService studentEvaluationService;

    public StudentEvaluationResource(StudentEvaluationService studentEvaluationService) {
        this.studentEvaluationService = studentEvaluationService;
    }

    /**
     * {@code POST  /student-evaluations} : Create a new studentEvaluation.
     *
     * @param studentEvaluation the studentEvaluation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new studentEvaluation, or with status
     * {@code 400 (Bad Request)} if the studentEvaluation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/student-evaluations")
    public ResponseEntity<StudentEvaluation> createStudentEvaluation(@Valid @RequestBody StudentEvaluation studentEvaluation) throws URISyntaxException {
        log.debug("REST request to save StudentEvaluation : {}", studentEvaluation);
        if (studentEvaluation.getId() != null) {
            throw new BadRequestAlertException("A new studentEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentEvaluation result = studentEvaluationService.save(studentEvaluation);
        return ResponseEntity.created(new URI("/api/student-evaluations/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /student-evaluations} : Updates a matrix studentEvaluation.
     *
     * @param matrix
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated studentEvaluation, or with status
     * {@code 400 (Bad Request)} if the studentEvaluation is not valid, or with
     * status {@code 500 (Internal Server Error)} if the studentEvaluation
     * couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/student-evaluations")
    public ResponseEntity<Void> updateStudentEvaluation(@RequestBody String[][] matrix) throws URISyntaxException {
        String message = "Actualización correcta de datos";
        try {
            studentEvaluationService.update(matrix);
        } catch (Exception e) {
            message = e.toString();
        }
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, message)).build();
    }

    /**
     * {@code GET  /student-evaluations} : get all the studentEvaluations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of studentEvaluations in body.
     */
    @GetMapping("/student-evaluations")
    public ResponseEntity<List<StudentEvaluation>> getAllStudentEvaluations(Pageable pageable) {
        log.debug("REST request to get a page of StudentEvaluations");
        Page<StudentEvaluation> page = studentEvaluationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * Busca y ordena en una matriz las calificaciones por criterio de
     * evaluación del grupo indicado.
     *
     * @param id
     * @return
     */
    @GetMapping("/student-evaluations/n-group/{id}")
    public ResponseEntity<String[][]> findByGroupMatrix(@PathVariable Long id) {
        log.debug("REST request to get a evaluations Matrix for groupId: {}", id);
        return ResponseUtil.wrapOrNotFound(studentEvaluationService.findByGroupMatrix(id, null));
    }

    /**
     * {@code GET  /student-evaluations/:id} : get the "id" studentEvaluation.
     *
     * @param id the id of the studentEvaluation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the studentEvaluation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/student-evaluations/{id}")
    public ResponseEntity<StudentEvaluation> getStudentEvaluation(@PathVariable Long id) {
        log.debug("REST request to get StudentEvaluation : {}", id);
        Optional<StudentEvaluation> studentEvaluation = studentEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentEvaluation);
    }

    /**
     * {@code DELETE  /student-evaluations/:id} : delete the "id"
     * studentEvaluation.
     *
     * @param id the id of the studentEvaluation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/student-evaluations/{id}")
    public ResponseEntity<Void> deleteStudentEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete StudentEvaluation : {}", id);
        studentEvaluationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
