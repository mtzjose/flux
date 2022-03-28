package com.magma.flux.service.mapper;

import com.magma.flux.domain.Founder;
import com.magma.flux.service.dto.FounderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Founder} and its DTO {@link FounderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FounderMapper extends EntityMapper<FounderDTO, Founder> {}
