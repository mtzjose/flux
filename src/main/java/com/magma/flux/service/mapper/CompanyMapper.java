package com.magma.flux.service.mapper;

import com.magma.flux.domain.Company;
import com.magma.flux.service.dto.CompanyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring", uses = { AddressMapper.class, EmployeeRangeMapper.class })
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {
    @Mapping(target = "addressId", source = "addressId", qualifiedByName = "id")
    @Mapping(target = "employeeRange", source = "employeeRange", qualifiedByName = "id")
    CompanyDTO toDto(Company s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CompanyDTO toDtoId(Company company);
}
