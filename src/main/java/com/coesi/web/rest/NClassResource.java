package com.coesi.web.rest;

import com.coesi.domain.NClass;
import com.coesi.service.NClassService;
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
 * REST controller for managing {@link com.coesi.domain.NClass}.
 */
@RestController
@RequestMapping("/api")
public class NClassResource {

    private final Logger log = LoggerFactory.getLogger(NClassResource.class);

    private static final String ENTITY_NAME = "nClass";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NClassService nClassService;

    public NClassResource(NClassService nClassService) {
        this.nClassService = nClassService;
    }

    /**
     * {@code POST  /n-classes} : Create a new nClass.
     *
     * @param nClass the nClass to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new nClass, or with status {@code 400 (Bad Request)} if the
     * nClass has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/n-classes")
    public ResponseEntity<NClass> createNClass(@Valid @RequestBody NClass nClass) throws URISyntaxException {
        log.debug("REST request to save NClass : {}", nClass);
        if (nClass.getId() != null) {
            throw new BadRequestAlertException("A new nClass cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NClass result = nClassService.save(nClass);
        return ResponseEntity.created(new URI("/api/n-classes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /n-classes} : Updates an existing nClass.
     *
     * @param nClass the nClass to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated nClass, or with status {@code 400 (Bad Request)} if the
     * nClass is not valid, or with status {@code 500 (Internal Server Error)}
     * if the nClass couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/n-classes")
    public ResponseEntity<NClass> updateNClass(@Valid @RequestBody NClass nClass) throws URISyntaxException {
        log.debug("REST request to update NClass : {}", nClass);
        if (nClass.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NClass result = nClassService.save(nClass);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nClass.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /n-classes/career/:careerId} : get all the nClasses for the
     * parameter careerId
     *
     * @param careerId
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of nClasses in body.
     */
    @GetMapping("/n-classes/career")
    public ResponseEntity<List<NClass>> findAllByCareerId(@RequestParam(value = "careerId") String careerId, Pageable pageable) {
        log.debug("REST request to get a page of NClasses for the parameter careerId : {}", careerId);
        Page<NClass> page = nClassService.findAllByCareerId(Long.parseLong(careerId), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/n-classes/list/career")
    public List<NClass> findAllByCareerIdList(@RequestParam(value = "careerId") String careerId) {
        log.debug("REST list request to get a page of NClasses for the parameter careerId : {}", careerId);
        return nClassService.findAllByCareerIdList(Long.parseLong(careerId));
    }

    /**
     * {@code GET  /n-classes/:id} : get the "id" nClass.
     *
     * @param id the id of the nClass to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the nClass, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/n-classes/{id}")
    public ResponseEntity<NClass> getNClass(@PathVariable Long id) {
        log.debug("REST request to get NClass : {}", id);
        Optional<NClass> nClass = nClassService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nClass);
    }

    /**
     * {@code DELETE  /n-classes/:id} : delete the "id" nClass.
     *
     * @param id the id of the nClass to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/n-classes/{id}")
    public ResponseEntity<Void> deleteNClass(@PathVariable Long id) {
        log.debug("REST request to delete NClass : {}", id);
        nClassService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
