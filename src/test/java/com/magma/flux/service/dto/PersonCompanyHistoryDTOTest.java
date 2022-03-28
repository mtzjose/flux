package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonCompanyHistoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonCompanyHistoryDTO.class);
        PersonCompanyHistoryDTO personCompanyHistoryDTO1 = new PersonCompanyHistoryDTO();
        personCompanyHistoryDTO1.setId(1L);
        PersonCompanyHistoryDTO personCompanyHistoryDTO2 = new PersonCompanyHistoryDTO();
        assertThat(personCompanyHistoryDTO1).isNotEqualTo(personCompanyHistoryDTO2);
        personCompanyHistoryDTO2.setId(personCompanyHistoryDTO1.getId());
        assertThat(personCompanyHistoryDTO1).isEqualTo(personCompanyHistoryDTO2);
        personCompanyHistoryDTO2.setId(2L);
        assertThat(personCompanyHistoryDTO1).isNotEqualTo(personCompanyHistoryDTO2);
        personCompanyHistoryDTO1.setId(null);
        assertThat(personCompanyHistoryDTO1).isNotEqualTo(personCompanyHistoryDTO2);
    }
}
