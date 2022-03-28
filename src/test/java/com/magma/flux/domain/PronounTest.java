package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PronounTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pronoun.class);
        Pronoun pronoun1 = new Pronoun();
        pronoun1.setId(1L);
        Pronoun pronoun2 = new Pronoun();
        pronoun2.setId(pronoun1.getId());
        assertThat(pronoun1).isEqualTo(pronoun2);
        pronoun2.setId(2L);
        assertThat(pronoun1).isNotEqualTo(pronoun2);
        pronoun1.setId(null);
        assertThat(pronoun1).isNotEqualTo(pronoun2);
    }
}
