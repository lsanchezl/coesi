package com.coesi.web.rest;

import com.coesi.domain.Income;
import com.coesi.security.SecurityUtils;
import com.coesi.service.IncomeService;
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
 * REST controller for managing {@link com.coesi.domain.Income}.
 */
@RestController
@RequestMapping("/api")
public class IncomeResource {

    private final Logger log = LoggerFactory.getLogger(IncomeResource.class);

    private static final String ENTITY_NAME = "income";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncomeService incomeService;

    public IncomeResource(IncomeService incomeService) {
        this.incomeService = incomeService;
    }

    /**
     * {@code POST  /incomes} : Create a new income.
     *
     * @param income the income to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new income, or with status {@code 400 (Bad Request)} if the
     * income has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incomes")
    public ResponseEntity<Income> createIncome(@RequestBody Income income) throws URISyntaxException {
        log.debug("REST request to save Income : {}", income);
        if (income.getId() != null) {
            throw new BadRequestAlertException("A new income cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Income result = incomeService.save(SecurityUtils.getUserDTO(), income);
        return ResponseEntity.created(new URI("/api/incomes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /incomes} : Updates an existing income.
     *
     * @param income the income to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated income, or with status {@code 400 (Bad Request)} if the
     * income is not valid, or with status {@code 500 (Internal Server Error)}
     * if the income couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incomes")
    public ResponseEntity<Income> updateIncome(@Valid @RequestBody Income income) throws URISyntaxException {
        log.debug("REST request to update Income : {}", income);
        if (income.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Income result = incomeService.save(SecurityUtils.getUserDTO(), income);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, income.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /incomes} : get all the incomes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of incomes in body.
     */
    @GetMapping("/incomes")
    public List<Income> getAllIncomes() {
        log.debug("REST request to get all Incomes");
        return incomeService.findAll();
    }

    /**
     * {@code GET  /incomes/:id} : get the "id" income.
     *
     * @param id the id of the income to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the income, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incomes/{id}")
    public ResponseEntity<Income> getIncome(@PathVariable Long id) {
        log.debug("REST request to get Income : {}", id);
        Optional<Income> income = incomeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(income);
    }

    /**
     * {@code DELETE  /incomes/:id} : delete the "id" income.
     *
     * @param id the id of the income to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incomes/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        log.debug("REST request to delete Income : {}", id);
        incomeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
