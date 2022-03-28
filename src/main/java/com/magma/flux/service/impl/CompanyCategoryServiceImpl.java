package com.magma.flux.service.impl;

import com.magma.flux.domain.CompanyCategory;
import com.magma.flux.repository.CompanyCategoryRepository;
import com.magma.flux.service.CompanyCategoryService;
import com.magma.flux.service.dto.CompanyCategoryDTO;
import com.magma.flux.service.mapper.CompanyCategoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyCategory}.
 */
@Service
@Transactional
public class CompanyCategoryServiceImpl implements CompanyCategoryService {

    private final Logger log = LoggerFactory.getLogger(CompanyCategoryServiceImpl.class);

    private final CompanyCategoryRepository companyCategoryRepository;

    private final CompanyCategoryMapper companyCategoryMapper;

    public CompanyCategoryServiceImpl(CompanyCategoryRepository companyCategoryRepository, CompanyCategoryMapper companyCategoryMapper) {
        this.companyCategoryRepository = companyCategoryRepository;
        this.companyCategoryMapper = companyCategoryMapper;
    }

    @Override
    public CompanyCategoryDTO save(CompanyCategoryDTO companyCategoryDTO) {
        log.debug("Request to save CompanyCategory : {}", companyCategoryDTO);
        CompanyCategory companyCategory = companyCategoryMapper.toEntity(companyCategoryDTO);
        companyCategory = companyCategoryRepository.save(companyCategory);
        return companyCategoryMapper.toDto(companyCategory);
    }

    @Override
    public Optional<CompanyCategoryDTO> partialUpdate(CompanyCategoryDTO companyCategoryDTO) {
        log.debug("Request to partially update CompanyCategory : {}", companyCategoryDTO);

        return companyCategoryRepository
            .findById(companyCategoryDTO.getId())
            .map(existingCompanyCategory -> {
                companyCategoryMapper.partialUpdate(existingCompanyCategory, companyCategoryDTO);

                return existingCompanyCategory;
            })
            .map(companyCategoryRepository::save)
            .map(companyCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyCategoryDTO> findAll() {
        log.debug("Request to get all CompanyCategories");
        return companyCategoryRepository
            .findAll()
            .stream()
            .map(companyCategoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyCategoryDTO> findOne(Long id) {
        log.debug("Request to get CompanyCategory : {}", id);
        return companyCategoryRepository.findById(id).map(companyCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyCategory : {}", id);
        companyCategoryRepository.deleteById(id);
    }
}
