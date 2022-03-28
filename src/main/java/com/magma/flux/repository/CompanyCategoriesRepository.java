package com.magma.flux.repository;

import com.magma.flux.domain.CompanyCategories;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CompanyCategories entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CompanyCategoriesRepository extends JpaRepository<CompanyCategories, Long> {}
