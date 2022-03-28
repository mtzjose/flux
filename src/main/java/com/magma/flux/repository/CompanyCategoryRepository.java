package com.magma.flux.repository;

import com.magma.flux.domain.CompanyCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCategoryRepository extends JpaRepository<CompanyCategory, Long> {}
