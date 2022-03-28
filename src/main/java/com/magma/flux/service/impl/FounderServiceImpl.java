package com.magma.flux.service.impl;

import com.magma.flux.domain.Founder;
import com.magma.flux.repository.FounderRepository;
import com.magma.flux.service.FounderService;
import com.magma.flux.service.dto.FounderDTO;
import com.magma.flux.service.mapper.FounderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Founder}.
 */
@Service
@Transactional
public class FounderServiceImpl implements FounderService {

    private final Logger log = LoggerFactory.getLogger(FounderServiceImpl.class);

    private final FounderRepository founderRepository;

    private final FounderMapper founderMapper;

    public FounderServiceImpl(FounderRepository founderRepository, FounderMapper founderMapper) {
        this.founderRepository = founderRepository;
        this.founderMapper = founderMapper;
    }

    @Override
    public FounderDTO save(FounderDTO founderDTO) {
        log.debug("Request to save Founder : {}", founderDTO);
        Founder founder = founderMapper.toEntity(founderDTO);
        founder = founderRepository.save(founder);
        return founderMapper.toDto(founder);
    }

    @Override
    public Optional<FounderDTO> partialUpdate(FounderDTO founderDTO) {
        log.debug("Request to partially update Founder : {}", founderDTO);

        return founderRepository
            .findById(founderDTO.getId())
            .map(existingFounder -> {
                founderMapper.partialUpdate(existingFounder, founderDTO);

                return existingFounder;
            })
            .map(founderRepository::save)
            .map(founderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FounderDTO> findAll() {
        log.debug("Request to get all Founders");
        return founderRepository.findAll().stream().map(founderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FounderDTO> findOne(Long id) {
        log.debug("Request to get Founder : {}", id);
        return founderRepository.findById(id).map(founderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Founder : {}", id);
        founderRepository.deleteById(id);
    }
}
