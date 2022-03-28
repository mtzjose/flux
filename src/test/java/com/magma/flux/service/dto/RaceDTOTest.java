package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RaceDTO.class);
        RaceDTO raceDTO1 = new RaceDTO();
        raceDTO1.setId(1L);
        RaceDTO raceDTO2 = new RaceDTO();
        assertThat(raceDTO1).isNotEqualTo(raceDTO2);
        raceDTO2.setId(raceDTO1.getId());
        assertThat(raceDTO1).isEqualTo(raceDTO2);
        raceDTO2.setId(2L);
        assertThat(raceDTO1).isNotEqualTo(raceDTO2);
        raceDTO1.setId(null);
        assertThat(raceDTO1).isNotEqualTo(raceDTO2);
    }
}
