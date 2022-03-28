package com.magma.flux.service.mapper;

import com.magma.flux.domain.Job;
import com.magma.flux.service.dto.JobDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Job} and its DTO {@link JobDTO}.
 */
@Mapper(componentModel = "spring", uses = { CompanyMapper.class, CompanyPositionMapper.class })
public interface JobMapper extends EntityMapper<JobDTO, Job> {
    @Mapping(target = "companyId", source = "companyId", qualifiedByName = "id")
    @Mapping(target = "jobPositionId", source = "jobPositionId", qualifiedByName = "id")
    @Mapping(target = "companyId", source = "companyId", qualifiedByName = "id")
    @Mapping(target = "jobPositionId", source = "jobPositionId", qualifiedByName = "id")
    JobDTO toDto(Job s);
}
