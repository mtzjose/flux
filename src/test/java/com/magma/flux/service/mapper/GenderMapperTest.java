package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenderMapperTest {

    private GenderMapper genderMapper;

    @BeforeEach
    public void setUp() {
        genderMapper = new GenderMapperImpl();
    }
}
