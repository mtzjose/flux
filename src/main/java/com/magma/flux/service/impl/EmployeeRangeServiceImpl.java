package com.magma.flux.service.impl;

import com.magma.flux.domain.EmployeeRange;
import com.magma.flux.repository.EmployeeRangeRepository;
import com.magma.flux.service.EmployeeRangeService;
import com.magma.flux.service.dto.EmployeeRangeDTO;
import com.magma.flux.service.mapper.EmployeeRangeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeRange}.
 */
@Service
@Transactional
public class EmployeeRangeServiceImpl implements EmployeeRangeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeRangeServiceImpl.class);

    private final EmployeeRangeRepository employeeRangeRepository;

    private final EmployeeRangeMapper employeeRangeMapper;

    public EmployeeRangeServiceImpl(EmployeeRangeRepository employeeRangeRepository, EmployeeRangeMapper employeeRangeMapper) {
        this.employeeRangeRepository = employeeRangeRepository;
        this.employeeRangeMapper = employeeRangeMapper;
    }

    @Override
    public EmployeeRangeDTO save(EmployeeRangeDTO employeeRangeDTO) {
        log.debug("Request to save EmployeeRange : {}", employeeRangeDTO);
        EmployeeRange employeeRange = employeeRangeMapper.toEntity(employeeRangeDTO);
        employeeRange = employeeRangeRepository.save(employeeRange);
        return employeeRangeMapper.toDto(employeeRange);
    }

    @Override
    public Optional<EmployeeRangeDTO> partialUpdate(EmployeeRangeDTO employeeRangeDTO) {
        log.debug("Request to partially update EmployeeRange : {}", employeeRangeDTO);

        return employeeRangeRepository
            .findById(employeeRangeDTO.getId())
            .map(existingEmployeeRange -> {
                employeeRangeMapper.partialUpdate(existingEmployeeRange, employeeRangeDTO);

                return existingEmployeeRange;
            })
            .map(employeeRangeRepository::save)
            .map(employeeRangeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeRangeDTO> findAll() {
        log.debug("Request to get all EmployeeRanges");
        return employeeRangeRepository.findAll().stream().map(employeeRangeMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeRangeDTO> findOne(Long id) {
        log.debug("Request to get EmployeeRange : {}", id);
        return employeeRangeRepository.findById(id).map(employeeRangeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeRange : {}", id);
        employeeRangeRepository.deleteById(id);
    }
}
