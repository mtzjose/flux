package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.ProcessStage;
import com.magma.flux.repository.ProcessStageRepository;
import com.magma.flux.service.dto.ProcessStageDTO;
import com.magma.flux.service.mapper.ProcessStageMapper;
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
 * Integration tests for the {@link ProcessStageResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcessStageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/process-stages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcessStageRepository processStageRepository;

    @Autowired
    private ProcessStageMapper processStageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessStageMockMvc;

    private ProcessStage processStage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessStage createEntity(EntityManager em) {
        ProcessStage processStage = new ProcessStage().name(DEFAULT_NAME);
        return processStage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcessStage createUpdatedEntity(EntityManager em) {
        ProcessStage processStage = new ProcessStage().name(UPDATED_NAME);
        return processStage;
    }

    @BeforeEach
    public void initTest() {
        processStage = createEntity(em);
    }

    @Test
    @Transactional
    void createProcessStage() throws Exception {
        int databaseSizeBeforeCreate = processStageRepository.findAll().size();
        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);
        restProcessStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeCreate + 1);
        ProcessStage testProcessStage = processStageList.get(processStageList.size() - 1);
        assertThat(testProcessStage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createProcessStageWithExistingId() throws Exception {
        // Create the ProcessStage with an existing ID
        processStage.setId(1L);
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        int databaseSizeBeforeCreate = processStageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = processStageRepository.findAll().size();
        // set the field null
        processStage.setName(null);

        // Create the ProcessStage, which fails.
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        restProcessStageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcessStages() throws Exception {
        // Initialize the database
        processStageRepository.saveAndFlush(processStage);

        // Get all the processStageList
        restProcessStageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(processStage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getProcessStage() throws Exception {
        // Initialize the database
        processStageRepository.saveAndFlush(processStage);

        // Get the processStage
        restProcessStageMockMvc
            .perform(get(ENTITY_API_URL_ID, processStage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(processStage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingProcessStage() throws Exception {
        // Get the processStage
        restProcessStageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcessStage() throws Exception {
        // Initialize the database
        processStageRepository.saveAndFlush(processStage);

        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();

        // Update the processStage
        ProcessStage updatedProcessStage = processStageRepository.findById(processStage.getId()).get();
        // Disconnect from session so that the updates on updatedProcessStage are not directly saved in db
        em.detach(updatedProcessStage);
        updatedProcessStage.name(UPDATED_NAME);
        ProcessStageDTO processStageDTO = processStageMapper.toDto(updatedProcessStage);

        restProcessStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processStageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
        ProcessStage testProcessStage = processStageList.get(processStageList.size() - 1);
        assertThat(testProcessStage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingProcessStage() throws Exception {
        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();
        processStage.setId(count.incrementAndGet());

        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, processStageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcessStage() throws Exception {
        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();
        processStage.setId(count.incrementAndGet());

        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcessStage() throws Exception {
        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();
        processStage.setId(count.incrementAndGet());

        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessStageWithPatch() throws Exception {
        // Initialize the database
        processStageRepository.saveAndFlush(processStage);

        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();

        // Update the processStage using partial update
        ProcessStage partialUpdatedProcessStage = new ProcessStage();
        partialUpdatedProcessStage.setId(processStage.getId());

        restProcessStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessStage))
            )
            .andExpect(status().isOk());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
        ProcessStage testProcessStage = processStageList.get(processStageList.size() - 1);
        assertThat(testProcessStage.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateProcessStageWithPatch() throws Exception {
        // Initialize the database
        processStageRepository.saveAndFlush(processStage);

        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();

        // Update the processStage using partial update
        ProcessStage partialUpdatedProcessStage = new ProcessStage();
        partialUpdatedProcessStage.setId(processStage.getId());

        partialUpdatedProcessStage.name(UPDATED_NAME);

        restProcessStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcessStage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcessStage))
            )
            .andExpect(status().isOk());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
        ProcessStage testProcessStage = processStageList.get(processStageList.size() - 1);
        assertThat(testProcessStage.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingProcessStage() throws Exception {
        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();
        processStage.setId(count.incrementAndGet());

        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, processStageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcessStage() throws Exception {
        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();
        processStage.setId(count.incrementAndGet());

        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcessStage() throws Exception {
        int databaseSizeBeforeUpdate = processStageRepository.findAll().size();
        processStage.setId(count.incrementAndGet());

        // Create the ProcessStage
        ProcessStageDTO processStageDTO = processStageMapper.toDto(processStage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessStageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(processStageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcessStage in the database
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcessStage() throws Exception {
        // Initialize the database
        processStageRepository.saveAndFlush(processStage);

        int databaseSizeBeforeDelete = processStageRepository.findAll().size();

        // Delete the processStage
        restProcessStageMockMvc
            .perform(delete(ENTITY_API_URL_ID, processStage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcessStage> processStageList = processStageRepository.findAll();
        assertThat(processStageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
