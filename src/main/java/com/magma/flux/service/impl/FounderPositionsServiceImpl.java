package com.magma.flux.service.impl;

import com.magma.flux.domain.FounderPositions;
import com.magma.flux.repository.FounderPositionsRepository;
import com.magma.flux.service.FounderPositionsService;
import com.magma.flux.service.dto.FounderPositionsDTO;
import com.magma.flux.service.mapper.FounderPositionsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FounderPositions}.
 */
@Service
@Transactional
public class FounderPositionsServiceImpl implements FounderPositionsService {

    private final Logger log = LoggerFactory.getLogger(FounderPositionsServiceImpl.class);

    private final FounderPositionsRepository founderPositionsRepository;

    private final FounderPositionsMapper founderPositionsMapper;

    public FounderPositionsServiceImpl(
        FounderPositionsRepository founderPositionsRepository,
        FounderPositionsMapper founderPositionsMapper
    ) {
        this.founderPositionsRepository = founderPositionsRepository;
        this.founderPositionsMapper = founderPositionsMapper;
    }

    @Override
    public FounderPositionsDTO save(FounderPositionsDTO founderPositionsDTO) {
        log.debug("Request to save FounderPositions : {}", founderPositionsDTO);
        FounderPositions founderPositions = founderPositionsMapper.toEntity(founderPositionsDTO);
        founderPositions = founderPositionsRepository.save(founderPositions);
        return founderPositionsMapper.toDto(founderPositions);
    }

    @Override
    public Optional<FounderPositionsDTO> partialUpdate(FounderPositionsDTO founderPositionsDTO) {
        log.debug("Request to partially update FounderPositions : {}", founderPositionsDTO);

        return founderPositionsRepository
            .findById(founderPositionsDTO.getId())
            .map(existingFounderPositions -> {
                founderPositionsMapper.partialUpdate(existingFounderPositions, founderPositionsDTO);

                return existingFounderPositions;
            })
            .map(founderPositionsRepository::save)
            .map(founderPositionsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FounderPositionsDTO> findAll() {
        log.debug("Request to get all FounderPositions");
        return founderPositionsRepository
            .findAll()
            .stream()
            .map(founderPositionsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FounderPositionsDTO> findOne(Long id) {
        log.debug("Request to get FounderPositions : {}", id);
        return founderPositionsRepository.findById(id).map(founderPositionsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FounderPositions : {}", id);
        founderPositionsRepository.deleteById(id);
    }
}
