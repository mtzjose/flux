package com.magma.flux.service.mapper;

import com.magma.flux.domain.ProcessStage;
import com.magma.flux.service.dto.ProcessStageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcessStage} and its DTO {@link ProcessStageDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessStageMapper extends EntityMapper<ProcessStageDTO, ProcessStage> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProcessStageDTO toDtoId(ProcessStage processStage);
}
