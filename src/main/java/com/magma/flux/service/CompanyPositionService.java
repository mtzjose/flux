package com.magma.flux.service;

import com.magma.flux.service.dto.CompanyPositionDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.CompanyPosition}.
 */
public interface CompanyPositionService {
    /**
     * Save a companyPosition.
     *
     * @param companyPositionDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyPositionDTO save(CompanyPositionDTO companyPositionDTO);

    /**
     * Partially updates a companyPosition.
     *
     * @param companyPositionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyPositionDTO> partialUpdate(CompanyPositionDTO companyPositionDTO);

    /**
     * Get all the companyPositions.
     *
     * @return the list of entities.
     */
    List<CompanyPositionDTO> findAll();

    /**
     * Get the "id" companyPosition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyPositionDTO> findOne(Long id);

    /**
     * Delete the "id" companyPosition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
