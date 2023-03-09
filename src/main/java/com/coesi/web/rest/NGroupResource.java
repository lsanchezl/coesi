package com.coesi.web.rest;

import com.coesi.domain.NGroup;
import com.coesi.security.SecurityUtils;
import com.coesi.service.NGroupService;
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
 * REST controller for managing {@link com.coesi.domain.NGroup}.
 */
@RestController
@RequestMapping("/api")
public class NGroupResource {

    private final Logger log = LoggerFactory.getLogger(NGroupResource.class);

    private static final String ENTITY_NAME = "nGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NGroupService nGroupService;

    public NGroupResource(NGroupService nGroupService) {
        this.nGroupService = nGroupService;
    }

    /**
     * {@code POST  /n-groups} : Create a new nGroup.
     *
     * @param nGroup the nGroup to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and
     * with body the new nGroup, or with status {@code 400 (Bad Request)} if the
     * nGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/n-groups")
    public ResponseEntity<NGroup> createNGroup(@RequestBody NGroup nGroup) throws URISyntaxException {
        log.debug("REST request to save NGroup : {}", nGroup);
        if (nGroup.getId() != null) {
            throw new BadRequestAlertException("A new nGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NGroup result = nGroupService.save(nGroup);
        return ResponseEntity.created(new URI("/api/n-groups/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * {@code PUT  /n-groups} : Updates an existing nGroup.
     *
     * @param nGroup the nGroup to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the updated nGroup, or with status {@code 400 (Bad Request)} if the
     * nGroup is not valid, or with status {@code 500 (Internal Server Error)}
     * if the nGroup couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/n-groups")
    public ResponseEntity<NGroup> updateNGroup(@Valid @RequestBody NGroup nGroup) throws URISyntaxException {
        log.debug("REST request to update NGroup : {}", nGroup);
        if (nGroup.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NGroup result = nGroupService.save(nGroup);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nGroup.getId().toString()))
                .body(result);
    }

    /**
     * {@code GET  /n-groups} : get all the nGroups.
     *
     * @param careerId
     * @param schoolCycleId
     * @param roomId
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the
     * list of nGroups in body.
     */
    @GetMapping("/n-groups/filtered")
    public ResponseEntity<List<NGroup>> findFiltered(
            @RequestParam(value = "careerId") String careerId,
            @RequestParam(value = "schoolCycleId") String schoolCycleId,
            @RequestParam(value = "roomId") String roomId, Pageable pageable) {
        log.debug("REST request to get a page of NGroups");
        Page<NGroup> page = nGroupService.findFiltered(SecurityUtils.getUserDTO(), Long.parseLong(careerId),
                Long.parseLong(schoolCycleId), Long.parseLong(roomId), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /n-groups/:id} : get the "id" nGroup.
     *
     * @param id the id of the nGroup to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with
     * body the nGroup, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/n-groups/{id}")
    public ResponseEntity<NGroup> getNGroup(@PathVariable Long id) {
        log.debug("REST request to get NGroup : {}", id);
        Optional<NGroup> nGroup = nGroupService.findOne(SecurityUtils.getUserDTO(), id);
        return ResponseUtil.wrapOrNotFound(nGroup);
    }

    /**
     * {@code DELETE  /n-groups/:id} : delete the "id" nGroup.
     *
     * @param id the id of the nGroup to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/n-groups/{id}")
    public ResponseEntity<Void> deleteNGroup(@PathVariable Long id) {
        log.debug("REST request to delete NGroup : {}", id);
        nGroupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
