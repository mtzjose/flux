package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.CompanyCategories;
import com.magma.flux.repository.CompanyCategoriesRepository;
import com.magma.flux.service.dto.CompanyCategoriesDTO;
import com.magma.flux.service.mapper.CompanyCategoriesMapper;
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
 * Integration tests for the {@link CompanyCategoriesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyCategoriesResourceIT {

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_CATEGORY_ID = 1L;
    private static final Long UPDATED_CATEGORY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/company-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyCategoriesRepository companyCategoriesRepository;

    @Autowired
    private CompanyCategoriesMapper companyCategoriesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyCategoriesMockMvc;

    private CompanyCategories companyCategories;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyCategories createEntity(EntityManager em) {
        CompanyCategories companyCategories = new CompanyCategories().companyId(DEFAULT_COMPANY_ID).categoryId(DEFAULT_CATEGORY_ID);
        return companyCategories;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyCategories createUpdatedEntity(EntityManager em) {
        CompanyCategories companyCategories = new CompanyCategories().companyId(UPDATED_COMPANY_ID).categoryId(UPDATED_CATEGORY_ID);
        return companyCategories;
    }

    @BeforeEach
    public void initTest() {
        companyCategories = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyCategories() throws Exception {
        int databaseSizeBeforeCreate = companyCategoriesRepository.findAll().size();
        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);
        restCompanyCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyCategories testCompanyCategories = companyCategoriesList.get(companyCategoriesList.size() - 1);
        assertThat(testCompanyCategories.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompanyCategories.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void createCompanyCategoriesWithExistingId() throws Exception {
        // Create the CompanyCategories with an existing ID
        companyCategories.setId(1L);
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        int databaseSizeBeforeCreate = companyCategoriesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCompanyIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyCategoriesRepository.findAll().size();
        // set the field null
        companyCategories.setCompanyId(null);

        // Create the CompanyCategories, which fails.
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        restCompanyCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyCategoriesRepository.findAll().size();
        // set the field null
        companyCategories.setCategoryId(null);

        // Create the CompanyCategories, which fails.
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        restCompanyCategoriesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyCategories() throws Exception {
        // Initialize the database
        companyCategoriesRepository.saveAndFlush(companyCategories);

        // Get all the companyCategoriesList
        restCompanyCategoriesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCategories.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.intValue())));
    }

    @Test
    @Transactional
    void getCompanyCategories() throws Exception {
        // Initialize the database
        companyCategoriesRepository.saveAndFlush(companyCategories);

        // Get the companyCategories
        restCompanyCategoriesMockMvc
            .perform(get(ENTITY_API_URL_ID, companyCategories.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyCategories.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompanyCategories() throws Exception {
        // Get the companyCategories
        restCompanyCategoriesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyCategories() throws Exception {
        // Initialize the database
        companyCategoriesRepository.saveAndFlush(companyCategories);

        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();

        // Update the companyCategories
        CompanyCategories updatedCompanyCategories = companyCategoriesRepository.findById(companyCategories.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyCategories are not directly saved in db
        em.detach(updatedCompanyCategories);
        updatedCompanyCategories.companyId(UPDATED_COMPANY_ID).categoryId(UPDATED_CATEGORY_ID);
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(updatedCompanyCategories);

        restCompanyCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyCategoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategories testCompanyCategories = companyCategoriesList.get(companyCategoriesList.size() - 1);
        assertThat(testCompanyCategories.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompanyCategories.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    void putNonExistingCompanyCategories() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();
        companyCategories.setId(count.incrementAndGet());

        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyCategoriesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyCategories() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();
        companyCategories.setId(count.incrementAndGet());

        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyCategories() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();
        companyCategories.setId(count.incrementAndGet());

        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoriesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyCategoriesWithPatch() throws Exception {
        // Initialize the database
        companyCategoriesRepository.saveAndFlush(companyCategories);

        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();

        // Update the companyCategories using partial update
        CompanyCategories partialUpdatedCompanyCategories = new CompanyCategories();
        partialUpdatedCompanyCategories.setId(companyCategories.getId());

        restCompanyCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyCategories))
            )
            .andExpect(status().isOk());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategories testCompanyCategories = companyCategoriesList.get(companyCategoriesList.size() - 1);
        assertThat(testCompanyCategories.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompanyCategories.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
    }

    @Test
    @Transactional
    void fullUpdateCompanyCategoriesWithPatch() throws Exception {
        // Initialize the database
        companyCategoriesRepository.saveAndFlush(companyCategories);

        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();

        // Update the companyCategories using partial update
        CompanyCategories partialUpdatedCompanyCategories = new CompanyCategories();
        partialUpdatedCompanyCategories.setId(companyCategories.getId());

        partialUpdatedCompanyCategories.companyId(UPDATED_COMPANY_ID).categoryId(UPDATED_CATEGORY_ID);

        restCompanyCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyCategories.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyCategories))
            )
            .andExpect(status().isOk());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategories testCompanyCategories = companyCategoriesList.get(companyCategoriesList.size() - 1);
        assertThat(testCompanyCategories.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompanyCategories.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyCategories() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();
        companyCategories.setId(count.incrementAndGet());

        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyCategoriesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyCategories() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();
        companyCategories.setId(count.incrementAndGet());

        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyCategories() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoriesRepository.findAll().size();
        companyCategories.setId(count.incrementAndGet());

        // Create the CompanyCategories
        CompanyCategoriesDTO companyCategoriesDTO = companyCategoriesMapper.toDto(companyCategories);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoriesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoriesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyCategories in the database
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyCategories() throws Exception {
        // Initialize the database
        companyCategoriesRepository.saveAndFlush(companyCategories);

        int databaseSizeBeforeDelete = companyCategoriesRepository.findAll().size();

        // Delete the companyCategories
        restCompanyCategoriesMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyCategories.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyCategories> companyCategoriesList = companyCategoriesRepository.findAll();
        assertThat(companyCategoriesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
