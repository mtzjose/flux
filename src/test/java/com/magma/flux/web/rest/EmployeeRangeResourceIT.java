package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.EmployeeRange;
import com.magma.flux.repository.EmployeeRangeRepository;
import com.magma.flux.service.dto.EmployeeRangeDTO;
import com.magma.flux.service.mapper.EmployeeRangeMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmployeeRangeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeRangeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-ranges";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRangeRepository employeeRangeRepository;

    @Autowired
    private EmployeeRangeMapper employeeRangeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeRangeMockMvc;

    private EmployeeRange employeeRange;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRange createEntity(EntityManager em) {
        EmployeeRange employeeRange = new EmployeeRange().name(DEFAULT_NAME);
        return employeeRange;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRange createUpdatedEntity(EntityManager em) {
        EmployeeRange employeeRange = new EmployeeRange().name(UPDATED_NAME);
        return employeeRange;
    }

    @BeforeEach
    public void initTest() {
        employeeRange = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeRange() throws Exception {
        int databaseSizeBeforeCreate = employeeRangeRepository.findAll().size();
        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);
        restEmployeeRangeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeRange testEmployeeRange = employeeRangeList.get(employeeRangeList.size() - 1);
        assertThat(testEmployeeRange.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createEmployeeRangeWithExistingId() throws Exception {
        // Create the EmployeeRange with an existing ID
        employeeRange.setId(1L);
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        int databaseSizeBeforeCreate = employeeRangeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeRangeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRangeRepository.findAll().size();
        // set the field null
        employeeRange.setName(null);

        // Create the EmployeeRange, which fails.
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        restEmployeeRangeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeRanges() throws Exception {
        // Initialize the database
        employeeRangeRepository.saveAndFlush(employeeRange);

        // Get all the employeeRangeList
        restEmployeeRangeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRange.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getEmployeeRange() throws Exception {
        // Initialize the database
        employeeRangeRepository.saveAndFlush(employeeRange);

        // Get the employeeRange
        restEmployeeRangeMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeRange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeRange.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeRange() throws Exception {
        // Get the employeeRange
        restEmployeeRangeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEmployeeRange() throws Exception {
        // Initialize the database
        employeeRangeRepository.saveAndFlush(employeeRange);

        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();

        // Update the employeeRange
        EmployeeRange updatedEmployeeRange = employeeRangeRepository.findById(employeeRange.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeRange are not directly saved in db
        em.detach(updatedEmployeeRange);
        updatedEmployeeRange.name(UPDATED_NAME);
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(updatedEmployeeRange);

        restEmployeeRangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeRangeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRange testEmployeeRange = employeeRangeList.get(employeeRangeList.size() - 1);
        assertThat(testEmployeeRange.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeRange() throws Exception {
        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();
        employeeRange.setId(count.incrementAndGet());

        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeRangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeRangeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeRange() throws Exception {
        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();
        employeeRange.setId(count.incrementAndGet());

        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRangeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeRange() throws Exception {
        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();
        employeeRange.setId(count.incrementAndGet());

        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRangeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeRangeWithPatch() throws Exception {
        // Initialize the database
        employeeRangeRepository.saveAndFlush(employeeRange);

        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();

        // Update the employeeRange using partial update
        EmployeeRange partialUpdatedEmployeeRange = new EmployeeRange();
        partialUpdatedEmployeeRange.setId(employeeRange.getId());

        restEmployeeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeRange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeRange))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRange testEmployeeRange = employeeRangeList.get(employeeRangeList.size() - 1);
        assertThat(testEmployeeRange.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeRangeWithPatch() throws Exception {
        // Initialize the database
        employeeRangeRepository.saveAndFlush(employeeRange);

        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();

        // Update the employeeRange using partial update
        EmployeeRange partialUpdatedEmployeeRange = new EmployeeRange();
        partialUpdatedEmployeeRange.setId(employeeRange.getId());

        partialUpdatedEmployeeRange.name(UPDATED_NAME);

        restEmployeeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeRange.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeRange))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRange testEmployeeRange = employeeRangeList.get(employeeRangeList.size() - 1);
        assertThat(testEmployeeRange.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeRange() throws Exception {
        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();
        employeeRange.setId(count.incrementAndGet());

        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeRangeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeRange() throws Exception {
        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();
        employeeRange.setId(count.incrementAndGet());

        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeRange() throws Exception {
        int databaseSizeBeforeUpdate = employeeRangeRepository.findAll().size();
        employeeRange.setId(count.incrementAndGet());

        // Create the EmployeeRange
        EmployeeRangeDTO employeeRangeDTO = employeeRangeMapper.toDto(employeeRange);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeRangeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeRangeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeRange in the database
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeRange() throws Exception {
        // Initialize the database
        employeeRangeRepository.saveAndFlush(employeeRange);

        int databaseSizeBeforeDelete = employeeRangeRepository.findAll().size();

        // Delete the employeeRange
        restEmployeeRangeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeRange.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeRange> employeeRangeList = employeeRangeRepository.findAll();
        assertThat(employeeRangeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
