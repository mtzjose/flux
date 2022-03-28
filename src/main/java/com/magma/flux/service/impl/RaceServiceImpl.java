package com.magma.flux.service.impl;

import com.magma.flux.domain.Race;
import com.magma.flux.repository.RaceRepository;
import com.magma.flux.service.RaceService;
import com.magma.flux.service.dto.RaceDTO;
import com.magma.flux.service.mapper.RaceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Race}.
 */
@Service
@Transactional
public class RaceServiceImpl implements RaceService {

    private final Logger log = LoggerFactory.getLogger(RaceServiceImpl.class);

    private final RaceRepository raceRepository;

    private final RaceMapper raceMapper;

    public RaceServiceImpl(RaceRepository raceRepository, RaceMapper raceMapper) {
        this.raceRepository = raceRepository;
        this.raceMapper = raceMapper;
    }

    @Override
    public RaceDTO save(RaceDTO raceDTO) {
        log.debug("Request to save Race : {}", raceDTO);
        Race race = raceMapper.toEntity(raceDTO);
        race = raceRepository.save(race);
        return raceMapper.toDto(race);
    }

    @Override
    public Optional<RaceDTO> partialUpdate(RaceDTO raceDTO) {
        log.debug("Request to partially update Race : {}", raceDTO);

        return raceRepository
            .findById(raceDTO.getId())
            .map(existingRace -> {
                raceMapper.partialUpdate(existingRace, raceDTO);

                return existingRace;
            })
            .map(raceRepository::save)
            .map(raceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RaceDTO> findAll() {
        log.debug("Request to get all Races");
        return raceRepository.findAll().stream().map(raceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RaceDTO> findOne(Long id) {
        log.debug("Request to get Race : {}", id);
        return raceRepository.findById(id).map(raceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Race : {}", id);
        raceRepository.deleteById(id);
    }
}
