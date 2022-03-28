package com.magma.flux.service;

import com.magma.flux.service.dto.CollegeDegreeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.CollegeDegree}.
 */
public interface CollegeDegreeService {
    /**
     * Save a collegeDegree.
     *
     * @param collegeDegreeDTO the entity to save.
     * @return the persisted entity.
     */
    CollegeDegreeDTO save(CollegeDegreeDTO collegeDegreeDTO);

    /**
     * Partially updates a collegeDegree.
     *
     * @param collegeDegreeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollegeDegreeDTO> partialUpdate(CollegeDegreeDTO collegeDegreeDTO);

    /**
     * Get all the collegeDegrees.
     *
     * @return the list of entities.
     */
    List<CollegeDegreeDTO> findAll();

    /**
     * Get the "id" collegeDegree.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollegeDegreeDTO> findOne(Long id);

    /**
     * Delete the "id" collegeDegree.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
