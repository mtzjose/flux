package com.magma.flux.service.impl;

import com.magma.flux.domain.CompanyPosition;
import com.magma.flux.repository.CompanyPositionRepository;
import com.magma.flux.service.CompanyPositionService;
import com.magma.flux.service.dto.CompanyPositionDTO;
import com.magma.flux.service.mapper.CompanyPositionMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyPosition}.
 */
@Service
@Transactional
public class CompanyPositionServiceImpl implements CompanyPositionService {

    private final Logger log = LoggerFactory.getLogger(CompanyPositionServiceImpl.class);

    private final CompanyPositionRepository companyPositionRepository;

    private final CompanyPositionMapper companyPositionMapper;

    public CompanyPositionServiceImpl(CompanyPositionRepository companyPositionRepository, CompanyPositionMapper companyPositionMapper) {
        this.companyPositionRepository = companyPositionRepository;
        this.companyPositionMapper = companyPositionMapper;
    }

    @Override
    public CompanyPositionDTO save(CompanyPositionDTO companyPositionDTO) {
        log.debug("Request to save CompanyPosition : {}", companyPositionDTO);
        CompanyPosition companyPosition = companyPositionMapper.toEntity(companyPositionDTO);
        companyPosition = companyPositionRepository.save(companyPosition);
        return companyPositionMapper.toDto(companyPosition);
    }

    @Override
    public Optional<CompanyPositionDTO> partialUpdate(CompanyPositionDTO companyPositionDTO) {
        log.debug("Request to partially update CompanyPosition : {}", companyPositionDTO);

        return companyPositionRepository
            .findById(companyPositionDTO.getId())
            .map(existingCompanyPosition -> {
                companyPositionMapper.partialUpdate(existingCompanyPosition, companyPositionDTO);

                return existingCompanyPosition;
            })
            .map(companyPositionRepository::save)
            .map(companyPositionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyPositionDTO> findAll() {
        log.debug("Request to get all CompanyPositions");
        return companyPositionRepository
            .findAll()
            .stream()
            .map(companyPositionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyPositionDTO> findOne(Long id) {
        log.debug("Request to get CompanyPosition : {}", id);
        return companyPositionRepository.findById(id).map(companyPositionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyPosition : {}", id);
        companyPositionRepository.deleteById(id);
    }
}
