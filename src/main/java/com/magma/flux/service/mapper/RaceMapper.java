package com.magma.flux.service.mapper;

import com.magma.flux.domain.Race;
import com.magma.flux.service.dto.RaceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Race} and its DTO {@link RaceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RaceMapper extends EntityMapper<RaceDTO, Race> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RaceDTO toDtoId(Race race);
}
