package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FounderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Founder.class);
        Founder founder1 = new Founder();
        founder1.setId(1L);
        Founder founder2 = new Founder();
        founder2.setId(founder1.getId());
        assertThat(founder1).isEqualTo(founder2);
        founder2.setId(2L);
        assertThat(founder1).isNotEqualTo(founder2);
        founder1.setId(null);
        assertThat(founder1).isNotEqualTo(founder2);
    }
}
