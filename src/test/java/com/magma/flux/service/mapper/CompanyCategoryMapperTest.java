package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyCategoryMapperTest {

    private CompanyCategoryMapper companyCategoryMapper;

    @BeforeEach
    public void setUp() {
        companyCategoryMapper = new CompanyCategoryMapperImpl();
    }
}
