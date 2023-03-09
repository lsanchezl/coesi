package com.coesi.web.rest;

import com.coesi.domain.Career;
import com.coesi.security.SecurityUtils;
import com.coesi.service.CareerService;
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
 * REST controller for managing {@link com.coesi.domain.Career}.
 */
@RestController
@RequestMapping("/api")
public class CareerResource {

    private final Logger log = LoggerFactory.getLogger(CareerResource.class);

    private static final String ENTITY_NAME = "career";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CareerService careerService;

    public CareerResource(CareerService careerService) {
        this.careerService = careerService;
    }

    /**
     * {@code POST  /careers} : Create a new career.
     *
     * @param career the career to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new career, or with status {@code 400 (Bad Request)} if the
     * career has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/careers")
    public ResponseEntity<Career> createCareer(@Valid @RequestBody Career career) throws URISyntaxException {
        log.debug("REST request to save Career : {}", career);
        if (career.getId() != null) {
            throw new BadRequestAlertException("A new career cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Career result = careerService.save(career);
        return ResponseEntity.created(new URI("/api/careers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /careers} : Updates an existing career.
     *
     * @param career the career to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated career, or with status {@code 400 (Bad Request)} if the
     * career is not valid, or with status {@code 500 (Internal Server Error)}
     * if the career couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/careers")
    public ResponseEntity<Career> updateCareer(@Valid @RequestBody Career career) throws URISyntaxException {
        log.debug("REST request to update Career : {}", career);
        if (career.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Career result = careerService.save(career);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, career.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /careers} : get all the careers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of careers in body.
     */
    @GetMapping("/careers")
    public ResponseEntity<List<Career>> getAllCareers(Pageable pageable) {
        log.debug("REST request to get a page of Careers");
        Page<Career> page = careerService.findAll(SecurityUtils.getUserDTO(), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /careers/:id} : get the "id" career.
     *
     * @param id the id of the career to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the career, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/careers/{id}")
    public ResponseEntity<Career> getCareer(@PathVariable Long id) {
        log.debug("REST request to get Career : {}", id);
        Optional<Career> career = careerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(career);
    }

    /**
     * {@code DELETE  /careers/:id} : delete the "id" career.
     *
     * @param id the id of the career to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/careers/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        log.debug("REST request to delete Career : {}", id);
        careerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
