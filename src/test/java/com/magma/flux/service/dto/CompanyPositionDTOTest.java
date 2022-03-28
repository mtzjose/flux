package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyPositionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyPositionDTO.class);
        CompanyPositionDTO companyPositionDTO1 = new CompanyPositionDTO();
        companyPositionDTO1.setId(1L);
        CompanyPositionDTO companyPositionDTO2 = new CompanyPositionDTO();
        assertThat(companyPositionDTO1).isNotEqualTo(companyPositionDTO2);
        companyPositionDTO2.setId(companyPositionDTO1.getId());
        assertThat(companyPositionDTO1).isEqualTo(companyPositionDTO2);
        companyPositionDTO2.setId(2L);
        assertThat(companyPositionDTO1).isNotEqualTo(companyPositionDTO2);
        companyPositionDTO1.setId(null);
        assertThat(companyPositionDTO1).isNotEqualTo(companyPositionDTO2);
    }
}
