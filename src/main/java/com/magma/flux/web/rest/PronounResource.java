package com.magma.flux.web.rest;

import com.magma.flux.repository.PronounRepository;
import com.magma.flux.service.PronounService;
import com.magma.flux.service.dto.PronounDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.Pronoun}.
 */
@RestController
@RequestMapping("/api")
public class PronounResource {

    private final Logger log = LoggerFactory.getLogger(PronounResource.class);

    private static final String ENTITY_NAME = "pronoun";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PronounService pronounService;

    private final PronounRepository pronounRepository;

    public PronounResource(PronounService pronounService, PronounRepository pronounRepository) {
        this.pronounService = pronounService;
        this.pronounRepository = pronounRepository;
    }

    /**
     * {@code POST  /pronouns} : Create a new pronoun.
     *
     * @param pronounDTO the pronounDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pronounDTO, or with status {@code 400 (Bad Request)} if the pronoun has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pronouns")
    public ResponseEntity<PronounDTO> createPronoun(@Valid @RequestBody PronounDTO pronounDTO) throws URISyntaxException {
        log.debug("REST request to save Pronoun : {}", pronounDTO);
        if (pronounDTO.getId() != null) {
            throw new BadRequestAlertException("A new pronoun cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PronounDTO result = pronounService.save(pronounDTO);
        return ResponseEntity
            .created(new URI("/api/pronouns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pronouns/:id} : Updates an existing pronoun.
     *
     * @param id the id of the pronounDTO to save.
     * @param pronounDTO the pronounDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pronounDTO,
     * or with status {@code 400 (Bad Request)} if the pronounDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pronounDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pronouns/{id}")
    public ResponseEntity<PronounDTO> updatePronoun(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PronounDTO pronounDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pronoun : {}, {}", id, pronounDTO);
        if (pronounDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pronounDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pronounRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PronounDTO result = pronounService.save(pronounDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pronounDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pronouns/:id} : Partial updates given fields of an existing pronoun, field will ignore if it is null
     *
     * @param id the id of the pronounDTO to save.
     * @param pronounDTO the pronounDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pronounDTO,
     * or with status {@code 400 (Bad Request)} if the pronounDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pronounDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pronounDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pronouns/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PronounDTO> partialUpdatePronoun(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PronounDTO pronounDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pronoun partially : {}, {}", id, pronounDTO);
        if (pronounDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pronounDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pronounRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PronounDTO> result = pronounService.partialUpdate(pronounDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pronounDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pronouns} : get all the pronouns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pronouns in body.
     */
    @GetMapping("/pronouns")
    public List<PronounDTO> getAllPronouns() {
        log.debug("REST request to get all Pronouns");
        return pronounService.findAll();
    }

    /**
     * {@code GET  /pronouns/:id} : get the "id" pronoun.
     *
     * @param id the id of the pronounDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pronounDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pronouns/{id}")
    public ResponseEntity<PronounDTO> getPronoun(@PathVariable Long id) {
        log.debug("REST request to get Pronoun : {}", id);
        Optional<PronounDTO> pronounDTO = pronounService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pronounDTO);
    }

    /**
     * {@code DELETE  /pronouns/:id} : delete the "id" pronoun.
     *
     * @param id the id of the pronounDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pronouns/{id}")
    public ResponseEntity<Void> deletePronoun(@PathVariable Long id) {
        log.debug("REST request to delete Pronoun : {}", id);
        pronounService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
