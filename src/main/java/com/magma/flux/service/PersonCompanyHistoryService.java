package com.magma.flux.service;

import com.magma.flux.service.dto.PersonCompanyHistoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.PersonCompanyHistory}.
 */
public interface PersonCompanyHistoryService {
    /**
     * Save a personCompanyHistory.
     *
     * @param personCompanyHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    PersonCompanyHistoryDTO save(PersonCompanyHistoryDTO personCompanyHistoryDTO);

    /**
     * Partially updates a personCompanyHistory.
     *
     * @param personCompanyHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PersonCompanyHistoryDTO> partialUpdate(PersonCompanyHistoryDTO personCompanyHistoryDTO);

    /**
     * Get all the personCompanyHistories.
     *
     * @return the list of entities.
     */
    List<PersonCompanyHistoryDTO> findAll();

    /**
     * Get the "id" personCompanyHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PersonCompanyHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" personCompanyHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
