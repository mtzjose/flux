package com.magma.flux.repository;

import com.magma.flux.domain.CollegeDegree;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CollegeDegree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollegeDegreeRepository extends JpaRepository<CollegeDegree, Long> {}
