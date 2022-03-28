package com.magma.flux.web.rest;

import com.magma.flux.repository.CollegeDegreeRepository;
import com.magma.flux.service.CollegeDegreeService;
import com.magma.flux.service.dto.CollegeDegreeDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.CollegeDegree}.
 */
@RestController
@RequestMapping("/api")
public class CollegeDegreeResource {

    private final Logger log = LoggerFactory.getLogger(CollegeDegreeResource.class);

    private static final String ENTITY_NAME = "collegeDegree";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollegeDegreeService collegeDegreeService;

    private final CollegeDegreeRepository collegeDegreeRepository;

    public CollegeDegreeResource(CollegeDegreeService collegeDegreeService, CollegeDegreeRepository collegeDegreeRepository) {
        this.collegeDegreeService = collegeDegreeService;
        this.collegeDegreeRepository = collegeDegreeRepository;
    }

    /**
     * {@code POST  /college-degrees} : Create a new collegeDegree.
     *
     * @param collegeDegreeDTO the collegeDegreeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collegeDegreeDTO, or with status {@code 400 (Bad Request)} if the collegeDegree has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/college-degrees")
    public ResponseEntity<CollegeDegreeDTO> createCollegeDegree(@Valid @RequestBody CollegeDegreeDTO collegeDegreeDTO)
        throws URISyntaxException {
        log.debug("REST request to save CollegeDegree : {}", collegeDegreeDTO);
        if (collegeDegreeDTO.getId() != null) {
            throw new BadRequestAlertException("A new collegeDegree cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollegeDegreeDTO result = collegeDegreeService.save(collegeDegreeDTO);
        return ResponseEntity
            .created(new URI("/api/college-degrees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /college-degrees/:id} : Updates an existing collegeDegree.
     *
     * @param id the id of the collegeDegreeDTO to save.
     * @param collegeDegreeDTO the collegeDegreeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegeDegreeDTO,
     * or with status {@code 400 (Bad Request)} if the collegeDegreeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collegeDegreeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/college-degrees/{id}")
    public ResponseEntity<CollegeDegreeDTO> updateCollegeDegree(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CollegeDegreeDTO collegeDegreeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CollegeDegree : {}, {}", id, collegeDegreeDTO);
        if (collegeDegreeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collegeDegreeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegeDegreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollegeDegreeDTO result = collegeDegreeService.save(collegeDegreeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collegeDegreeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /college-degrees/:id} : Partial updates given fields of an existing collegeDegree, field will ignore if it is null
     *
     * @param id the id of the collegeDegreeDTO to save.
     * @param collegeDegreeDTO the collegeDegreeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collegeDegreeDTO,
     * or with status {@code 400 (Bad Request)} if the collegeDegreeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the collegeDegreeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the collegeDegreeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/college-degrees/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CollegeDegreeDTO> partialUpdateCollegeDegree(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CollegeDegreeDTO collegeDegreeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CollegeDegree partially : {}, {}", id, collegeDegreeDTO);
        if (collegeDegreeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collegeDegreeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collegeDegreeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollegeDegreeDTO> result = collegeDegreeService.partialUpdate(collegeDegreeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collegeDegreeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /college-degrees} : get all the collegeDegrees.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collegeDegrees in body.
     */
    @GetMapping("/college-degrees")
    public List<CollegeDegreeDTO> getAllCollegeDegrees() {
        log.debug("REST request to get all CollegeDegrees");
        return collegeDegreeService.findAll();
    }

    /**
     * {@code GET  /college-degrees/:id} : get the "id" collegeDegree.
     *
     * @param id the id of the collegeDegreeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collegeDegreeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/college-degrees/{id}")
    public ResponseEntity<CollegeDegreeDTO> getCollegeDegree(@PathVariable Long id) {
        log.debug("REST request to get CollegeDegree : {}", id);
        Optional<CollegeDegreeDTO> collegeDegreeDTO = collegeDegreeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collegeDegreeDTO);
    }

    /**
     * {@code DELETE  /college-degrees/:id} : delete the "id" collegeDegree.
     *
     * @param id the id of the collegeDegreeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/college-degrees/{id}")
    public ResponseEntity<Void> deleteCollegeDegree(@PathVariable Long id) {
        log.debug("REST request to delete CollegeDegree : {}", id);
        collegeDegreeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
