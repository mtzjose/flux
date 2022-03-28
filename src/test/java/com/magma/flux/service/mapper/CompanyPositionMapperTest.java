package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyPositionMapperTest {

    private CompanyPositionMapper companyPositionMapper;

    @BeforeEach
    public void setUp() {
        companyPositionMapper = new CompanyPositionMapperImpl();
    }
}
