package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FounderDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FounderDTO.class);
        FounderDTO founderDTO1 = new FounderDTO();
        founderDTO1.setId(1L);
        FounderDTO founderDTO2 = new FounderDTO();
        assertThat(founderDTO1).isNotEqualTo(founderDTO2);
        founderDTO2.setId(founderDTO1.getId());
        assertThat(founderDTO1).isEqualTo(founderDTO2);
        founderDTO2.setId(2L);
        assertThat(founderDTO1).isNotEqualTo(founderDTO2);
        founderDTO1.setId(null);
        assertThat(founderDTO1).isNotEqualTo(founderDTO2);
    }
}
