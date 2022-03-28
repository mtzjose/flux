package com.magma.flux.repository;

import com.magma.flux.domain.EmployeeRange;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmployeeRange entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeRangeRepository extends JpaRepository<EmployeeRange, Long> {}
