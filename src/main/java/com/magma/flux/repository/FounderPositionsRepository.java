package com.magma.flux.repository;

import com.magma.flux.domain.FounderPositions;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the FounderPositions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FounderPositionsRepository extends JpaRepository<FounderPositions, Long> {}
