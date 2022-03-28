package com.magma.flux.service.impl;

import com.magma.flux.domain.ContactSource;
import com.magma.flux.repository.ContactSourceRepository;
import com.magma.flux.service.ContactSourceService;
import com.magma.flux.service.dto.ContactSourceDTO;
import com.magma.flux.service.mapper.ContactSourceMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ContactSource}.
 */
@Service
@Transactional
public class ContactSourceServiceImpl implements ContactSourceService {

    private final Logger log = LoggerFactory.getLogger(ContactSourceServiceImpl.class);

    private final ContactSourceRepository contactSourceRepository;

    private final ContactSourceMapper contactSourceMapper;

    public ContactSourceServiceImpl(ContactSourceRepository contactSourceRepository, ContactSourceMapper contactSourceMapper) {
        this.contactSourceRepository = contactSourceRepository;
        this.contactSourceMapper = contactSourceMapper;
    }

    @Override
    public ContactSourceDTO save(ContactSourceDTO contactSourceDTO) {
        log.debug("Request to save ContactSource : {}", contactSourceDTO);
        ContactSource contactSource = contactSourceMapper.toEntity(contactSourceDTO);
        contactSource = contactSourceRepository.save(contactSource);
        return contactSourceMapper.toDto(contactSource);
    }

    @Override
    public Optional<ContactSourceDTO> partialUpdate(ContactSourceDTO contactSourceDTO) {
        log.debug("Request to partially update ContactSource : {}", contactSourceDTO);

        return contactSourceRepository
            .findById(contactSourceDTO.getId())
            .map(existingContactSource -> {
                contactSourceMapper.partialUpdate(existingContactSource, contactSourceDTO);

                return existingContactSource;
            })
            .map(contactSourceRepository::save)
            .map(contactSourceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactSourceDTO> findAll() {
        log.debug("Request to get all ContactSources");
        return contactSourceRepository.findAll().stream().map(contactSourceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactSourceDTO> findOne(Long id) {
        log.debug("Request to get ContactSource : {}", id);
        return contactSourceRepository.findById(id).map(contactSourceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactSource : {}", id);
        contactSourceRepository.deleteById(id);
    }
}
