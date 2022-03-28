package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.CompanyPosition;
import com.magma.flux.repository.CompanyPositionRepository;
import com.magma.flux.service.dto.CompanyPositionDTO;
import com.magma.flux.service.mapper.CompanyPositionMapper;
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
 * Integration tests for the {@link CompanyPositionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyPositionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyPositionRepository companyPositionRepository;

    @Autowired
    private CompanyPositionMapper companyPositionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyPositionMockMvc;

    private CompanyPosition companyPosition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyPosition createEntity(EntityManager em) {
        CompanyPosition companyPosition = new CompanyPosition().name(DEFAULT_NAME);
        return companyPosition;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyPosition createUpdatedEntity(EntityManager em) {
        CompanyPosition companyPosition = new CompanyPosition().name(UPDATED_NAME);
        return companyPosition;
    }

    @BeforeEach
    public void initTest() {
        companyPosition = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyPosition() throws Exception {
        int databaseSizeBeforeCreate = companyPositionRepository.findAll().size();
        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);
        restCompanyPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyPosition testCompanyPosition = companyPositionList.get(companyPositionList.size() - 1);
        assertThat(testCompanyPosition.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCompanyPositionWithExistingId() throws Exception {
        // Create the CompanyPosition with an existing ID
        companyPosition.setId(1L);
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        int databaseSizeBeforeCreate = companyPositionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyPositionRepository.findAll().size();
        // set the field null
        companyPosition.setName(null);

        // Create the CompanyPosition, which fails.
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        restCompanyPositionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyPositions() throws Exception {
        // Initialize the database
        companyPositionRepository.saveAndFlush(companyPosition);

        // Get all the companyPositionList
        restCompanyPositionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyPosition.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCompanyPosition() throws Exception {
        // Initialize the database
        companyPositionRepository.saveAndFlush(companyPosition);

        // Get the companyPosition
        restCompanyPositionMockMvc
            .perform(get(ENTITY_API_URL_ID, companyPosition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyPosition.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCompanyPosition() throws Exception {
        // Get the companyPosition
        restCompanyPositionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyPosition() throws Exception {
        // Initialize the database
        companyPositionRepository.saveAndFlush(companyPosition);

        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();

        // Update the companyPosition
        CompanyPosition updatedCompanyPosition = companyPositionRepository.findById(companyPosition.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyPosition are not directly saved in db
        em.detach(updatedCompanyPosition);
        updatedCompanyPosition.name(UPDATED_NAME);
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(updatedCompanyPosition);

        restCompanyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
        CompanyPosition testCompanyPosition = companyPositionList.get(companyPositionList.size() - 1);
        assertThat(testCompanyPosition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCompanyPosition() throws Exception {
        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();
        companyPosition.setId(count.incrementAndGet());

        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyPositionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyPosition() throws Exception {
        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();
        companyPosition.setId(count.incrementAndGet());

        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyPositionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyPosition() throws Exception {
        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();
        companyPosition.setId(count.incrementAndGet());

        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyPositionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyPositionWithPatch() throws Exception {
        // Initialize the database
        companyPositionRepository.saveAndFlush(companyPosition);

        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();

        // Update the companyPosition using partial update
        CompanyPosition partialUpdatedCompanyPosition = new CompanyPosition();
        partialUpdatedCompanyPosition.setId(companyPosition.getId());

        partialUpdatedCompanyPosition.name(UPDATED_NAME);

        restCompanyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyPosition))
            )
            .andExpect(status().isOk());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
        CompanyPosition testCompanyPosition = companyPositionList.get(companyPositionList.size() - 1);
        assertThat(testCompanyPosition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCompanyPositionWithPatch() throws Exception {
        // Initialize the database
        companyPositionRepository.saveAndFlush(companyPosition);

        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();

        // Update the companyPosition using partial update
        CompanyPosition partialUpdatedCompanyPosition = new CompanyPosition();
        partialUpdatedCompanyPosition.setId(companyPosition.getId());

        partialUpdatedCompanyPosition.name(UPDATED_NAME);

        restCompanyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyPosition.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyPosition))
            )
            .andExpect(status().isOk());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
        CompanyPosition testCompanyPosition = companyPositionList.get(companyPositionList.size() - 1);
        assertThat(testCompanyPosition.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyPosition() throws Exception {
        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();
        companyPosition.setId(count.incrementAndGet());

        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyPositionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyPosition() throws Exception {
        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();
        companyPosition.setId(count.incrementAndGet());

        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyPosition() throws Exception {
        int databaseSizeBeforeUpdate = companyPositionRepository.findAll().size();
        companyPosition.setId(count.incrementAndGet());

        // Create the CompanyPosition
        CompanyPositionDTO companyPositionDTO = companyPositionMapper.toDto(companyPosition);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyPositionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyPositionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyPosition in the database
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyPosition() throws Exception {
        // Initialize the database
        companyPositionRepository.saveAndFlush(companyPosition);

        int databaseSizeBeforeDelete = companyPositionRepository.findAll().size();

        // Delete the companyPosition
        restCompanyPositionMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyPosition.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyPosition> companyPositionList = companyPositionRepository.findAll();
        assertThat(companyPositionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
