package com.magma.flux.service.mapper;

import com.magma.flux.domain.Person;
import com.magma.flux.service.dto.PersonDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Person} and its DTO {@link PersonDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        SchoolMapper.class,
        CollegeDegreeMapper.class,
        CountryMapper.class,
        GenderMapper.class,
        PronounMapper.class,
        RaceMapper.class,
        AddressMapper.class,
    }
)
public interface PersonMapper extends EntityMapper<PersonDTO, Person> {
    @Mapping(target = "school", source = "school", qualifiedByName = "id")
    @Mapping(target = "major", source = "major", qualifiedByName = "id")
    @Mapping(target = "nationalityId", source = "nationalityId", qualifiedByName = "id")
    @Mapping(target = "genderId", source = "genderId", qualifiedByName = "id")
    @Mapping(target = "pronounId", source = "pronounId", qualifiedByName = "id")
    @Mapping(target = "raceId", source = "raceId", qualifiedByName = "id")
    @Mapping(target = "addressId", source = "addressId", qualifiedByName = "id")
    PersonDTO toDto(Person s);
}
