package com.magma.flux.service.mapper;

import com.magma.flux.domain.CollegeDegree;
import com.magma.flux.service.dto.CollegeDegreeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CollegeDegree} and its DTO {@link CollegeDegreeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CollegeDegreeMapper extends EntityMapper<CollegeDegreeDTO, CollegeDegree> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CollegeDegreeDTO toDtoId(CollegeDegree collegeDegree);
}
