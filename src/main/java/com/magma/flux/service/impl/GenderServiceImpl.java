package com.magma.flux.service.impl;

import com.magma.flux.domain.Gender;
import com.magma.flux.repository.GenderRepository;
import com.magma.flux.service.GenderService;
import com.magma.flux.service.dto.GenderDTO;
import com.magma.flux.service.mapper.GenderMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Gender}.
 */
@Service
@Transactional
public class GenderServiceImpl implements GenderService {

    private final Logger log = LoggerFactory.getLogger(GenderServiceImpl.class);

    private final GenderRepository genderRepository;

    private final GenderMapper genderMapper;

    public GenderServiceImpl(GenderRepository genderRepository, GenderMapper genderMapper) {
        this.genderRepository = genderRepository;
        this.genderMapper = genderMapper;
    }

    @Override
    public GenderDTO save(GenderDTO genderDTO) {
        log.debug("Request to save Gender : {}", genderDTO);
        Gender gender = genderMapper.toEntity(genderDTO);
        gender = genderRepository.save(gender);
        return genderMapper.toDto(gender);
    }

    @Override
    public Optional<GenderDTO> partialUpdate(GenderDTO genderDTO) {
        log.debug("Request to partially update Gender : {}", genderDTO);

        return genderRepository
            .findById(genderDTO.getId())
            .map(existingGender -> {
                genderMapper.partialUpdate(existingGender, genderDTO);

                return existingGender;
            })
            .map(genderRepository::save)
            .map(genderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GenderDTO> findAll() {
        log.debug("Request to get all Genders");
        return genderRepository.findAll().stream().map(genderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GenderDTO> findOne(Long id) {
        log.debug("Request to get Gender : {}", id);
        return genderRepository.findById(id).map(genderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Gender : {}", id);
        genderRepository.deleteById(id);
    }
}
