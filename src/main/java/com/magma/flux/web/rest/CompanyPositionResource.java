package com.magma.flux.web.rest;

import com.magma.flux.repository.CompanyPositionRepository;
import com.magma.flux.service.CompanyPositionService;
import com.magma.flux.service.dto.CompanyPositionDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.CompanyPosition}.
 */
@RestController
@RequestMapping("/api")
public class CompanyPositionResource {

    private final Logger log = LoggerFactory.getLogger(CompanyPositionResource.class);

    private static final String ENTITY_NAME = "companyPosition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyPositionService companyPositionService;

    private final CompanyPositionRepository companyPositionRepository;

    public CompanyPositionResource(CompanyPositionService companyPositionService, CompanyPositionRepository companyPositionRepository) {
        this.companyPositionService = companyPositionService;
        this.companyPositionRepository = companyPositionRepository;
    }

    /**
     * {@code POST  /company-positions} : Create a new companyPosition.
     *
     * @param companyPositionDTO the companyPositionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyPositionDTO, or with status {@code 400 (Bad Request)} if the companyPosition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-positions")
    public ResponseEntity<CompanyPositionDTO> createCompanyPosition(@Valid @RequestBody CompanyPositionDTO companyPositionDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyPosition : {}", companyPositionDTO);
        if (companyPositionDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyPosition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyPositionDTO result = companyPositionService.save(companyPositionDTO);
        return ResponseEntity
            .created(new URI("/api/company-positions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-positions/:id} : Updates an existing companyPosition.
     *
     * @param id the id of the companyPositionDTO to save.
     * @param companyPositionDTO the companyPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyPositionDTO,
     * or with status {@code 400 (Bad Request)} if the companyPositionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-positions/{id}")
    public ResponseEntity<CompanyPositionDTO> updateCompanyPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompanyPositionDTO companyPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyPosition : {}, {}", id, companyPositionDTO);
        if (companyPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyPositionDTO result = companyPositionService.save(companyPositionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyPositionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-positions/:id} : Partial updates given fields of an existing companyPosition, field will ignore if it is null
     *
     * @param id the id of the companyPositionDTO to save.
     * @param companyPositionDTO the companyPositionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyPositionDTO,
     * or with status {@code 400 (Bad Request)} if the companyPositionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyPositionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyPositionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-positions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyPositionDTO> partialUpdateCompanyPosition(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompanyPositionDTO companyPositionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyPosition partially : {}, {}", id, companyPositionDTO);
        if (companyPositionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyPositionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyPositionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyPositionDTO> result = companyPositionService.partialUpdate(companyPositionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyPositionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-positions} : get all the companyPositions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyPositions in body.
     */
    @GetMapping("/company-positions")
    public List<CompanyPositionDTO> getAllCompanyPositions() {
        log.debug("REST request to get all CompanyPositions");
        return companyPositionService.findAll();
    }

    /**
     * {@code GET  /company-positions/:id} : get the "id" companyPosition.
     *
     * @param id the id of the companyPositionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyPositionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-positions/{id}")
    public ResponseEntity<CompanyPositionDTO> getCompanyPosition(@PathVariable Long id) {
        log.debug("REST request to get CompanyPosition : {}", id);
        Optional<CompanyPositionDTO> companyPositionDTO = companyPositionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyPositionDTO);
    }

    /**
     * {@code DELETE  /company-positions/:id} : delete the "id" companyPosition.
     *
     * @param id the id of the companyPositionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-positions/{id}")
    public ResponseEntity<Void> deleteCompanyPosition(@PathVariable Long id) {
        log.debug("REST request to delete CompanyPosition : {}", id);
        companyPositionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
