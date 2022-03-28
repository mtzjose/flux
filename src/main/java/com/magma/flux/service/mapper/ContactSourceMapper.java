package com.magma.flux.service.mapper;

import com.magma.flux.domain.ContactSource;
import com.magma.flux.service.dto.ContactSourceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ContactSource} and its DTO {@link ContactSourceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ContactSourceMapper extends EntityMapper<ContactSourceDTO, ContactSource> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ContactSourceDTO toDtoId(ContactSource contactSource);
}
