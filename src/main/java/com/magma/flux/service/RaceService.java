package com.magma.flux.service;

import com.magma.flux.service.dto.RaceDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.Race}.
 */
public interface RaceService {
    /**
     * Save a race.
     *
     * @param raceDTO the entity to save.
     * @return the persisted entity.
     */
    RaceDTO save(RaceDTO raceDTO);

    /**
     * Partially updates a race.
     *
     * @param raceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RaceDTO> partialUpdate(RaceDTO raceDTO);

    /**
     * Get all the races.
     *
     * @return the list of entities.
     */
    List<RaceDTO> findAll();

    /**
     * Get the "id" race.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RaceDTO> findOne(Long id);

    /**
     * Delete the "id" race.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
