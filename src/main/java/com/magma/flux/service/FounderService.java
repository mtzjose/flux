package com.magma.flux.service;

import com.magma.flux.service.dto.FounderDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.Founder}.
 */
public interface FounderService {
    /**
     * Save a founder.
     *
     * @param founderDTO the entity to save.
     * @return the persisted entity.
     */
    FounderDTO save(FounderDTO founderDTO);

    /**
     * Partially updates a founder.
     *
     * @param founderDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FounderDTO> partialUpdate(FounderDTO founderDTO);

    /**
     * Get all the founders.
     *
     * @return the list of entities.
     */
    List<FounderDTO> findAll();

    /**
     * Get the "id" founder.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FounderDTO> findOne(Long id);

    /**
     * Delete the "id" founder.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
