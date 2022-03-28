package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeRangeMapperTest {

    private EmployeeRangeMapper employeeRangeMapper;

    @BeforeEach
    public void setUp() {
        employeeRangeMapper = new EmployeeRangeMapperImpl();
    }
}
