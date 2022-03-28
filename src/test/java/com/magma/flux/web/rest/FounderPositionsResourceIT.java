package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.FounderPositions;
import com.magma.flux.repository.FounderPositionsRepository;
import com.magma.flux.service.dto.FounderPositionsDTO;
import com.magma.flux.service.mapper.FounderPositionsMapper;
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
 * Integration tests for the {@link FounderPositionsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FounderPositionsResourceIT {

    private static final Long DEFAULT_POSITION_ID = 1L;
    private static final Long UPDATED_POSITION_ID = 2L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/founder-positions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FounderPositionsRepository founderPositionsRepository;

    @Autowired
    private FounderPositionsMapper founderPositionsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFounderPositionsMockMvc;

    private FounderPositions founderPositions;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FounderPositions createEntity(EntityManager em) {
        FounderPositions founderPositions = new FounderPositions().positionId(DEFAULT_POSITION_ID).companyId(DEFAULT_COMPANY_ID);
        return founderPositions;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FounderPositions createUpdatedEntity(EntityManager em) {
        FounderPositions founderPositions = new FounderPositions().positionId(UPDATED_POSITION_ID).companyId(UPDATED_COMPANY_ID);
        return founderPositions;
    }

    @BeforeEach
    public void initTest() {
        founderPositions = createEntity(em);
    }

    @Test
    @Transactional
    void createFounderPositions() throws Exception {
        int databaseSizeBeforeCreate = founderPositionsRepository.findAll().size();
        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);
        restFounderPositionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeCreate + 1);
        FounderPositions testFounderPositions = founderPositionsList.get(founderPositionsList.size() - 1);
        assertThat(testFounderPositions.getPositionId()).isEqualTo(DEFAULT_POSITION_ID);
        assertThat(testFounderPositions.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
    }

    @Test
    @Transactional
    void createFounderPositionsWithExistingId() throws Exception {
        // Create the FounderPositions with an existing ID
        founderPositions.setId(1L);
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        int databaseSizeBeforeCreate = founderPositionsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFounderPositionsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFounderPositions() throws Exception {
        // Initialize the database
        founderPositionsRepository.saveAndFlush(founderPositions);

        // Get all the founderPositionsList
        restFounderPositionsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(founderPositions.getId().intValue())))
            .andExpect(jsonPath("$.[*].positionId").value(hasItem(DEFAULT_POSITION_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getFounderPositions() throws Exception {
        // Initialize the database
        founderPositionsRepository.saveAndFlush(founderPositions);

        // Get the founderPositions
        restFounderPositionsMockMvc
            .perform(get(ENTITY_API_URL_ID, founderPositions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(founderPositions.getId().intValue()))
            .andExpect(jsonPath("$.positionId").value(DEFAULT_POSITION_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFounderPositions() throws Exception {
        // Get the founderPositions
        restFounderPositionsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFounderPositions() throws Exception {
        // Initialize the database
        founderPositionsRepository.saveAndFlush(founderPositions);

        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();

        // Update the founderPositions
        FounderPositions updatedFounderPositions = founderPositionsRepository.findById(founderPositions.getId()).get();
        // Disconnect from session so that the updates on updatedFounderPositions are not directly saved in db
        em.detach(updatedFounderPositions);
        updatedFounderPositions.positionId(UPDATED_POSITION_ID).companyId(UPDATED_COMPANY_ID);
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(updatedFounderPositions);

        restFounderPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, founderPositionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isOk());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
        FounderPositions testFounderPositions = founderPositionsList.get(founderPositionsList.size() - 1);
        assertThat(testFounderPositions.getPositionId()).isEqualTo(UPDATED_POSITION_ID);
        assertThat(testFounderPositions.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void putNonExistingFounderPositions() throws Exception {
        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();
        founderPositions.setId(count.incrementAndGet());

        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFounderPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, founderPositionsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFounderPositions() throws Exception {
        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();
        founderPositions.setId(count.incrementAndGet());

        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderPositionsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFounderPositions() throws Exception {
        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();
        founderPositions.setId(count.incrementAndGet());

        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderPositionsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFounderPositionsWithPatch() throws Exception {
        // Initialize the database
        founderPositionsRepository.saveAndFlush(founderPositions);

        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();

        // Update the founderPositions using partial update
        FounderPositions partialUpdatedFounderPositions = new FounderPositions();
        partialUpdatedFounderPositions.setId(founderPositions.getId());

        partialUpdatedFounderPositions.positionId(UPDATED_POSITION_ID).companyId(UPDATED_COMPANY_ID);

        restFounderPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFounderPositions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFounderPositions))
            )
            .andExpect(status().isOk());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
        FounderPositions testFounderPositions = founderPositionsList.get(founderPositionsList.size() - 1);
        assertThat(testFounderPositions.getPositionId()).isEqualTo(UPDATED_POSITION_ID);
        assertThat(testFounderPositions.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void fullUpdateFounderPositionsWithPatch() throws Exception {
        // Initialize the database
        founderPositionsRepository.saveAndFlush(founderPositions);

        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();

        // Update the founderPositions using partial update
        FounderPositions partialUpdatedFounderPositions = new FounderPositions();
        partialUpdatedFounderPositions.setId(founderPositions.getId());

        partialUpdatedFounderPositions.positionId(UPDATED_POSITION_ID).companyId(UPDATED_COMPANY_ID);

        restFounderPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFounderPositions.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFounderPositions))
            )
            .andExpect(status().isOk());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
        FounderPositions testFounderPositions = founderPositionsList.get(founderPositionsList.size() - 1);
        assertThat(testFounderPositions.getPositionId()).isEqualTo(UPDATED_POSITION_ID);
        assertThat(testFounderPositions.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingFounderPositions() throws Exception {
        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();
        founderPositions.setId(count.incrementAndGet());

        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFounderPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, founderPositionsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFounderPositions() throws Exception {
        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();
        founderPositions.setId(count.incrementAndGet());

        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFounderPositions() throws Exception {
        int databaseSizeBeforeUpdate = founderPositionsRepository.findAll().size();
        founderPositions.setId(count.incrementAndGet());

        // Create the FounderPositions
        FounderPositionsDTO founderPositionsDTO = founderPositionsMapper.toDto(founderPositions);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderPositionsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(founderPositionsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FounderPositions in the database
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFounderPositions() throws Exception {
        // Initialize the database
        founderPositionsRepository.saveAndFlush(founderPositions);

        int databaseSizeBeforeDelete = founderPositionsRepository.findAll().size();

        // Delete the founderPositions
        restFounderPositionsMockMvc
            .perform(delete(ENTITY_API_URL_ID, founderPositions.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FounderPositions> founderPositionsList = founderPositionsRepository.findAll();
        assertThat(founderPositionsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
