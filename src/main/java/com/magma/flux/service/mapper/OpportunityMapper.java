package com.magma.flux.service.mapper;

import com.magma.flux.domain.Opportunity;
import com.magma.flux.service.dto.OpportunityDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Opportunity} and its DTO {@link OpportunityDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class, ContactSourceMapper.class, ProcessStageMapper.class })
public interface OpportunityMapper extends EntityMapper<OpportunityDTO, Opportunity> {
    @Mapping(target = "companyId", source = "companyId", qualifiedByName = "id")
    @Mapping(target = "contactSourceId", source = "contactSourceId", qualifiedByName = "id")
    @Mapping(target = "processStageId", source = "processStageId", qualifiedByName = "id")
    OpportunityDTO toDto(Opportunity s);
}
