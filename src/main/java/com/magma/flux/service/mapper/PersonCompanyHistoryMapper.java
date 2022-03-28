package com.magma.flux.service.mapper;

import com.magma.flux.domain.PersonCompanyHistory;
import com.magma.flux.service.dto.PersonCompanyHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonCompanyHistory} and its DTO {@link PersonCompanyHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PersonCompanyHistoryMapper extends EntityMapper<PersonCompanyHistoryDTO, PersonCompanyHistory> {}
