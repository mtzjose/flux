package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactSourceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSourceDTO.class);
        ContactSourceDTO contactSourceDTO1 = new ContactSourceDTO();
        contactSourceDTO1.setId(1L);
        ContactSourceDTO contactSourceDTO2 = new ContactSourceDTO();
        assertThat(contactSourceDTO1).isNotEqualTo(contactSourceDTO2);
        contactSourceDTO2.setId(contactSourceDTO1.getId());
        assertThat(contactSourceDTO1).isEqualTo(contactSourceDTO2);
        contactSourceDTO2.setId(2L);
        assertThat(contactSourceDTO1).isNotEqualTo(contactSourceDTO2);
        contactSourceDTO1.setId(null);
        assertThat(contactSourceDTO1).isNotEqualTo(contactSourceDTO2);
    }
}
