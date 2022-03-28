package com.magma.flux.web.rest;

import com.magma.flux.repository.ContactSourceRepository;
import com.magma.flux.service.ContactSourceService;
import com.magma.flux.service.dto.ContactSourceDTO;
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
 * REST controller for managing {@link com.magma.flux.domain.ContactSource}.
 */
@RestController
@RequestMapping("/api")
public class ContactSourceResource {

    private final Logger log = LoggerFactory.getLogger(ContactSourceResource.class);

    private static final String ENTITY_NAME = "contactSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactSourceService contactSourceService;

    private final ContactSourceRepository contactSourceRepository;

    public ContactSourceResource(ContactSourceService contactSourceService, ContactSourceRepository contactSourceRepository) {
        this.contactSourceService = contactSourceService;
        this.contactSourceRepository = contactSourceRepository;
    }

    /**
     * {@code POST  /contact-sources} : Create a new contactSource.
     *
     * @param contactSourceDTO the contactSourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contactSourceDTO, or with status {@code 400 (Bad Request)} if the contactSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contact-sources")
    public ResponseEntity<ContactSourceDTO> createContactSource(@Valid @RequestBody ContactSourceDTO contactSourceDTO)
        throws URISyntaxException {
        log.debug("REST request to save ContactSource : {}", contactSourceDTO);
        if (contactSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new contactSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactSourceDTO result = contactSourceService.save(contactSourceDTO);
        return ResponseEntity
            .created(new URI("/api/contact-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contact-sources/:id} : Updates an existing contactSource.
     *
     * @param id the id of the contactSourceDTO to save.
     * @param contactSourceDTO the contactSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactSourceDTO,
     * or with status {@code 400 (Bad Request)} if the contactSourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contactSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contact-sources/{id}")
    public ResponseEntity<ContactSourceDTO> updateContactSource(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ContactSourceDTO contactSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ContactSource : {}, {}", id, contactSourceDTO);
        if (contactSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ContactSourceDTO result = contactSourceService.save(contactSourceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /contact-sources/:id} : Partial updates given fields of an existing contactSource, field will ignore if it is null
     *
     * @param id the id of the contactSourceDTO to save.
     * @param contactSourceDTO the contactSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contactSourceDTO,
     * or with status {@code 400 (Bad Request)} if the contactSourceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contactSourceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contactSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/contact-sources/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContactSourceDTO> partialUpdateContactSource(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ContactSourceDTO contactSourceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ContactSource partially : {}, {}", id, contactSourceDTO);
        if (contactSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contactSourceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contactSourceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContactSourceDTO> result = contactSourceService.partialUpdate(contactSourceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contactSourceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contact-sources} : get all the contactSources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contactSources in body.
     */
    @GetMapping("/contact-sources")
    public List<ContactSourceDTO> getAllContactSources() {
        log.debug("REST request to get all ContactSources");
        return contactSourceService.findAll();
    }

    /**
     * {@code GET  /contact-sources/:id} : get the "id" contactSource.
     *
     * @param id the id of the contactSourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contactSourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contact-sources/{id}")
    public ResponseEntity<ContactSourceDTO> getContactSource(@PathVariable Long id) {
        log.debug("REST request to get ContactSource : {}", id);
        Optional<ContactSourceDTO> contactSourceDTO = contactSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactSourceDTO);
    }

    /**
     * {@code DELETE  /contact-sources/:id} : delete the "id" contactSource.
     *
     * @param id the id of the contactSourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contact-sources/{id}")
    public ResponseEntity<Void> deleteContactSource(@PathVariable Long id) {
        log.debug("REST request to delete ContactSource : {}", id);
        contactSourceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
