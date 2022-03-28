package com.magma.flux.web.rest;

import com.magma.flux.repository.PersonCompanyHistoryRepository;
import com.magma.flux.service.PersonCompanyHistoryService;
import com.magma.flux.service.dto.PersonCompanyHistoryDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.PersonCompanyHistory}.
 */
@RestController
@RequestMapping("/api")
public class PersonCompanyHistoryResource {

    private final Logger log = LoggerFactory.getLogger(PersonCompanyHistoryResource.class);

    private static final String ENTITY_NAME = "personCompanyHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonCompanyHistoryService personCompanyHistoryService;

    private final PersonCompanyHistoryRepository personCompanyHistoryRepository;

    public PersonCompanyHistoryResource(
        PersonCompanyHistoryService personCompanyHistoryService,
        PersonCompanyHistoryRepository personCompanyHistoryRepository
    ) {
        this.personCompanyHistoryService = personCompanyHistoryService;
        this.personCompanyHistoryRepository = personCompanyHistoryRepository;
    }

    /**
     * {@code POST  /person-company-histories} : Create a new personCompanyHistory.
     *
     * @param personCompanyHistoryDTO the personCompanyHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personCompanyHistoryDTO, or with status {@code 400 (Bad Request)} if the personCompanyHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/person-company-histories")
    public ResponseEntity<PersonCompanyHistoryDTO> createPersonCompanyHistory(
        @Valid @RequestBody PersonCompanyHistoryDTO personCompanyHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to save PersonCompanyHistory : {}", personCompanyHistoryDTO);
        if (personCompanyHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new personCompanyHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonCompanyHistoryDTO result = personCompanyHistoryService.save(personCompanyHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/person-company-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /person-company-histories/:id} : Updates an existing personCompanyHistory.
     *
     * @param id the id of the personCompanyHistoryDTO to save.
     * @param personCompanyHistoryDTO the personCompanyHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personCompanyHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the personCompanyHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personCompanyHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/person-company-histories/{id}")
    public ResponseEntity<PersonCompanyHistoryDTO> updatePersonCompanyHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonCompanyHistoryDTO personCompanyHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonCompanyHistory : {}, {}", id, personCompanyHistoryDTO);
        if (personCompanyHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personCompanyHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personCompanyHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonCompanyHistoryDTO result = personCompanyHistoryService.save(personCompanyHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personCompanyHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /person-company-histories/:id} : Partial updates given fields of an existing personCompanyHistory, field will ignore if it is null
     *
     * @param id the id of the personCompanyHistoryDTO to save.
     * @param personCompanyHistoryDTO the personCompanyHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personCompanyHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the personCompanyHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personCompanyHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personCompanyHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/person-company-histories/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonCompanyHistoryDTO> partialUpdatePersonCompanyHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonCompanyHistoryDTO personCompanyHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonCompanyHistory partially : {}, {}", id, personCompanyHistoryDTO);
        if (personCompanyHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personCompanyHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personCompanyHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonCompanyHistoryDTO> result = personCompanyHistoryService.partialUpdate(personCompanyHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personCompanyHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /person-company-histories} : get all the personCompanyHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personCompanyHistories in body.
     */
    @GetMapping("/person-company-histories")
    public List<PersonCompanyHistoryDTO> getAllPersonCompanyHistories() {
        log.debug("REST request to get all PersonCompanyHistories");
        return personCompanyHistoryService.findAll();
    }

    /**
     * {@code GET  /person-company-histories/:id} : get the "id" personCompanyHistory.
     *
     * @param id the id of the personCompanyHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personCompanyHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/person-company-histories/{id}")
    public ResponseEntity<PersonCompanyHistoryDTO> getPersonCompanyHistory(@PathVariable Long id) {
        log.debug("REST request to get PersonCompanyHistory : {}", id);
        Optional<PersonCompanyHistoryDTO> personCompanyHistoryDTO = personCompanyHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personCompanyHistoryDTO);
    }

    /**
     * {@code DELETE  /person-company-histories/:id} : delete the "id" personCompanyHistory.
     *
     * @param id the id of the personCompanyHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/person-company-histories/{id}")
    public ResponseEntity<Void> deletePersonCompanyHistory(@PathVariable Long id) {
        log.debug("REST request to delete PersonCompanyHistory : {}", id);
        personCompanyHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
