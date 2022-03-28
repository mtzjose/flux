package com.magma.flux.web.rest;

import com.magma.flux.repository.FounderPositionsRepository;
import com.magma.flux.service.FounderPositionsService;
import com.magma.flux.service.dto.FounderPositionsDTO;
import com.magma.flux.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.magma.flux.domain.FounderPositions}.
 */
@RestController
@RequestMapping("/api")
public class FounderPositionsResource {

    private final Logger log = LoggerFactory.getLogger(FounderPositionsResource.class);

    private static final String ENTITY_NAME = "founderPositions";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FounderPositionsService founderPositionsService;

    private final FounderPositionsRepository founderPositionsRepository;

    public FounderPositionsResource(
        FounderPositionsService founderPositionsService,
        FounderPositionsRepository founderPositionsRepository
    ) {
        this.founderPositionsService = founderPositionsService;
        this.founderPositionsRepository = founderPositionsRepository;
    }

    /**
     * {@code POST  /founder-positions} : Create a new founderPositions.
     *
     * @param founderPositionsDTO the founderPositionsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new founderPositionsDTO, or with status {@code 400 (Bad Request)} if the founderPositions has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/founder-positions")
    public ResponseEntity<FounderPositionsDTO> createFounderPositions(@Valid @RequestBody FounderPositionsDTO founderPositionsDTO)
        throws URISyntaxException {
        log.debug("REST request to save FounderPositions : {}", founderPositionsDTO);
        if (founderPositionsDTO.getId() != null) {
            throw new BadRequestAlertException("A new founderPositions cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FounderPositionsDTO result = founderPositionsService.save(founderPositionsDTO);
        return ResponseEntity
            .created(new URI("/api/founder-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /founder-positions/:id} : Updates an existing founderPositions.
     *
     * @param id the id of the founderPositionsDTO to save.
     * @param founderPositionsDTO the founderPositionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated founderPositionsDTO,
     * or with status {@code 400 (Bad Request)} if the founderPositionsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the founderPositionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/founder-positions/{id}")
    public ResponseEntity<FounderPositionsDTO> updateFounderPositions(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FounderPositionsDTO founderPositionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FounderPositions : {}, {}", id, founderPositionsDTO);
        if (founderPositionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, founderPositionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!founderPositionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FounderPositionsDTO result = founderPositionsService.save(founderPositionsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, founderPositionsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /founder-positions/:id} : Partial updates given fields of an existing founderPositions, field will ignore if it is null
     *
     * @param id the id of the founderPositionsDTO to save.
     * @param founderPositionsDTO the founderPositionsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated founderPositionsDTO,
     * or with status {@code 400 (Bad Request)} if the founderPositionsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the founderPositionsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the founderPositionsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/founder-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FounderPositionsDTO> partialUpdateFounderPositions(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FounderPositionsDTO founderPositionsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FounderPositions partially : {}, {}", id, founderPositionsDTO);
        if (founderPositionsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, founderPositionsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!founderPositionsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FounderPositionsDTO> result = founderPositionsService.partialUpdate(founderPositionsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, founderPositionsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /founder-positions} : get all the founderPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of founderPositions in body.
     */
    @GetMapping("/founder-positions")
    public List<FounderPositionsDTO> getAllFounderPositions() {
        log.debug("REST request to get all FounderPositions");
        return founderPositionsService.findAll();
    }

    /**
     * {@code GET  /founder-positions/:id} : get the "id" founderPositions.
     *
     * @param id the id of the founderPositionsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the founderPositionsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/founder-positions/{id}")
    public ResponseEntity<FounderPositionsDTO> getFounderPositions(@PathVariable Long id) {
        log.debug("REST request to get FounderPositions : {}", id);
        Optional<FounderPositionsDTO> founderPositionsDTO = founderPositionsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(founderPositionsDTO);
    }

    /**
     * {@code DELETE  /founder-positions/:id} : delete the "id" founderPositions.
     *
     * @param id the id of the founderPositionsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/founder-positions/{id}")
    public ResponseEntity<Void> deleteFounderPositions(@PathVariable Long id) {
        log.debug("REST request to delete FounderPositions : {}", id);
        founderPositionsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
