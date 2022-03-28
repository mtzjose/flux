package com.magma.flux.service;

import com.magma.flux.service.dto.GenderDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.Gender}.
 */
public interface GenderService {
    /**
     * Save a gender.
     *
     * @param genderDTO the entity to save.
     * @return the persisted entity.
     */
    GenderDTO save(GenderDTO genderDTO);

    /**
     * Partially updates a gender.
     *
     * @param genderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<GenderDTO> partialUpdate(GenderDTO genderDTO);

    /**
     * Get all the genders.
     *
     * @return the list of entities.
     */
    List<GenderDTO> findAll();

    /**
     * Get the "id" gender.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GenderDTO> findOne(Long id);

    /**
     * Delete the "id" gender.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
