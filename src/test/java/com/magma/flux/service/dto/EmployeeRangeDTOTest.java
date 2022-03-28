package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeRangeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRangeDTO.class);
        EmployeeRangeDTO employeeRangeDTO1 = new EmployeeRangeDTO();
        employeeRangeDTO1.setId(1L);
        EmployeeRangeDTO employeeRangeDTO2 = new EmployeeRangeDTO();
        assertThat(employeeRangeDTO1).isNotEqualTo(employeeRangeDTO2);
        employeeRangeDTO2.setId(employeeRangeDTO1.getId());
        assertThat(employeeRangeDTO1).isEqualTo(employeeRangeDTO2);
        employeeRangeDTO2.setId(2L);
        assertThat(employeeRangeDTO1).isNotEqualTo(employeeRangeDTO2);
        employeeRangeDTO1.setId(null);
        assertThat(employeeRangeDTO1).isNotEqualTo(employeeRangeDTO2);
    }
}
