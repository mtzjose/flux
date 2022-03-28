package com.magma.flux.service;

import com.magma.flux.service.dto.FounderPositionsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.FounderPositions}.
 */
public interface FounderPositionsService {
    /**
     * Save a founderPositions.
     *
     * @param founderPositionsDTO the entity to save.
     * @return the persisted entity.
     */
    FounderPositionsDTO save(FounderPositionsDTO founderPositionsDTO);

    /**
     * Partially updates a founderPositions.
     *
     * @param founderPositionsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FounderPositionsDTO> partialUpdate(FounderPositionsDTO founderPositionsDTO);

    /**
     * Get all the founderPositions.
     *
     * @return the list of entities.
     */
    List<FounderPositionsDTO> findAll();

    /**
     * Get the "id" founderPositions.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FounderPositionsDTO> findOne(Long id);

    /**
     * Delete the "id" founderPositions.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
