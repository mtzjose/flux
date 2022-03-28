package com.magma.flux.web.rest;

import com.magma.flux.repository.OpportunityRepository;
import com.magma.flux.service.OpportunityService;
import com.magma.flux.service.dto.OpportunityDTO;
import com.magma.flux.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.magma.flux.domain.Opportunity}.
 */
@RestController
@RequestMapping("/api")
public class OpportunityResource {

    private final Logger log = LoggerFactory.getLogger(OpportunityResource.class);

    private static final String ENTITY_NAME = "opportunity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OpportunityService opportunityService;

    private final OpportunityRepository opportunityRepository;

    public OpportunityResource(OpportunityService opportunityService, OpportunityRepository opportunityRepository) {
        this.opportunityService = opportunityService;
        this.opportunityRepository = opportunityRepository;
    }

    /**
     * {@code POST  /opportunities} : Create a new opportunity.
     *
     * @param opportunityDTO the opportunityDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new opportunityDTO, or with status {@code 400 (Bad Request)} if the opportunity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/opportunities")
    public ResponseEntity<OpportunityDTO> createOpportunity(@RequestBody OpportunityDTO opportunityDTO) throws URISyntaxException {
        log.debug("REST request to save Opportunity : {}", opportunityDTO);
        if (opportunityDTO.getId() != null) {
            throw new BadRequestAlertException("A new opportunity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OpportunityDTO result = opportunityService.save(opportunityDTO);
        return ResponseEntity
            .created(new URI("/api/opportunities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /opportunities/:id} : Updates an existing opportunity.
     *
     * @param id the id of the opportunityDTO to save.
     * @param opportunityDTO the opportunityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityDTO,
     * or with status {@code 400 (Bad Request)} if the opportunityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the opportunityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/opportunities/{id}")
    public ResponseEntity<OpportunityDTO> updateOpportunity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpportunityDTO opportunityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Opportunity : {}, {}", id, opportunityDTO);
        if (opportunityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OpportunityDTO result = opportunityService.save(opportunityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opportunityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /opportunities/:id} : Partial updates given fields of an existing opportunity, field will ignore if it is null
     *
     * @param id the id of the opportunityDTO to save.
     * @param opportunityDTO the opportunityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated opportunityDTO,
     * or with status {@code 400 (Bad Request)} if the opportunityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the opportunityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the opportunityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/opportunities/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OpportunityDTO> partialUpdateOpportunity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OpportunityDTO opportunityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Opportunity partially : {}, {}", id, opportunityDTO);
        if (opportunityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, opportunityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!opportunityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OpportunityDTO> result = opportunityService.partialUpdate(opportunityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, opportunityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /opportunities} : get all the opportunities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of opportunities in body.
     */
    @GetMapping("/opportunities")
    public List<OpportunityDTO> getAllOpportunities() {
        log.debug("REST request to get all Opportunities");
        return opportunityService.findAll();
    }

    /**
     * {@code GET  /opportunities/:id} : get the "id" opportunity.
     *
     * @param id the id of the opportunityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the opportunityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/opportunities/{id}")
    public ResponseEntity<OpportunityDTO> getOpportunity(@PathVariable Long id) {
        log.debug("REST request to get Opportunity : {}", id);
        Optional<OpportunityDTO> opportunityDTO = opportunityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(opportunityDTO);
    }

    /**
     * {@code DELETE  /opportunities/:id} : delete the "id" opportunity.
     *
     * @param id the id of the opportunityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/opportunities/{id}")
    public ResponseEntity<Void> deleteOpportunity(@PathVariable Long id) {
        log.debug("REST request to delete Opportunity : {}", id);
        opportunityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
