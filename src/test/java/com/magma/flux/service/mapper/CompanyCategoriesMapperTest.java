package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyCategoriesMapperTest {

    private CompanyCategoriesMapper companyCategoriesMapper;

    @BeforeEach
    public void setUp() {
        companyCategoriesMapper = new CompanyCategoriesMapperImpl();
    }
}
