package com.magma.flux.web.rest;

import com.magma.flux.repository.FounderRepository;
import com.magma.flux.service.FounderService;
import com.magma.flux.service.dto.FounderDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.Founder}.
 */
@RestController
@RequestMapping("/api")
public class FounderResource {

    private final Logger log = LoggerFactory.getLogger(FounderResource.class);

    private static final String ENTITY_NAME = "founder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FounderService founderService;

    private final FounderRepository founderRepository;

    public FounderResource(FounderService founderService, FounderRepository founderRepository) {
        this.founderService = founderService;
        this.founderRepository = founderRepository;
    }

    /**
     * {@code POST  /founders} : Create a new founder.
     *
     * @param founderDTO the founderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new founderDTO, or with status {@code 400 (Bad Request)} if the founder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/founders")
    public ResponseEntity<FounderDTO> createFounder(@Valid @RequestBody FounderDTO founderDTO) throws URISyntaxException {
        log.debug("REST request to save Founder : {}", founderDTO);
        if (founderDTO.getId() != null) {
            throw new BadRequestAlertException("A new founder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FounderDTO result = founderService.save(founderDTO);
        return ResponseEntity
            .created(new URI("/api/founders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /founders/:id} : Updates an existing founder.
     *
     * @param id the id of the founderDTO to save.
     * @param founderDTO the founderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated founderDTO,
     * or with status {@code 400 (Bad Request)} if the founderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the founderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/founders/{id}")
    public ResponseEntity<FounderDTO> updateFounder(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FounderDTO founderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Founder : {}, {}", id, founderDTO);
        if (founderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, founderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!founderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FounderDTO result = founderService.save(founderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, founderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /founders/:id} : Partial updates given fields of an existing founder, field will ignore if it is null
     *
     * @param id the id of the founderDTO to save.
     * @param founderDTO the founderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated founderDTO,
     * or with status {@code 400 (Bad Request)} if the founderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the founderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the founderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/founders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FounderDTO> partialUpdateFounder(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FounderDTO founderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Founder partially : {}, {}", id, founderDTO);
        if (founderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, founderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!founderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FounderDTO> result = founderService.partialUpdate(founderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, founderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /founders} : get all the founders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of founders in body.
     */
    @GetMapping("/founders")
    public List<FounderDTO> getAllFounders() {
        log.debug("REST request to get all Founders");
        return founderService.findAll();
    }

    /**
     * {@code GET  /founders/:id} : get the "id" founder.
     *
     * @param id the id of the founderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the founderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/founders/{id}")
    public ResponseEntity<FounderDTO> getFounder(@PathVariable Long id) {
        log.debug("REST request to get Founder : {}", id);
        Optional<FounderDTO> founderDTO = founderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(founderDTO);
    }

    /**
     * {@code DELETE  /founders/:id} : delete the "id" founder.
     *
     * @param id the id of the founderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/founders/{id}")
    public ResponseEntity<Void> deleteFounder(@PathVariable Long id) {
        log.debug("REST request to delete Founder : {}", id);
        founderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
