package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpportunityMapperTest {

    private OpportunityMapper opportunityMapper;

    @BeforeEach
    public void setUp() {
        opportunityMapper = new OpportunityMapperImpl();
    }
}
