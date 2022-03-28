package com.magma.flux.repository;

import com.magma.flux.domain.Pronoun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pronoun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PronounRepository extends JpaRepository<Pronoun, Long> {}
