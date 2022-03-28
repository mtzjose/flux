package com.magma.flux.service.mapper;

import com.magma.flux.domain.Pronoun;
import com.magma.flux.service.dto.PronounDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pronoun} and its DTO {@link PronounDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PronounMapper extends EntityMapper<PronounDTO, Pronoun> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PronounDTO toDtoId(Pronoun pronoun);
}
