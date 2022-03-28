package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GenderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GenderDTO.class);
        GenderDTO genderDTO1 = new GenderDTO();
        genderDTO1.setId(1L);
        GenderDTO genderDTO2 = new GenderDTO();
        assertThat(genderDTO1).isNotEqualTo(genderDTO2);
        genderDTO2.setId(genderDTO1.getId());
        assertThat(genderDTO1).isEqualTo(genderDTO2);
        genderDTO2.setId(2L);
        assertThat(genderDTO1).isNotEqualTo(genderDTO2);
        genderDTO1.setId(null);
        assertThat(genderDTO1).isNotEqualTo(genderDTO2);
    }
}
