package com.magma.flux.service.mapper;

import com.magma.flux.domain.CompanyCategory;
import com.magma.flux.service.dto.CompanyCategoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyCategory} and its DTO {@link CompanyCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyCategoryMapper extends EntityMapper<CompanyCategoryDTO, CompanyCategory> {}
