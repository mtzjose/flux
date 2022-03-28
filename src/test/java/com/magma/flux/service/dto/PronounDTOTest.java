package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PronounDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PronounDTO.class);
        PronounDTO pronounDTO1 = new PronounDTO();
        pronounDTO1.setId(1L);
        PronounDTO pronounDTO2 = new PronounDTO();
        assertThat(pronounDTO1).isNotEqualTo(pronounDTO2);
        pronounDTO2.setId(pronounDTO1.getId());
        assertThat(pronounDTO1).isEqualTo(pronounDTO2);
        pronounDTO2.setId(2L);
        assertThat(pronounDTO1).isNotEqualTo(pronounDTO2);
        pronounDTO1.setId(null);
        assertThat(pronounDTO1).isNotEqualTo(pronounDTO2);
    }
}
