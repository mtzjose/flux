package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FounderMapperTest {

    private FounderMapper founderMapper;

    @BeforeEach
    public void setUp() {
        founderMapper = new FounderMapperImpl();
    }
}
