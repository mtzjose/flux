package com.magma.flux.service.impl;

import com.magma.flux.domain.PersonCompanyHistory;
import com.magma.flux.repository.PersonCompanyHistoryRepository;
import com.magma.flux.service.PersonCompanyHistoryService;
import com.magma.flux.service.dto.PersonCompanyHistoryDTO;
import com.magma.flux.service.mapper.PersonCompanyHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonCompanyHistory}.
 */
@Service
@Transactional
public class PersonCompanyHistoryServiceImpl implements PersonCompanyHistoryService {

    private final Logger log = LoggerFactory.getLogger(PersonCompanyHistoryServiceImpl.class);

    private final PersonCompanyHistoryRepository personCompanyHistoryRepository;

    private final PersonCompanyHistoryMapper personCompanyHistoryMapper;

    public PersonCompanyHistoryServiceImpl(
        PersonCompanyHistoryRepository personCompanyHistoryRepository,
        PersonCompanyHistoryMapper personCompanyHistoryMapper
    ) {
        this.personCompanyHistoryRepository = personCompanyHistoryRepository;
        this.personCompanyHistoryMapper = personCompanyHistoryMapper;
    }

    @Override
    public PersonCompanyHistoryDTO save(PersonCompanyHistoryDTO personCompanyHistoryDTO) {
        log.debug("Request to save PersonCompanyHistory : {}", personCompanyHistoryDTO);
        PersonCompanyHistory personCompanyHistory = personCompanyHistoryMapper.toEntity(personCompanyHistoryDTO);
        personCompanyHistory = personCompanyHistoryRepository.save(personCompanyHistory);
        return personCompanyHistoryMapper.toDto(personCompanyHistory);
    }

    @Override
    public Optional<PersonCompanyHistoryDTO> partialUpdate(PersonCompanyHistoryDTO personCompanyHistoryDTO) {
        log.debug("Request to partially update PersonCompanyHistory : {}", personCompanyHistoryDTO);

        return personCompanyHistoryRepository
            .findById(personCompanyHistoryDTO.getId())
            .map(existingPersonCompanyHistory -> {
                personCompanyHistoryMapper.partialUpdate(existingPersonCompanyHistory, personCompanyHistoryDTO);

                return existingPersonCompanyHistory;
            })
            .map(personCompanyHistoryRepository::save)
            .map(personCompanyHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonCompanyHistoryDTO> findAll() {
        log.debug("Request to get all PersonCompanyHistories");
        return personCompanyHistoryRepository
            .findAll()
            .stream()
            .map(personCompanyHistoryMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonCompanyHistoryDTO> findOne(Long id) {
        log.debug("Request to get PersonCompanyHistory : {}", id);
        return personCompanyHistoryRepository.findById(id).map(personCompanyHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PersonCompanyHistory : {}", id);
        personCompanyHistoryRepository.deleteById(id);
    }
}
