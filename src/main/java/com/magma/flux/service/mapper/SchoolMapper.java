package com.magma.flux.service.mapper;

import com.magma.flux.domain.School;
import com.magma.flux.service.dto.SchoolDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link School} and its DTO {@link SchoolDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SchoolMapper extends EntityMapper<SchoolDTO, School> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SchoolDTO toDtoId(School school);
}
