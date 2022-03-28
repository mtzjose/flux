package com.magma.flux.service.impl;

import com.magma.flux.domain.CollegeDegree;
import com.magma.flux.repository.CollegeDegreeRepository;
import com.magma.flux.service.CollegeDegreeService;
import com.magma.flux.service.dto.CollegeDegreeDTO;
import com.magma.flux.service.mapper.CollegeDegreeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CollegeDegree}.
 */
@Service
@Transactional
public class CollegeDegreeServiceImpl implements CollegeDegreeService {

    private final Logger log = LoggerFactory.getLogger(CollegeDegreeServiceImpl.class);

    private final CollegeDegreeRepository collegeDegreeRepository;

    private final CollegeDegreeMapper collegeDegreeMapper;

    public CollegeDegreeServiceImpl(CollegeDegreeRepository collegeDegreeRepository, CollegeDegreeMapper collegeDegreeMapper) {
        this.collegeDegreeRepository = collegeDegreeRepository;
        this.collegeDegreeMapper = collegeDegreeMapper;
    }

    @Override
    public CollegeDegreeDTO save(CollegeDegreeDTO collegeDegreeDTO) {
        log.debug("Request to save CollegeDegree : {}", collegeDegreeDTO);
        CollegeDegree collegeDegree = collegeDegreeMapper.toEntity(collegeDegreeDTO);
        collegeDegree = collegeDegreeRepository.save(collegeDegree);
        return collegeDegreeMapper.toDto(collegeDegree);
    }

    @Override
    public Optional<CollegeDegreeDTO> partialUpdate(CollegeDegreeDTO collegeDegreeDTO) {
        log.debug("Request to partially update CollegeDegree : {}", collegeDegreeDTO);

        return collegeDegreeRepository
            .findById(collegeDegreeDTO.getId())
            .map(existingCollegeDegree -> {
                collegeDegreeMapper.partialUpdate(existingCollegeDegree, collegeDegreeDTO);

                return existingCollegeDegree;
            })
            .map(collegeDegreeRepository::save)
            .map(collegeDegreeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CollegeDegreeDTO> findAll() {
        log.debug("Request to get all CollegeDegrees");
        return collegeDegreeRepository.findAll().stream().map(collegeDegreeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollegeDegreeDTO> findOne(Long id) {
        log.debug("Request to get CollegeDegree : {}", id);
        return collegeDegreeRepository.findById(id).map(collegeDegreeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CollegeDegree : {}", id);
        collegeDegreeRepository.deleteById(id);
    }
}
