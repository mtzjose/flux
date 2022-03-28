package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FounderPositionsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FounderPositions.class);
        FounderPositions founderPositions1 = new FounderPositions();
        founderPositions1.setId(1L);
        FounderPositions founderPositions2 = new FounderPositions();
        founderPositions2.setId(founderPositions1.getId());
        assertThat(founderPositions1).isEqualTo(founderPositions2);
        founderPositions2.setId(2L);
        assertThat(founderPositions1).isNotEqualTo(founderPositions2);
        founderPositions1.setId(null);
        assertThat(founderPositions1).isNotEqualTo(founderPositions2);
    }
}
