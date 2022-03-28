package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.CompanyCategory;
import com.magma.flux.repository.CompanyCategoryRepository;
import com.magma.flux.service.dto.CompanyCategoryDTO;
import com.magma.flux.service.mapper.CompanyCategoryMapper;
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
 * Integration tests for the {@link CompanyCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/company-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyCategoryRepository companyCategoryRepository;

    @Autowired
    private CompanyCategoryMapper companyCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyCategoryMockMvc;

    private CompanyCategory companyCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyCategory createEntity(EntityManager em) {
        CompanyCategory companyCategory = new CompanyCategory().name(DEFAULT_NAME);
        return companyCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyCategory createUpdatedEntity(EntityManager em) {
        CompanyCategory companyCategory = new CompanyCategory().name(UPDATED_NAME);
        return companyCategory;
    }

    @BeforeEach
    public void initTest() {
        companyCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyCategory() throws Exception {
        int databaseSizeBeforeCreate = companyCategoryRepository.findAll().size();
        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);
        restCompanyCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyCategory testCompanyCategory = companyCategoryList.get(companyCategoryList.size() - 1);
        assertThat(testCompanyCategory.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCompanyCategoryWithExistingId() throws Exception {
        // Create the CompanyCategory with an existing ID
        companyCategory.setId(1L);
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        int databaseSizeBeforeCreate = companyCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = companyCategoryRepository.findAll().size();
        // set the field null
        companyCategory.setName(null);

        // Create the CompanyCategory, which fails.
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        restCompanyCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCompanyCategories() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        // Get all the companyCategoryList
        restCompanyCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        // Get the companyCategory
        restCompanyCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, companyCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCompanyCategory() throws Exception {
        // Get the companyCategory
        restCompanyCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();

        // Update the companyCategory
        CompanyCategory updatedCompanyCategory = companyCategoryRepository.findById(companyCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyCategory are not directly saved in db
        em.detach(updatedCompanyCategory);
        updatedCompanyCategory.name(UPDATED_NAME);
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(updatedCompanyCategory);

        restCompanyCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategory testCompanyCategory = companyCategoryList.get(companyCategoryList.size() - 1);
        assertThat(testCompanyCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();
        companyCategory.setId(count.incrementAndGet());

        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();
        companyCategory.setId(count.incrementAndGet());

        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();
        companyCategory.setId(count.incrementAndGet());

        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyCategoryWithPatch() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();

        // Update the companyCategory using partial update
        CompanyCategory partialUpdatedCompanyCategory = new CompanyCategory();
        partialUpdatedCompanyCategory.setId(companyCategory.getId());

        partialUpdatedCompanyCategory.name(UPDATED_NAME);

        restCompanyCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyCategory))
            )
            .andExpect(status().isOk());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategory testCompanyCategory = companyCategoryList.get(companyCategoryList.size() - 1);
        assertThat(testCompanyCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCompanyCategoryWithPatch() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();

        // Update the companyCategory using partial update
        CompanyCategory partialUpdatedCompanyCategory = new CompanyCategory();
        partialUpdatedCompanyCategory.setId(companyCategory.getId());

        partialUpdatedCompanyCategory.name(UPDATED_NAME);

        restCompanyCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyCategory))
            )
            .andExpect(status().isOk());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
        CompanyCategory testCompanyCategory = companyCategoryList.get(companyCategoryList.size() - 1);
        assertThat(testCompanyCategory.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();
        companyCategory.setId(count.incrementAndGet());

        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();
        companyCategory.setId(count.incrementAndGet());

        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyCategory() throws Exception {
        int databaseSizeBeforeUpdate = companyCategoryRepository.findAll().size();
        companyCategory.setId(count.incrementAndGet());

        // Create the CompanyCategory
        CompanyCategoryDTO companyCategoryDTO = companyCategoryMapper.toDto(companyCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyCategory in the database
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyCategory() throws Exception {
        // Initialize the database
        companyCategoryRepository.saveAndFlush(companyCategory);

        int databaseSizeBeforeDelete = companyCategoryRepository.findAll().size();

        // Delete the companyCategory
        restCompanyCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyCategory> companyCategoryList = companyCategoryRepository.findAll();
        assertThat(companyCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
