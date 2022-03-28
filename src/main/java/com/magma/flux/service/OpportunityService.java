package com.magma.flux.service;

import com.magma.flux.service.dto.OpportunityDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.Opportunity}.
 */
public interface OpportunityService {
    /**
     * Save a opportunity.
     *
     * @param opportunityDTO the entity to save.
     * @return the persisted entity.
     */
    OpportunityDTO save(OpportunityDTO opportunityDTO);

    /**
     * Partially updates a opportunity.
     *
     * @param opportunityDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OpportunityDTO> partialUpdate(OpportunityDTO opportunityDTO);

    /**
     * Get all the opportunities.
     *
     * @return the list of entities.
     */
    List<OpportunityDTO> findAll();

    /**
     * Get the "id" opportunity.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OpportunityDTO> findOne(Long id);

    /**
     * Delete the "id" opportunity.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
