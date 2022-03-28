package com.magma.flux.web.rest;

import com.magma.flux.repository.RaceRepository;
import com.magma.flux.service.RaceService;
import com.magma.flux.service.dto.RaceDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.Race}.
 */
@RestController
@RequestMapping("/api")
public class RaceResource {

    private final Logger log = LoggerFactory.getLogger(RaceResource.class);

    private static final String ENTITY_NAME = "race";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RaceService raceService;

    private final RaceRepository raceRepository;

    public RaceResource(RaceService raceService, RaceRepository raceRepository) {
        this.raceService = raceService;
        this.raceRepository = raceRepository;
    }

    /**
     * {@code POST  /races} : Create a new race.
     *
     * @param raceDTO the raceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new raceDTO, or with status {@code 400 (Bad Request)} if the race has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/races")
    public ResponseEntity<RaceDTO> createRace(@Valid @RequestBody RaceDTO raceDTO) throws URISyntaxException {
        log.debug("REST request to save Race : {}", raceDTO);
        if (raceDTO.getId() != null) {
            throw new BadRequestAlertException("A new race cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RaceDTO result = raceService.save(raceDTO);
        return ResponseEntity
            .created(new URI("/api/races/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /races/:id} : Updates an existing race.
     *
     * @param id the id of the raceDTO to save.
     * @param raceDTO the raceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raceDTO,
     * or with status {@code 400 (Bad Request)} if the raceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the raceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/races/{id}")
    public ResponseEntity<RaceDTO> updateRace(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RaceDTO raceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Race : {}, {}", id, raceDTO);
        if (raceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RaceDTO result = raceService.save(raceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /races/:id} : Partial updates given fields of an existing race, field will ignore if it is null
     *
     * @param id the id of the raceDTO to save.
     * @param raceDTO the raceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated raceDTO,
     * or with status {@code 400 (Bad Request)} if the raceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the raceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the raceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/races/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RaceDTO> partialUpdateRace(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RaceDTO raceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Race partially : {}, {}", id, raceDTO);
        if (raceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, raceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!raceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RaceDTO> result = raceService.partialUpdate(raceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, raceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /races} : get all the races.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of races in body.
     */
    @GetMapping("/races")
    public List<RaceDTO> getAllRaces() {
        log.debug("REST request to get all Races");
        return raceService.findAll();
    }

    /**
     * {@code GET  /races/:id} : get the "id" race.
     *
     * @param id the id of the raceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the raceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/races/{id}")
    public ResponseEntity<RaceDTO> getRace(@PathVariable Long id) {
        log.debug("REST request to get Race : {}", id);
        Optional<RaceDTO> raceDTO = raceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(raceDTO);
    }

    /**
     * {@code DELETE  /races/:id} : delete the "id" race.
     *
     * @param id the id of the raceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/races/{id}")
    public ResponseEntity<Void> deleteRace(@PathVariable Long id) {
        log.debug("REST request to delete Race : {}", id);
        raceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
