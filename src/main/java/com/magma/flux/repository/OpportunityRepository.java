package com.magma.flux.repository;

import com.magma.flux.domain.Opportunity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Opportunity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {}
