package com.coesi.web.rest;

import com.coesi.domain.ConceptIncome;
import com.coesi.service.ConceptIncomeService;
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
 * REST controller for managing {@link com.coesi.domain.ConceptIncome}.
 */
@RestController
@RequestMapping("/api")
public class ConceptIncomeResource {

    private final Logger log = LoggerFactory.getLogger(ConceptIncomeResource.class);

    private static final String ENTITY_NAME = "conceptIncome";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConceptIncomeService conceptIncomeService;

    public ConceptIncomeResource(ConceptIncomeService conceptIncomeService) {
        this.conceptIncomeService = conceptIncomeService;
    }

    /**
     * {@code POST  /concept-incomes} : Create a new conceptIncome.
     *
     * @param conceptIncome the conceptIncome to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conceptIncome, or with status {@code 400 (Bad Request)} if the conceptIncome has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/concept-incomes")
    public ResponseEntity<ConceptIncome> createConceptIncome(@Valid @RequestBody ConceptIncome conceptIncome) throws URISyntaxException {
        log.debug("REST request to save ConceptIncome : {}", conceptIncome);
        if (conceptIncome.getId() != null) {
            throw new BadRequestAlertException("A new conceptIncome cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConceptIncome result = conceptIncomeService.save(conceptIncome);
        return ResponseEntity.created(new URI("/api/concept-incomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /concept-incomes} : Updates an existing conceptIncome.
     *
     * @param conceptIncome the conceptIncome to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conceptIncome,
     * or with status {@code 400 (Bad Request)} if the conceptIncome is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conceptIncome couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/concept-incomes")
    public ResponseEntity<ConceptIncome> updateConceptIncome(@Valid @RequestBody ConceptIncome conceptIncome) throws URISyntaxException {
        log.debug("REST request to update ConceptIncome : {}", conceptIncome);
        if (conceptIncome.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConceptIncome result = conceptIncomeService.save(conceptIncome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conceptIncome.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /concept-incomes} : get all the conceptIncomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conceptIncomes in body.
     */
    @GetMapping("/concept-incomes")
    public List<ConceptIncome> getAllConceptIncomes() {
        log.debug("REST request to get all ConceptIncomes");
        return conceptIncomeService.findAll();
    }

    /**
     * {@code GET  /concept-incomes/:id} : get the "id" conceptIncome.
     *
     * @param id the id of the conceptIncome to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conceptIncome, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/concept-incomes/{id}")
    public ResponseEntity<ConceptIncome> getConceptIncome(@PathVariable Long id) {
        log.debug("REST request to get ConceptIncome : {}", id);
        Optional<ConceptIncome> conceptIncome = conceptIncomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conceptIncome);
    }

    /**
     * {@code DELETE  /concept-incomes/:id} : delete the "id" conceptIncome.
     *
     * @param id the id of the conceptIncome to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/concept-incomes/{id}")
    public ResponseEntity<Void> deleteConceptIncome(@PathVariable Long id) {
        log.debug("REST request to delete ConceptIncome : {}", id);
        conceptIncomeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
