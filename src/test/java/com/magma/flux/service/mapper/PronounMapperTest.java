package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PronounMapperTest {

    private PronounMapper pronounMapper;

    @BeforeEach
    public void setUp() {
        pronounMapper = new PronounMapperImpl();
    }
}
