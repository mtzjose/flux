package com.magma.flux.service;

import com.magma.flux.service.dto.ProcessStageDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.magma.flux.domain.ProcessStage}.
 */
public interface ProcessStageService {
    /**
     * Save a processStage.
     *
     * @param processStageDTO the entity to save.
     * @return the persisted entity.
     */
    ProcessStageDTO save(ProcessStageDTO processStageDTO);

    /**
     * Partially updates a processStage.
     *
     * @param processStageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProcessStageDTO> partialUpdate(ProcessStageDTO processStageDTO);

    /**
     * Get all the processStages.
     *
     * @return the list of entities.
     */
    List<ProcessStageDTO> findAll();

    /**
     * Get the "id" processStage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProcessStageDTO> findOne(Long id);

    /**
     * Delete the "id" processStage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
