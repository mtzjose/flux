package com.magma.flux.web.rest;

import com.magma.flux.repository.EmployeeRangeRepository;
import com.magma.flux.service.EmployeeRangeService;
import com.magma.flux.service.dto.EmployeeRangeDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.EmployeeRange}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeRangeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeRangeResource.class);

    private static final String ENTITY_NAME = "employeeRange";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeRangeService employeeRangeService;

    private final EmployeeRangeRepository employeeRangeRepository;

    public EmployeeRangeResource(EmployeeRangeService employeeRangeService, EmployeeRangeRepository employeeRangeRepository) {
        this.employeeRangeService = employeeRangeService;
        this.employeeRangeRepository = employeeRangeRepository;
    }

    /**
     * {@code POST  /employee-ranges} : Create a new employeeRange.
     *
     * @param employeeRangeDTO the employeeRangeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeRangeDTO, or with status {@code 400 (Bad Request)} if the employeeRange has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employee-ranges")
    public ResponseEntity<EmployeeRangeDTO> createEmployeeRange(@Valid @RequestBody EmployeeRangeDTO employeeRangeDTO)
        throws URISyntaxException {
        log.debug("REST request to save EmployeeRange : {}", employeeRangeDTO);
        if (employeeRangeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employeeRange cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeRangeDTO result = employeeRangeService.save(employeeRangeDTO);
        return ResponseEntity
            .created(new URI("/api/employee-ranges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /employee-ranges/:id} : Updates an existing employeeRange.
     *
     * @param id the id of the employeeRangeDTO to save.
     * @param employeeRangeDTO the employeeRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeRangeDTO,
     * or with status {@code 400 (Bad Request)} if the employeeRangeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employee-ranges/{id}")
    public ResponseEntity<EmployeeRangeDTO> updateEmployeeRange(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EmployeeRangeDTO employeeRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EmployeeRange : {}, {}", id, employeeRangeDTO);
        if (employeeRangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeRangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EmployeeRangeDTO result = employeeRangeService.save(employeeRangeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeRangeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /employee-ranges/:id} : Partial updates given fields of an existing employeeRange, field will ignore if it is null
     *
     * @param id the id of the employeeRangeDTO to save.
     * @param employeeRangeDTO the employeeRangeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeRangeDTO,
     * or with status {@code 400 (Bad Request)} if the employeeRangeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the employeeRangeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the employeeRangeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/employee-ranges/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EmployeeRangeDTO> partialUpdateEmployeeRange(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EmployeeRangeDTO employeeRangeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EmployeeRange partially : {}, {}", id, employeeRangeDTO);
        if (employeeRangeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, employeeRangeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!employeeRangeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EmployeeRangeDTO> result = employeeRangeService.partialUpdate(employeeRangeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeRangeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /employee-ranges} : get all the employeeRanges.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employeeRanges in body.
     */
    @GetMapping("/employee-ranges")
    public List<EmployeeRangeDTO> getAllEmployeeRanges() {
        log.debug("REST request to get all EmployeeRanges");
        return employeeRangeService.findAll();
    }

    /**
     * {@code GET  /employee-ranges/:id} : get the "id" employeeRange.
     *
     * @param id the id of the employeeRangeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeRangeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employee-ranges/{id}")
    public ResponseEntity<EmployeeRangeDTO> getEmployeeRange(@PathVariable Long id) {
        log.debug("REST request to get EmployeeRange : {}", id);
        Optional<EmployeeRangeDTO> employeeRangeDTO = employeeRangeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeRangeDTO);
    }

    /**
     * {@code DELETE  /employee-ranges/:id} : delete the "id" employeeRange.
     *
     * @param id the id of the employeeRangeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employee-ranges/{id}")
    public ResponseEntity<Void> deleteEmployeeRange(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeRange : {}", id);
        employeeRangeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
