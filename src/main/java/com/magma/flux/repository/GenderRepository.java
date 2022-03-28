package com.magma.flux.repository;

import com.magma.flux.domain.Gender;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Gender entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {}
