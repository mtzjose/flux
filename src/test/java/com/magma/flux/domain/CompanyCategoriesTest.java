package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CompanyCategoriesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyCategories.class);
        CompanyCategories companyCategories1 = new CompanyCategories();
        companyCategories1.setId(1L);
        CompanyCategories companyCategories2 = new CompanyCategories();
        companyCategories2.setId(companyCategories1.getId());
        assertThat(companyCategories1).isEqualTo(companyCategories2);
        companyCategories2.setId(2L);
        assertThat(companyCategories1).isNotEqualTo(companyCategories2);
        companyCategories1.setId(null);
        assertThat(companyCategories1).isNotEqualTo(companyCategories2);
    }
}
