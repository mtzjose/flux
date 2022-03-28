package com.magma.flux.service;

import com.magma.flux.service.dto.EmployeeRangeDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.EmployeeRange}.
 */
public interface EmployeeRangeService {
    /**
     * Save a employeeRange.
     *
     * @param employeeRangeDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeRangeDTO save(EmployeeRangeDTO employeeRangeDTO);

    /**
     * Partially updates a employeeRange.
     *
     * @param employeeRangeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EmployeeRangeDTO> partialUpdate(EmployeeRangeDTO employeeRangeDTO);

    /**
     * Get all the employeeRanges.
     *
     * @return the list of entities.
     */
    List<EmployeeRangeDTO> findAll();

    /**
     * Get the "id" employeeRange.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeRangeDTO> findOne(Long id);

    /**
     * Delete the "id" employeeRange.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
