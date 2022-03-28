package com.magma.flux.service.impl;

import com.magma.flux.domain.Pronoun;
import com.magma.flux.repository.PronounRepository;
import com.magma.flux.service.PronounService;
import com.magma.flux.service.dto.PronounDTO;
import com.magma.flux.service.mapper.PronounMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pronoun}.
 */
@Service
@Transactional
public class PronounServiceImpl implements PronounService {

    private final Logger log = LoggerFactory.getLogger(PronounServiceImpl.class);

    private final PronounRepository pronounRepository;

    private final PronounMapper pronounMapper;

    public PronounServiceImpl(PronounRepository pronounRepository, PronounMapper pronounMapper) {
        this.pronounRepository = pronounRepository;
        this.pronounMapper = pronounMapper;
    }

    @Override
    public PronounDTO save(PronounDTO pronounDTO) {
        log.debug("Request to save Pronoun : {}", pronounDTO);
        Pronoun pronoun = pronounMapper.toEntity(pronounDTO);
        pronoun = pronounRepository.save(pronoun);
        return pronounMapper.toDto(pronoun);
    }

    @Override
    public Optional<PronounDTO> partialUpdate(PronounDTO pronounDTO) {
        log.debug("Request to partially update Pronoun : {}", pronounDTO);

        return pronounRepository
            .findById(pronounDTO.getId())
            .map(existingPronoun -> {
                pronounMapper.partialUpdate(existingPronoun, pronounDTO);

                return existingPronoun;
            })
            .map(pronounRepository::save)
            .map(pronounMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PronounDTO> findAll() {
        log.debug("Request to get all Pronouns");
        return pronounRepository.findAll().stream().map(pronounMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PronounDTO> findOne(Long id) {
        log.debug("Request to get Pronoun : {}", id);
        return pronounRepository.findById(id).map(pronounMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pronoun : {}", id);
        pronounRepository.deleteById(id);
    }
}
