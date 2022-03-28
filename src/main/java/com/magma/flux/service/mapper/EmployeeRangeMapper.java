package com.magma.flux.service.mapper;

import com.magma.flux.domain.EmployeeRange;
import com.magma.flux.service.dto.EmployeeRangeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeRange} and its DTO {@link EmployeeRangeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeRangeMapper extends EntityMapper<EmployeeRangeDTO, EmployeeRange> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeRangeDTO toDtoId(EmployeeRange employeeRange);
}
