package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeRangeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRange.class);
        EmployeeRange employeeRange1 = new EmployeeRange();
        employeeRange1.setId(1L);
        EmployeeRange employeeRange2 = new EmployeeRange();
        employeeRange2.setId(employeeRange1.getId());
        assertThat(employeeRange1).isEqualTo(employeeRange2);
        employeeRange2.setId(2L);
        assertThat(employeeRange1).isNotEqualTo(employeeRange2);
        employeeRange1.setId(null);
        assertThat(employeeRange1).isNotEqualTo(employeeRange2);
    }
}
