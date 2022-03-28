package com.magma.flux.web.rest;

import com.magma.flux.repository.CompanyCategoriesRepository;
import com.magma.flux.service.CompanyCategoriesService;
import com.magma.flux.service.dto.CompanyCategoriesDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.CompanyCategories}.
 */
@RestController
@RequestMapping("/api")
public class CompanyCategoriesResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCategoriesResource.class);

    private static final String ENTITY_NAME = "companyCategories";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyCategoriesService companyCategoriesService;

    private final CompanyCategoriesRepository companyCategoriesRepository;

    public CompanyCategoriesResource(
        CompanyCategoriesService companyCategoriesService,
        CompanyCategoriesRepository companyCategoriesRepository
    ) {
        this.companyCategoriesService = companyCategoriesService;
        this.companyCategoriesRepository = companyCategoriesRepository;
    }

    /**
     * {@code POST  /company-categories} : Create a new companyCategories.
     *
     * @param companyCategoriesDTO the companyCategoriesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyCategoriesDTO, or with status {@code 400 (Bad Request)} if the companyCategories has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-categories")
    public ResponseEntity<CompanyCategoriesDTO> createCompanyCategories(@Valid @RequestBody CompanyCategoriesDTO companyCategoriesDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyCategories : {}", companyCategoriesDTO);
        if (companyCategoriesDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyCategories cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyCategoriesDTO result = companyCategoriesService.save(companyCategoriesDTO);
        return ResponseEntity
            .created(new URI("/api/company-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-categories/:id} : Updates an existing companyCategories.
     *
     * @param id the id of the companyCategoriesDTO to save.
     * @param companyCategoriesDTO the companyCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the companyCategoriesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-categories/{id}")
    public ResponseEntity<CompanyCategoriesDTO> updateCompanyCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompanyCategoriesDTO companyCategoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyCategories : {}, {}", id, companyCategoriesDTO);
        if (companyCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyCategoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyCategoriesDTO result = companyCategoriesService.save(companyCategoriesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyCategoriesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-categories/:id} : Partial updates given fields of an existing companyCategories, field will ignore if it is null
     *
     * @param id the id of the companyCategoriesDTO to save.
     * @param companyCategoriesDTO the companyCategoriesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCategoriesDTO,
     * or with status {@code 400 (Bad Request)} if the companyCategoriesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyCategoriesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyCategoriesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyCategoriesDTO> partialUpdateCompanyCategories(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompanyCategoriesDTO companyCategoriesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyCategories partially : {}, {}", id, companyCategoriesDTO);
        if (companyCategoriesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyCategoriesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyCategoriesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyCategoriesDTO> result = companyCategoriesService.partialUpdate(companyCategoriesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyCategoriesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-categories} : get all the companyCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyCategories in body.
     */
    @GetMapping("/company-categories")
    public List<CompanyCategoriesDTO> getAllCompanyCategories() {
        log.debug("REST request to get all CompanyCategories");
        return companyCategoriesService.findAll();
    }

    /**
     * {@code GET  /company-categories/:id} : get the "id" companyCategories.
     *
     * @param id the id of the companyCategoriesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyCategoriesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-categories/{id}")
    public ResponseEntity<CompanyCategoriesDTO> getCompanyCategories(@PathVariable Long id) {
        log.debug("REST request to get CompanyCategories : {}", id);
        Optional<CompanyCategoriesDTO> companyCategoriesDTO = companyCategoriesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyCategoriesDTO);
    }

    /**
     * {@code DELETE  /company-categories/:id} : delete the "id" companyCategories.
     *
     * @param id the id of the companyCategoriesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-categories/{id}")
    public ResponseEntity<Void> deleteCompanyCategories(@PathVariable Long id) {
        log.debug("REST request to delete CompanyCategories : {}", id);
        companyCategoriesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
