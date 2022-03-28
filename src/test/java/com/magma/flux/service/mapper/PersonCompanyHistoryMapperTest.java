package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonCompanyHistoryMapperTest {

    private PersonCompanyHistoryMapper personCompanyHistoryMapper;

    @BeforeEach
    public void setUp() {
        personCompanyHistoryMapper = new PersonCompanyHistoryMapperImpl();
    }
}
