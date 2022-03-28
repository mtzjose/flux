package com.magma.flux.service;

import com.magma.flux.service.dto.ContactSourceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.ContactSource}.
 */
public interface ContactSourceService {
    /**
     * Save a contactSource.
     *
     * @param contactSourceDTO the entity to save.
     * @return the persisted entity.
     */
    ContactSourceDTO save(ContactSourceDTO contactSourceDTO);

    /**
     * Partially updates a contactSource.
     *
     * @param contactSourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactSourceDTO> partialUpdate(ContactSourceDTO contactSourceDTO);

    /**
     * Get all the contactSources.
     *
     * @return the list of entities.
     */
    List<ContactSourceDTO> findAll();

    /**
     * Get the "id" contactSource.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactSourceDTO> findOne(Long id);

    /**
     * Delete the "id" contactSource.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
