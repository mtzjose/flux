package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollegeDegreeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CollegeDegreeDTO.class);
        CollegeDegreeDTO collegeDegreeDTO1 = new CollegeDegreeDTO();
        collegeDegreeDTO1.setId(1L);
        CollegeDegreeDTO collegeDegreeDTO2 = new CollegeDegreeDTO();
        assertThat(collegeDegreeDTO1).isNotEqualTo(collegeDegreeDTO2);
        collegeDegreeDTO2.setId(collegeDegreeDTO1.getId());
        assertThat(collegeDegreeDTO1).isEqualTo(collegeDegreeDTO2);
        collegeDegreeDTO2.setId(2L);
        assertThat(collegeDegreeDTO1).isNotEqualTo(collegeDegreeDTO2);
        collegeDegreeDTO1.setId(null);
        assertThat(collegeDegreeDTO1).isNotEqualTo(collegeDegreeDTO2);
    }
}
