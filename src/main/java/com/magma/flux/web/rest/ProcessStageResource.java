package com.magma.flux.web.rest;

import com.magma.flux.repository.ProcessStageRepository;
import com.magma.flux.service.ProcessStageService;
import com.magma.flux.service.dto.ProcessStageDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.ProcessStage}.
 */
@RestController
@RequestMapping("/api")
public class ProcessStageResource {

    private final Logger log = LoggerFactory.getLogger(ProcessStageResource.class);

    private static final String ENTITY_NAME = "processStage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessStageService processStageService;

    private final ProcessStageRepository processStageRepository;

    public ProcessStageResource(ProcessStageService processStageService, ProcessStageRepository processStageRepository) {
        this.processStageService = processStageService;
        this.processStageRepository = processStageRepository;
    }

    /**
     * {@code POST  /process-stages} : Create a new processStage.
     *
     * @param processStageDTO the processStageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processStageDTO, or with status {@code 400 (Bad Request)} if the processStage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-stages")
    public ResponseEntity<ProcessStageDTO> createProcessStage(@Valid @RequestBody ProcessStageDTO processStageDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProcessStage : {}", processStageDTO);
        if (processStageDTO.getId() != null) {
            throw new BadRequestAlertException("A new processStage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessStageDTO result = processStageService.save(processStageDTO);
        return ResponseEntity
            .created(new URI("/api/process-stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-stages/:id} : Updates an existing processStage.
     *
     * @param id the id of the processStageDTO to save.
     * @param processStageDTO the processStageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processStageDTO,
     * or with status {@code 400 (Bad Request)} if the processStageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processStageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-stages/{id}")
    public ResponseEntity<ProcessStageDTO> updateProcessStage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcessStageDTO processStageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessStage : {}, {}", id, processStageDTO);
        if (processStageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processStageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processStageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessStageDTO result = processStageService.save(processStageDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processStageDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /process-stages/:id} : Partial updates given fields of an existing processStage, field will ignore if it is null
     *
     * @param id the id of the processStageDTO to save.
     * @param processStageDTO the processStageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processStageDTO,
     * or with status {@code 400 (Bad Request)} if the processStageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the processStageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the processStageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/process-stages/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcessStageDTO> partialUpdateProcessStage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcessStageDTO processStageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcessStage partially : {}, {}", id, processStageDTO);
        if (processStageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processStageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processStageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcessStageDTO> result = processStageService.partialUpdate(processStageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processStageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /process-stages} : get all the processStages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processStages in body.
     */
    @GetMapping("/process-stages")
    public List<ProcessStageDTO> getAllProcessStages() {
        log.debug("REST request to get all ProcessStages");
        return processStageService.findAll();
    }

    /**
     * {@code GET  /process-stages/:id} : get the "id" processStage.
     *
     * @param id the id of the processStageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processStageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-stages/{id}")
    public ResponseEntity<ProcessStageDTO> getProcessStage(@PathVariable Long id) {
        log.debug("REST request to get ProcessStage : {}", id);
        Optional<ProcessStageDTO> processStageDTO = processStageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processStageDTO);
    }

    /**
     * {@code DELETE  /process-stages/:id} : delete the "id" processStage.
     *
     * @param id the id of the processStageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-stages/{id}")
    public ResponseEntity<Void> deleteProcessStage(@PathVariable Long id) {
        log.debug("REST request to delete ProcessStage : {}", id);
        processStageService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
