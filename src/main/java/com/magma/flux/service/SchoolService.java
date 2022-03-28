package com.magma.flux.service;

import com.magma.flux.service.dto.SchoolDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.School}.
 */
public interface SchoolService {
    /**
     * Save a school.
     *
     * @param schoolDTO the entity to save.
     * @return the persisted entity.
     */
    SchoolDTO save(SchoolDTO schoolDTO);

    /**
     * Partially updates a school.
     *
     * @param schoolDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SchoolDTO> partialUpdate(SchoolDTO schoolDTO);

    /**
     * Get all the schools.
     *
     * @return the list of entities.
     */
    List<SchoolDTO> findAll();

    /**
     * Get the "id" school.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SchoolDTO> findOne(Long id);

    /**
     * Delete the "id" school.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
