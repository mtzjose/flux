package com.magma.flux.repository;

import com.magma.flux.domain.Founder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Founder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FounderRepository extends JpaRepository<Founder, Long> {}
