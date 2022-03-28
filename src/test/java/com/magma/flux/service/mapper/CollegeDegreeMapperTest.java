package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CollegeDegreeMapperTest {

    private CollegeDegreeMapper collegeDegreeMapper;

    @BeforeEach
    public void setUp() {
        collegeDegreeMapper = new CollegeDegreeMapperImpl();
    }
}
