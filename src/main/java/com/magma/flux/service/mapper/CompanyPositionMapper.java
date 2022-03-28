package com.magma.flux.service.mapper;

import com.magma.flux.domain.CompanyPosition;
import com.magma.flux.service.dto.CompanyPositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyPosition} and its DTO {@link CompanyPositionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyPositionMapper extends EntityMapper<CompanyPositionDTO, CompanyPosition> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyPositionDTO toDtoId(CompanyPosition companyPosition);
}
