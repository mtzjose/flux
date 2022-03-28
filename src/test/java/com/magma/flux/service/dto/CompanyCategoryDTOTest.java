package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCategoryDTO.class);
        CompanyCategoryDTO companyCategoryDTO1 = new CompanyCategoryDTO();
        companyCategoryDTO1.setId(1L);
        CompanyCategoryDTO companyCategoryDTO2 = new CompanyCategoryDTO();
        assertThat(companyCategoryDTO1).isNotEqualTo(companyCategoryDTO2);
        companyCategoryDTO2.setId(companyCategoryDTO1.getId());
        assertThat(companyCategoryDTO1).isEqualTo(companyCategoryDTO2);
        companyCategoryDTO2.setId(2L);
        assertThat(companyCategoryDTO1).isNotEqualTo(companyCategoryDTO2);
        companyCategoryDTO1.setId(null);
        assertThat(companyCategoryDTO1).isNotEqualTo(companyCategoryDTO2);
    }
}
