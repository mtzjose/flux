package com.magma.flux.service;

import com.magma.flux.service.dto.CompanyCategoriesDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.CompanyCategories}.
 */
public interface CompanyCategoriesService {
    /**
     * Save a companyCategories.
     *
     * @param companyCategoriesDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyCategoriesDTO save(CompanyCategoriesDTO companyCategoriesDTO);

    /**
     * Partially updates a companyCategories.
     *
     * @param companyCategoriesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyCategoriesDTO> partialUpdate(CompanyCategoriesDTO companyCategoriesDTO);

    /**
     * Get all the companyCategories.
     *
     * @return the list of entities.
     */
    List<CompanyCategoriesDTO> findAll();

    /**
     * Get the "id" companyCategories.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyCategoriesDTO> findOne(Long id);

    /**
     * Delete the "id" companyCategories.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
