package com.magma.flux.web.rest;

import com.magma.flux.repository.GenderRepository;
import com.magma.flux.service.GenderService;
import com.magma.flux.service.dto.GenderDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.Gender}.
 */
@RestController
@RequestMapping("/api")
public class GenderResource {

    private final Logger log = LoggerFactory.getLogger(GenderResource.class);

    private static final String ENTITY_NAME = "gender";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenderService genderService;

    private final GenderRepository genderRepository;

    public GenderResource(GenderService genderService, GenderRepository genderRepository) {
        this.genderService = genderService;
        this.genderRepository = genderRepository;
    }

    /**
     * {@code POST  /genders} : Create a new gender.
     *
     * @param genderDTO the genderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new genderDTO, or with status {@code 400 (Bad Request)} if the gender has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/genders")
    public ResponseEntity<GenderDTO> createGender(@Valid @RequestBody GenderDTO genderDTO) throws URISyntaxException {
        log.debug("REST request to save Gender : {}", genderDTO);
        if (genderDTO.getId() != null) {
            throw new BadRequestAlertException("A new gender cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenderDTO result = genderService.save(genderDTO);
        return ResponseEntity
            .created(new URI("/api/genders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /genders/:id} : Updates an existing gender.
     *
     * @param id the id of the genderDTO to save.
     * @param genderDTO the genderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genderDTO,
     * or with status {@code 400 (Bad Request)} if the genderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the genderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/genders/{id}")
    public ResponseEntity<GenderDTO> updateGender(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GenderDTO genderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Gender : {}, {}", id, genderDTO);
        if (genderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GenderDTO result = genderService.save(genderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, genderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /genders/:id} : Partial updates given fields of an existing gender, field will ignore if it is null
     *
     * @param id the id of the genderDTO to save.
     * @param genderDTO the genderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated genderDTO,
     * or with status {@code 400 (Bad Request)} if the genderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the genderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the genderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/genders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<GenderDTO> partialUpdateGender(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GenderDTO genderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Gender partially : {}, {}", id, genderDTO);
        if (genderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, genderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!genderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GenderDTO> result = genderService.partialUpdate(genderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, genderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /genders} : get all the genders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of genders in body.
     */
    @GetMapping("/genders")
    public List<GenderDTO> getAllGenders() {
        log.debug("REST request to get all Genders");
        return genderService.findAll();
    }

    /**
     * {@code GET  /genders/:id} : get the "id" gender.
     *
     * @param id the id of the genderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the genderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/genders/{id}")
    public ResponseEntity<GenderDTO> getGender(@PathVariable Long id) {
        log.debug("REST request to get Gender : {}", id);
        Optional<GenderDTO> genderDTO = genderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(genderDTO);
    }

    /**
     * {@code DELETE  /genders/:id} : delete the "id" gender.
     *
     * @param id the id of the genderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/genders/{id}")
    public ResponseEntity<Void> deleteGender(@PathVariable Long id) {
        log.debug("REST request to delete Gender : {}", id);
        genderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
