package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FounderPositionsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FounderPositionsDTO.class);
        FounderPositionsDTO founderPositionsDTO1 = new FounderPositionsDTO();
        founderPositionsDTO1.setId(1L);
        FounderPositionsDTO founderPositionsDTO2 = new FounderPositionsDTO();
        assertThat(founderPositionsDTO1).isNotEqualTo(founderPositionsDTO2);
        founderPositionsDTO2.setId(founderPositionsDTO1.getId());
        assertThat(founderPositionsDTO1).isEqualTo(founderPositionsDTO2);
        founderPositionsDTO2.setId(2L);
        assertThat(founderPositionsDTO1).isNotEqualTo(founderPositionsDTO2);
        founderPositionsDTO1.setId(null);
        assertThat(founderPositionsDTO1).isNotEqualTo(founderPositionsDTO2);
    }
}
