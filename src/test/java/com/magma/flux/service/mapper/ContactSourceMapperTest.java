package com.magma.flux.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContactSourceMapperTest {

    private ContactSourceMapper contactSourceMapper;

    @BeforeEach
    public void setUp() {
        contactSourceMapper = new ContactSourceMapperImpl();
    }
}
