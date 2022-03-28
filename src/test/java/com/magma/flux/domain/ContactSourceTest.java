package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContactSourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactSource.class);
        ContactSource contactSource1 = new ContactSource();
        contactSource1.setId(1L);
        ContactSource contactSource2 = new ContactSource();
        contactSource2.setId(contactSource1.getId());
        assertThat(contactSource1).isEqualTo(contactSource2);
        contactSource2.setId(2L);
        assertThat(contactSource1).isNotEqualTo(contactSource2);
        contactSource1.setId(null);
        assertThat(contactSource1).isNotEqualTo(contactSource2);
    }
}
