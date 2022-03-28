package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonCompanyHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonCompanyHistory.class);
        PersonCompanyHistory personCompanyHistory1 = new PersonCompanyHistory();
        personCompanyHistory1.setId(1L);
        PersonCompanyHistory personCompanyHistory2 = new PersonCompanyHistory();
        personCompanyHistory2.setId(personCompanyHistory1.getId());
        assertThat(personCompanyHistory1).isEqualTo(personCompanyHistory2);
        personCompanyHistory2.setId(2L);
        assertThat(personCompanyHistory1).isNotEqualTo(personCompanyHistory2);
        personCompanyHistory1.setId(null);
        assertThat(personCompanyHistory1).isNotEqualTo(personCompanyHistory2);
    }
}
