package com.magma.flux.service.mapper;

import com.magma.flux.domain.CompanyCategories;
import com.magma.flux.service.dto.CompanyCategoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CompanyCategories} and its DTO {@link CompanyCategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompanyCategoriesMapper extends EntityMapper<CompanyCategoriesDTO, CompanyCategories> {}
