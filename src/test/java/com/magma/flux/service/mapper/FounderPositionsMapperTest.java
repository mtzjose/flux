package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FounderPositionsMapperTest {

    private FounderPositionsMapper founderPositionsMapper;

    @BeforeEach
    public void setUp() {
        founderPositionsMapper = new FounderPositionsMapperImpl();
    }
}
