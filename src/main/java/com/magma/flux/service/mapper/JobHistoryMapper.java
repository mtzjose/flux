package com.magma.flux.service.mapper;

import com.magma.flux.domain.JobHistory;
import com.magma.flux.service.dto.JobHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobHistory} and its DTO {@link JobHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobHistoryMapper extends EntityMapper<JobHistoryDTO, JobHistory> {}
