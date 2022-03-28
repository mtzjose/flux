package com.magma.flux.repository;

import com.magma.flux.domain.ProcessStage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcessStage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessStageRepository extends JpaRepository<ProcessStage, Long> {}
