package com.magma.flux.service.mapper;

import com.magma.flux.domain.FounderPositions;
import com.magma.flux.service.dto.FounderPositionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FounderPositions} and its DTO {@link FounderPositionsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FounderPositionsMapper extends EntityMapper<FounderPositionsDTO, FounderPositions> {}
