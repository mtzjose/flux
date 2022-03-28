package com.magma.flux.service.impl;

import com.magma.flux.domain.CompanyCategories;
import com.magma.flux.repository.CompanyCategoriesRepository;
import com.magma.flux.service.CompanyCategoriesService;
import com.magma.flux.service.dto.CompanyCategoriesDTO;
import com.magma.flux.service.mapper.CompanyCategoriesMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CompanyCategories}.
 */
@Service
@Transactional
public class CompanyCategoriesServiceImpl implements CompanyCategoriesService {

    private final Logger log = LoggerFactory.getLogger(CompanyCategoriesServiceImpl.class);

    private final CompanyCategoriesRepository companyCategoriesRepository;

    private final CompanyCategoriesMapper companyCategoriesMapper;

    public CompanyCategoriesServiceImpl(
        CompanyCategoriesRepository companyCategoriesRepository,
        CompanyCategoriesMapper companyCategoriesMapper
    ) {
        this.companyCategoriesRepository = companyCategoriesRepository;
        this.companyCategoriesMapper = companyCategoriesMapper;
    }

    @Override
    public CompanyCategoriesDTO save(CompanyCategoriesDTO companyCategoriesDTO) {
        log.debug("Request to save CompanyCategories : {}", companyCategoriesDTO);
        CompanyCategories companyCategories = companyCategoriesMapper.toEntity(companyCategoriesDTO);
        companyCategories = companyCategoriesRepository.save(companyCategories);
        return companyCategoriesMapper.toDto(companyCategories);
    }

    @Override
    public Optional<CompanyCategoriesDTO> partialUpdate(CompanyCategoriesDTO companyCategoriesDTO) {
        log.debug("Request to partially update CompanyCategories : {}", companyCategoriesDTO);

        return companyCategoriesRepository
            .findById(companyCategoriesDTO.getId())
            .map(existingCompanyCategories -> {
                companyCategoriesMapper.partialUpdate(existingCompanyCategories, companyCategoriesDTO);

                return existingCompanyCategories;
            })
            .map(companyCategoriesRepository::save)
            .map(companyCategoriesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CompanyCategoriesDTO> findAll() {
        log.debug("Request to get all CompanyCategories");
        return companyCategoriesRepository
            .findAll()
            .stream()
            .map(companyCategoriesMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyCategoriesDTO> findOne(Long id) {
        log.debug("Request to get CompanyCategories : {}", id);
        return companyCategoriesRepository.findById(id).map(companyCategoriesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyCategories : {}", id);
        companyCategoriesRepository.deleteById(id);
    }
}
