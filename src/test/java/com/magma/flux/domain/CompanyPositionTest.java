package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyPositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyPosition.class);
        CompanyPosition companyPosition1 = new CompanyPosition();
        companyPosition1.setId(1L);
        CompanyPosition companyPosition2 = new CompanyPosition();
        companyPosition2.setId(companyPosition1.getId());
        assertThat(companyPosition1).isEqualTo(companyPosition2);
        companyPosition2.setId(2L);
        assertThat(companyPosition1).isNotEqualTo(companyPosition2);
        companyPosition1.setId(null);
        assertThat(companyPosition1).isNotEqualTo(companyPosition2);
    }
}
