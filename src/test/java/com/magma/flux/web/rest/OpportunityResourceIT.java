package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.Opportunity;
import com.magma.flux.repository.OpportunityRepository;
import com.magma.flux.service.dto.OpportunityDTO;
import com.magma.flux.service.mapper.OpportunityMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link OpportunityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OpportunityResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final LocalDate DEFAULT_APPLY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_APPLY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_CONTACT_SOURCE_ID = 1L;
    private static final Long UPDATED_CONTACT_SOURCE_ID = 2L;

    private static final Long DEFAULT_PROCESS_STAGE_ID = 1L;
    private static final Long UPDATED_PROCESS_STAGE_ID = 2L;

    private static final String ENTITY_API_URL = "/api/opportunities";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OpportunityRepository opportunityRepository;

    @Autowired
    private OpportunityMapper opportunityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOpportunityMockMvc;

    private Opportunity opportunity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opportunity createEntity(EntityManager em) {
        Opportunity opportunity = new Opportunity()
            .companyId(DEFAULT_COMPANY_ID)
            .applyDate(DEFAULT_APPLY_DATE)
            .contactSourceId(DEFAULT_CONTACT_SOURCE_ID)
            .processStageId(DEFAULT_PROCESS_STAGE_ID);
        return opportunity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Opportunity createUpdatedEntity(EntityManager em) {
        Opportunity opportunity = new Opportunity()
            .companyId(UPDATED_COMPANY_ID)
            .applyDate(UPDATED_APPLY_DATE)
            .contactSourceId(UPDATED_CONTACT_SOURCE_ID)
            .processStageId(UPDATED_PROCESS_STAGE_ID);
        return opportunity;
    }

    @BeforeEach
    public void initTest() {
        opportunity = createEntity(em);
    }

    @Test
    @Transactional
    void createOpportunity() throws Exception {
        int databaseSizeBeforeCreate = opportunityRepository.findAll().size();
        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);
        restOpportunityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeCreate + 1);
        Opportunity testOpportunity = opportunityList.get(opportunityList.size() - 1);
        assertThat(testOpportunity.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testOpportunity.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testOpportunity.getContactSourceId()).isEqualTo(DEFAULT_CONTACT_SOURCE_ID);
        assertThat(testOpportunity.getProcessStageId()).isEqualTo(DEFAULT_PROCESS_STAGE_ID);
    }

    @Test
    @Transactional
    void createOpportunityWithExistingId() throws Exception {
        // Create the Opportunity with an existing ID
        opportunity.setId(1L);
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        int databaseSizeBeforeCreate = opportunityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpportunityMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOpportunities() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        // Get all the opportunityList
        restOpportunityMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(opportunity.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].applyDate").value(hasItem(DEFAULT_APPLY_DATE.toString())))
            .andExpect(jsonPath("$.[*].contactSourceId").value(hasItem(DEFAULT_CONTACT_SOURCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].processStageId").value(hasItem(DEFAULT_PROCESS_STAGE_ID.intValue())));
    }

    @Test
    @Transactional
    void getOpportunity() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        // Get the opportunity
        restOpportunityMockMvc
            .perform(get(ENTITY_API_URL_ID, opportunity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(opportunity.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.applyDate").value(DEFAULT_APPLY_DATE.toString()))
            .andExpect(jsonPath("$.contactSourceId").value(DEFAULT_CONTACT_SOURCE_ID.intValue()))
            .andExpect(jsonPath("$.processStageId").value(DEFAULT_PROCESS_STAGE_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOpportunity() throws Exception {
        // Get the opportunity
        restOpportunityMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOpportunity() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();

        // Update the opportunity
        Opportunity updatedOpportunity = opportunityRepository.findById(opportunity.getId()).get();
        // Disconnect from session so that the updates on updatedOpportunity are not directly saved in db
        em.detach(updatedOpportunity);
        updatedOpportunity
            .companyId(UPDATED_COMPANY_ID)
            .applyDate(UPDATED_APPLY_DATE)
            .contactSourceId(UPDATED_CONTACT_SOURCE_ID)
            .processStageId(UPDATED_PROCESS_STAGE_ID);
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(updatedOpportunity);

        restOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opportunityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
        Opportunity testOpportunity = opportunityList.get(opportunityList.size() - 1);
        assertThat(testOpportunity.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOpportunity.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testOpportunity.getContactSourceId()).isEqualTo(UPDATED_CONTACT_SOURCE_ID);
        assertThat(testOpportunity.getProcessStageId()).isEqualTo(UPDATED_PROCESS_STAGE_ID);
    }

    @Test
    @Transactional
    void putNonExistingOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();
        opportunity.setId(count.incrementAndGet());

        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, opportunityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();
        opportunity.setId(count.incrementAndGet());

        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();
        opportunity.setId(count.incrementAndGet());

        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(opportunityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOpportunityWithPatch() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();

        // Update the opportunity using partial update
        Opportunity partialUpdatedOpportunity = new Opportunity();
        partialUpdatedOpportunity.setId(opportunity.getId());

        partialUpdatedOpportunity
            .companyId(UPDATED_COMPANY_ID)
            .contactSourceId(UPDATED_CONTACT_SOURCE_ID)
            .processStageId(UPDATED_PROCESS_STAGE_ID);

        restOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpportunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpportunity))
            )
            .andExpect(status().isOk());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
        Opportunity testOpportunity = opportunityList.get(opportunityList.size() - 1);
        assertThat(testOpportunity.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOpportunity.getApplyDate()).isEqualTo(DEFAULT_APPLY_DATE);
        assertThat(testOpportunity.getContactSourceId()).isEqualTo(UPDATED_CONTACT_SOURCE_ID);
        assertThat(testOpportunity.getProcessStageId()).isEqualTo(UPDATED_PROCESS_STAGE_ID);
    }

    @Test
    @Transactional
    void fullUpdateOpportunityWithPatch() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();

        // Update the opportunity using partial update
        Opportunity partialUpdatedOpportunity = new Opportunity();
        partialUpdatedOpportunity.setId(opportunity.getId());

        partialUpdatedOpportunity
            .companyId(UPDATED_COMPANY_ID)
            .applyDate(UPDATED_APPLY_DATE)
            .contactSourceId(UPDATED_CONTACT_SOURCE_ID)
            .processStageId(UPDATED_PROCESS_STAGE_ID);

        restOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOpportunity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOpportunity))
            )
            .andExpect(status().isOk());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
        Opportunity testOpportunity = opportunityList.get(opportunityList.size() - 1);
        assertThat(testOpportunity.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testOpportunity.getApplyDate()).isEqualTo(UPDATED_APPLY_DATE);
        assertThat(testOpportunity.getContactSourceId()).isEqualTo(UPDATED_CONTACT_SOURCE_ID);
        assertThat(testOpportunity.getProcessStageId()).isEqualTo(UPDATED_PROCESS_STAGE_ID);
    }

    @Test
    @Transactional
    void patchNonExistingOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();
        opportunity.setId(count.incrementAndGet());

        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, opportunityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();
        opportunity.setId(count.incrementAndGet());

        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOpportunity() throws Exception {
        int databaseSizeBeforeUpdate = opportunityRepository.findAll().size();
        opportunity.setId(count.incrementAndGet());

        // Create the Opportunity
        OpportunityDTO opportunityDTO = opportunityMapper.toDto(opportunity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOpportunityMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(opportunityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Opportunity in the database
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOpportunity() throws Exception {
        // Initialize the database
        opportunityRepository.saveAndFlush(opportunity);

        int databaseSizeBeforeDelete = opportunityRepository.findAll().size();

        // Delete the opportunity
        restOpportunityMockMvc
            .perform(delete(ENTITY_API_URL_ID, opportunity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Opportunity> opportunityList = opportunityRepository.findAll();
        assertThat(opportunityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
