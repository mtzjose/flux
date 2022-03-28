package com.magma.flux.service;

import com.magma.flux.service.dto.PronounDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.Pronoun}.
 */
public interface PronounService {
    /**
     * Save a pronoun.
     *
     * @param pronounDTO the entity to save.
     * @return the persisted entity.
     */
    PronounDTO save(PronounDTO pronounDTO);

    /**
     * Partially updates a pronoun.
     *
     * @param pronounDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PronounDTO> partialUpdate(PronounDTO pronounDTO);

    /**
     * Get all the pronouns.
     *
     * @return the list of entities.
     */
    List<PronounDTO> findAll();

    /**
     * Get the "id" pronoun.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PronounDTO> findOne(Long id);

    /**
     * Delete the "id" pronoun.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
