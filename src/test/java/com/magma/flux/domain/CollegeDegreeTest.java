package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollegeDegreeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollegeDegree.class);
        CollegeDegree collegeDegree1 = new CollegeDegree();
        collegeDegree1.setId(1L);
        CollegeDegree collegeDegree2 = new CollegeDegree();
        collegeDegree2.setId(collegeDegree1.getId());
        assertThat(collegeDegree1).isEqualTo(collegeDegree2);
        collegeDegree2.setId(2L);
        assertThat(collegeDegree1).isNotEqualTo(collegeDegree2);
        collegeDegree1.setId(null);
        assertThat(collegeDegree1).isNotEqualTo(collegeDegree2);
    }
}
