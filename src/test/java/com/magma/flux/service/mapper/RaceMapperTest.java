package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaceMapperTest {

    private RaceMapper raceMapper;

    @BeforeEach
    public void setUp() {
        raceMapper = new RaceMapperImpl();
    }
}
