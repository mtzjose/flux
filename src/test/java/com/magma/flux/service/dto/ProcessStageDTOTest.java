package com.magma.flux.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessStageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessStageDTO.class);
        ProcessStageDTO processStageDTO1 = new ProcessStageDTO();
        processStageDTO1.setId(1L);
        ProcessStageDTO processStageDTO2 = new ProcessStageDTO();
        assertThat(processStageDTO1).isNotEqualTo(processStageDTO2);
        processStageDTO2.setId(processStageDTO1.getId());
        assertThat(processStageDTO1).isEqualTo(processStageDTO2);
        processStageDTO2.setId(2L);
        assertThat(processStageDTO1).isNotEqualTo(processStageDTO2);
        processStageDTO1.setId(null);
        assertThat(processStageDTO1).isNotEqualTo(processStageDTO2);
    }
}
