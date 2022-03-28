package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.PersonCompanyHistory;
import com.magma.flux.repository.PersonCompanyHistoryRepository;
import com.magma.flux.service.dto.PersonCompanyHistoryDTO;
import com.magma.flux.service.mapper.PersonCompanyHistoryMapper;
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
 * Integration tests for the {@link PersonCompanyHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonCompanyHistoryResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final Boolean DEFAULT_INVESTOR = false;
    private static final Boolean UPDATED_INVESTOR = true;

    private static final Boolean DEFAULT_FOUNDER = false;
    private static final Boolean UPDATED_FOUNDER = true;

    private static final String ENTITY_API_URL = "/api/person-company-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonCompanyHistoryRepository personCompanyHistoryRepository;

    @Autowired
    private PersonCompanyHistoryMapper personCompanyHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonCompanyHistoryMockMvc;

    private PersonCompanyHistory personCompanyHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonCompanyHistory createEntity(EntityManager em) {
        PersonCompanyHistory personCompanyHistory = new PersonCompanyHistory()
            .companyId(DEFAULT_COMPANY_ID)
            .personId(DEFAULT_PERSON_ID)
            .investor(DEFAULT_INVESTOR)
            .founder(DEFAULT_FOUNDER);
        return personCompanyHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonCompanyHistory createUpdatedEntity(EntityManager em) {
        PersonCompanyHistory personCompanyHistory = new PersonCompanyHistory()
            .companyId(UPDATED_COMPANY_ID)
            .personId(UPDATED_PERSON_ID)
            .investor(UPDATED_INVESTOR)
            .founder(UPDATED_FOUNDER);
        return personCompanyHistory;
    }

    @BeforeEach
    public void initTest() {
        personCompanyHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeCreate = personCompanyHistoryRepository.findAll().size();
        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);
        restPersonCompanyHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PersonCompanyHistory testPersonCompanyHistory = personCompanyHistoryList.get(personCompanyHistoryList.size() - 1);
        assertThat(testPersonCompanyHistory.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPersonCompanyHistory.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
        assertThat(testPersonCompanyHistory.getInvestor()).isEqualTo(DEFAULT_INVESTOR);
        assertThat(testPersonCompanyHistory.getFounder()).isEqualTo(DEFAULT_FOUNDER);
    }

    @Test
    @Transactional
    void createPersonCompanyHistoryWithExistingId() throws Exception {
        // Create the PersonCompanyHistory with an existing ID
        personCompanyHistory.setId(1L);
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        int databaseSizeBeforeCreate = personCompanyHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonCompanyHistoryMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPersonCompanyHistories() throws Exception {
        // Initialize the database
        personCompanyHistoryRepository.saveAndFlush(personCompanyHistory);

        // Get all the personCompanyHistoryList
        restPersonCompanyHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personCompanyHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].investor").value(hasItem(DEFAULT_INVESTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].founder").value(hasItem(DEFAULT_FOUNDER.booleanValue())));
    }

    @Test
    @Transactional
    void getPersonCompanyHistory() throws Exception {
        // Initialize the database
        personCompanyHistoryRepository.saveAndFlush(personCompanyHistory);

        // Get the personCompanyHistory
        restPersonCompanyHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, personCompanyHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personCompanyHistory.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.investor").value(DEFAULT_INVESTOR.booleanValue()))
            .andExpect(jsonPath("$.founder").value(DEFAULT_FOUNDER.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingPersonCompanyHistory() throws Exception {
        // Get the personCompanyHistory
        restPersonCompanyHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonCompanyHistory() throws Exception {
        // Initialize the database
        personCompanyHistoryRepository.saveAndFlush(personCompanyHistory);

        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();

        // Update the personCompanyHistory
        PersonCompanyHistory updatedPersonCompanyHistory = personCompanyHistoryRepository.findById(personCompanyHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPersonCompanyHistory are not directly saved in db
        em.detach(updatedPersonCompanyHistory);
        updatedPersonCompanyHistory
            .companyId(UPDATED_COMPANY_ID)
            .personId(UPDATED_PERSON_ID)
            .investor(UPDATED_INVESTOR)
            .founder(UPDATED_FOUNDER);
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(updatedPersonCompanyHistory);

        restPersonCompanyHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personCompanyHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
        PersonCompanyHistory testPersonCompanyHistory = personCompanyHistoryList.get(personCompanyHistoryList.size() - 1);
        assertThat(testPersonCompanyHistory.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPersonCompanyHistory.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testPersonCompanyHistory.getInvestor()).isEqualTo(UPDATED_INVESTOR);
        assertThat(testPersonCompanyHistory.getFounder()).isEqualTo(UPDATED_FOUNDER);
    }

    @Test
    @Transactional
    void putNonExistingPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();
        personCompanyHistory.setId(count.incrementAndGet());

        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonCompanyHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personCompanyHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();
        personCompanyHistory.setId(count.incrementAndGet());

        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();
        personCompanyHistory.setId(count.incrementAndGet());

        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyHistoryMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonCompanyHistoryWithPatch() throws Exception {
        // Initialize the database
        personCompanyHistoryRepository.saveAndFlush(personCompanyHistory);

        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();

        // Update the personCompanyHistory using partial update
        PersonCompanyHistory partialUpdatedPersonCompanyHistory = new PersonCompanyHistory();
        partialUpdatedPersonCompanyHistory.setId(personCompanyHistory.getId());

        partialUpdatedPersonCompanyHistory.personId(UPDATED_PERSON_ID);

        restPersonCompanyHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonCompanyHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonCompanyHistory))
            )
            .andExpect(status().isOk());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
        PersonCompanyHistory testPersonCompanyHistory = personCompanyHistoryList.get(personCompanyHistoryList.size() - 1);
        assertThat(testPersonCompanyHistory.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testPersonCompanyHistory.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testPersonCompanyHistory.getInvestor()).isEqualTo(DEFAULT_INVESTOR);
        assertThat(testPersonCompanyHistory.getFounder()).isEqualTo(DEFAULT_FOUNDER);
    }

    @Test
    @Transactional
    void fullUpdatePersonCompanyHistoryWithPatch() throws Exception {
        // Initialize the database
        personCompanyHistoryRepository.saveAndFlush(personCompanyHistory);

        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();

        // Update the personCompanyHistory using partial update
        PersonCompanyHistory partialUpdatedPersonCompanyHistory = new PersonCompanyHistory();
        partialUpdatedPersonCompanyHistory.setId(personCompanyHistory.getId());

        partialUpdatedPersonCompanyHistory
            .companyId(UPDATED_COMPANY_ID)
            .personId(UPDATED_PERSON_ID)
            .investor(UPDATED_INVESTOR)
            .founder(UPDATED_FOUNDER);

        restPersonCompanyHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonCompanyHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonCompanyHistory))
            )
            .andExpect(status().isOk());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
        PersonCompanyHistory testPersonCompanyHistory = personCompanyHistoryList.get(personCompanyHistoryList.size() - 1);
        assertThat(testPersonCompanyHistory.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testPersonCompanyHistory.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
        assertThat(testPersonCompanyHistory.getInvestor()).isEqualTo(UPDATED_INVESTOR);
        assertThat(testPersonCompanyHistory.getFounder()).isEqualTo(UPDATED_FOUNDER);
    }

    @Test
    @Transactional
    void patchNonExistingPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();
        personCompanyHistory.setId(count.incrementAndGet());

        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonCompanyHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personCompanyHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();
        personCompanyHistory.setId(count.incrementAndGet());

        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonCompanyHistory() throws Exception {
        int databaseSizeBeforeUpdate = personCompanyHistoryRepository.findAll().size();
        personCompanyHistory.setId(count.incrementAndGet());

        // Create the PersonCompanyHistory
        PersonCompanyHistoryDTO personCompanyHistoryDTO = personCompanyHistoryMapper.toDto(personCompanyHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonCompanyHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personCompanyHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonCompanyHistory in the database
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonCompanyHistory() throws Exception {
        // Initialize the database
        personCompanyHistoryRepository.saveAndFlush(personCompanyHistory);

        int databaseSizeBeforeDelete = personCompanyHistoryRepository.findAll().size();

        // Delete the personCompanyHistory
        restPersonCompanyHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, personCompanyHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonCompanyHistory> personCompanyHistoryList = personCompanyHistoryRepository.findAll();
        assertThat(personCompanyHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
