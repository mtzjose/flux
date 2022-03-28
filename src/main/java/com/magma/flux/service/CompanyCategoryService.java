package com.magma.flux.service;

import com.magma.flux.service.dto.CompanyCategoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.CompanyCategory}.
 */
public interface CompanyCategoryService {
    /**
     * Save a companyCategory.
     *
     * @param companyCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyCategoryDTO save(CompanyCategoryDTO companyCategoryDTO);

    /**
     * Partially updates a companyCategory.
     *
     * @param companyCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyCategoryDTO> partialUpdate(CompanyCategoryDTO companyCategoryDTO);

    /**
     * Get all the companyCategories.
     *
     * @return the list of entities.
     */
    List<CompanyCategoryDTO> findAll();

    /**
     * Get the "id" companyCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" companyCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
