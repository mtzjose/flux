package com.magma.flux.repository;

import com.magma.flux.domain.Race;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Race entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {}
