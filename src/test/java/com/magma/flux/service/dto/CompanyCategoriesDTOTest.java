package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyCategoriesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCategoriesDTO.class);
        CompanyCategoriesDTO companyCategoriesDTO1 = new CompanyCategoriesDTO();
        companyCategoriesDTO1.setId(1L);
        CompanyCategoriesDTO companyCategoriesDTO2 = new CompanyCategoriesDTO();
        assertThat(companyCategoriesDTO1).isNotEqualTo(companyCategoriesDTO2);
        companyCategoriesDTO2.setId(companyCategoriesDTO1.getId());
        assertThat(companyCategoriesDTO1).isEqualTo(companyCategoriesDTO2);
        companyCategoriesDTO2.setId(2L);
        assertThat(companyCategoriesDTO1).isNotEqualTo(companyCategoriesDTO2);
        companyCategoriesDTO1.setId(null);
        assertThat(companyCategoriesDTO1).isNotEqualTo(companyCategoriesDTO2);
    }
}
