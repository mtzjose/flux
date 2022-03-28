package com.magma.flux.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.magma.flux.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcessStageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcessStage.class);
        ProcessStage processStage1 = new ProcessStage();
        processStage1.setId(1L);
        ProcessStage processStage2 = new ProcessStage();
        processStage2.setId(processStage1.getId());
        assertThat(processStage1).isEqualTo(processStage2);
        processStage2.setId(2L);
        assertThat(processStage1).isNotEqualTo(processStage2);
        processStage1.setId(null);
        assertThat(processStage1).isNotEqualTo(processStage2);
    }
}
