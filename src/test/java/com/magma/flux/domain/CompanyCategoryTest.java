package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCategory.class);
        CompanyCategory companyCategory1 = new CompanyCategory();
        companyCategory1.setId(1L);
        CompanyCategory companyCategory2 = new CompanyCategory();
        companyCategory2.setId(companyCategory1.getId());
        assertThat(companyCategory1).isEqualTo(companyCategory2);
        companyCategory2.setId(2L);
        assertThat(companyCategory1).isNotEqualTo(companyCategory2);
        companyCategory1.setId(null);
        assertThat(companyCategory1).isNotEqualTo(companyCategory2);
    }
}
