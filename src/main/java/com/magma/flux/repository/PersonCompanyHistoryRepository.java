package com.magma.flux.repository;

import com.magma.flux.domain.PersonCompanyHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonCompanyHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonCompanyHistoryRepository extends JpaRepository<PersonCompanyHistory, Long> {}
