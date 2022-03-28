package com.magma.flux.service.impl;

import com.magma.flux.domain.ProcessStage;
import com.magma.flux.repository.ProcessStageRepository;
import com.magma.flux.service.ProcessStageService;
import com.magma.flux.service.dto.ProcessStageDTO;
import com.magma.flux.service.mapper.ProcessStageMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProcessStage}.
 */
@Service
@Transactional
public class ProcessStageServiceImpl implements ProcessStageService {

    private final Logger log = LoggerFactory.getLogger(ProcessStageServiceImpl.class);

    private final ProcessStageRepository processStageRepository;

    private final ProcessStageMapper processStageMapper;

    public ProcessStageServiceImpl(ProcessStageRepository processStageRepository, ProcessStageMapper processStageMapper) {
        this.processStageRepository = processStageRepository;
        this.processStageMapper = processStageMapper;
    }

    @Override
    public ProcessStageDTO save(ProcessStageDTO processStageDTO) {
        log.debug("Request to save ProcessStage : {}", processStageDTO);
        ProcessStage processStage = processStageMapper.toEntity(processStageDTO);
        processStage = processStageRepository.save(processStage);
        return processStageMapper.toDto(processStage);
    }

    @Override
    public Optional<ProcessStageDTO> partialUpdate(ProcessStageDTO processStageDTO) {
        log.debug("Request to partially update ProcessStage : {}", processStageDTO);

        return processStageRepository
            .findById(processStageDTO.getId())
            .map(existingProcessStage -> {
                processStageMapper.partialUpdate(existingProcessStage, processStageDTO);

                return existingProcessStage;
            })
            .map(processStageRepository::save)
            .map(processStageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProcessStageDTO> findAll() {
        log.debug("Request to get all ProcessStages");
        return processStageRepository.findAll().stream().map(processStageMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProcessStageDTO> findOne(Long id) {
        log.debug("Request to get ProcessStage : {}", id);
        return processStageRepository.findById(id).map(processStageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcessStage : {}", id);
        processStageRepository.deleteById(id);
    }
}
