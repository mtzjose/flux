package com.magma.flux.service.mapper;

import com.magma.flux.domain.Gender;
import com.magma.flux.service.dto.GenderDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gender} and its DTO {@link GenderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GenderMapper extends EntityMapper<GenderDTO, Gender> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    GenderDTO toDtoId(Gender gender);
}
