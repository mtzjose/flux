package com.magma.flux.web.rest;

import com.magma.flux.repository.CompanyCategoryRepository;
import com.magma.flux.service.CompanyCategoryService;
import com.magma.flux.service.dto.CompanyCategoryDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.CompanyCategory}.
 */
@RestController
@RequestMapping("/api")
public class CompanyCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CompanyCategoryResource.class);

    private static final String ENTITY_NAME = "companyCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyCategoryService companyCategoryService;

    private final CompanyCategoryRepository companyCategoryRepository;

    public CompanyCategoryResource(CompanyCategoryService companyCategoryService, CompanyCategoryRepository companyCategoryRepository) {
        this.companyCategoryService = companyCategoryService;
        this.companyCategoryRepository = companyCategoryRepository;
    }

    /**
     * {@code POST  /company-categories} : Create a new companyCategory.
     *
     * @param companyCategoryDTO the companyCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyCategoryDTO, or with status {@code 400 (Bad Request)} if the companyCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-categories")
    public ResponseEntity<CompanyCategoryDTO> createCompanyCategory(@Valid @RequestBody CompanyCategoryDTO companyCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyCategory : {}", companyCategoryDTO);
        if (companyCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyCategoryDTO result = companyCategoryService.save(companyCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/company-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-categories/:id} : Updates an existing companyCategory.
     *
     * @param id the id of the companyCategoryDTO to save.
     * @param companyCategoryDTO the companyCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the companyCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-categories/{id}")
    public ResponseEntity<CompanyCategoryDTO> updateCompanyCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CompanyCategoryDTO companyCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyCategory : {}, {}", id, companyCategoryDTO);
        if (companyCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyCategoryDTO result = companyCategoryService.save(companyCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-categories/:id} : Partial updates given fields of an existing companyCategory, field will ignore if it is null
     *
     * @param id the id of the companyCategoryDTO to save.
     * @param companyCategoryDTO the companyCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the companyCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-categories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyCategoryDTO> partialUpdateCompanyCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CompanyCategoryDTO companyCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyCategory partially : {}, {}", id, companyCategoryDTO);
        if (companyCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyCategoryDTO> result = companyCategoryService.partialUpdate(companyCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, companyCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-categories} : get all the companyCategories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyCategories in body.
     */
    @GetMapping("/company-categories")
    public List<CompanyCategoryDTO> getAllCompanyCategories() {
        log.debug("REST request to get all CompanyCategories");
        return companyCategoryService.findAll();
    }

    /**
     * {@code GET  /company-categories/:id} : get the "id" companyCategory.
     *
     * @param id the id of the companyCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-categories/{id}")
    public ResponseEntity<CompanyCategoryDTO> getCompanyCategory(@PathVariable Long id) {
        log.debug("REST request to get CompanyCategory : {}", id);
        Optional<CompanyCategoryDTO> companyCategoryDTO = companyCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyCategoryDTO);
    }

    /**
     * {@code DELETE  /company-categories/:id} : delete the "id" companyCategory.
     *
     * @param id the id of the companyCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-categories/{id}")
    public ResponseEntity<Void> deleteCompanyCategory(@PathVariable Long id) {
        log.debug("REST request to delete CompanyCategory : {}", id);
        companyCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
