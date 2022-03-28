package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcessStageMapperTest {

    private ProcessStageMapper processStageMapper;

    @BeforeEach
    public void setUp() {
        processStageMapper = new ProcessStageMapperImpl();
    }
}
