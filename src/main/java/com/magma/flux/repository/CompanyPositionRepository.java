package com.magma.flux.repository;

import com.magma.flux.domain.CompanyPosition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyPosition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyPositionRepository extends JpaRepository<CompanyPosition, Long> {}
