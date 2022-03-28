package com.magma.flux.service.impl;

import com.magma.flux.domain.School;
import com.magma.flux.repository.SchoolRepository;
import com.magma.flux.service.SchoolService;
import com.magma.flux.service.dto.SchoolDTO;
import com.magma.flux.service.mapper.SchoolMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link School}.
 */
@Service
@Transactional
public class SchoolServiceImpl implements SchoolService {

    private final Logger log = LoggerFactory.getLogger(SchoolServiceImpl.class);

    private final SchoolRepository schoolRepository;

    private final SchoolMapper schoolMapper;

    public SchoolServiceImpl(SchoolRepository schoolRepository, SchoolMapper schoolMapper) {
        this.schoolRepository = schoolRepository;
        this.schoolMapper = schoolMapper;
    }

    @Override
    public SchoolDTO save(SchoolDTO schoolDTO) {
        log.debug("Request to save School : {}", schoolDTO);
        School school = schoolMapper.toEntity(schoolDTO);
        school = schoolRepository.save(school);
        return schoolMapper.toDto(school);
    }

    @Override
    public Optional<SchoolDTO> partialUpdate(SchoolDTO schoolDTO) {
        log.debug("Request to partially update School : {}", schoolDTO);

        return schoolRepository
            .findById(schoolDTO.getId())
            .map(existingSchool -> {
                schoolMapper.partialUpdate(existingSchool, schoolDTO);

                return existingSchool;
            })
            .map(schoolRepository::save)
            .map(schoolMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SchoolDTO> findAll() {
        log.debug("Request to get all Schools");
        return schoolRepository.findAll().stream().map(schoolMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolDTO> findOne(Long id) {
        log.debug("Request to get School : {}", id);
        return schoolRepository.findById(id).map(schoolMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete School : {}", id);
        schoolRepository.deleteById(id);
    }
}
