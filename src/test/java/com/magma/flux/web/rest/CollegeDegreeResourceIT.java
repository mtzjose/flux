package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.CollegeDegree;
import com.magma.flux.repository.CollegeDegreeRepository;
import com.magma.flux.service.dto.CollegeDegreeDTO;
import com.magma.flux.service.mapper.CollegeDegreeMapper;
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
 * Integration tests for the {@link CollegeDegreeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollegeDegreeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/college-degrees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollegeDegreeRepository collegeDegreeRepository;

    @Autowired
    private CollegeDegreeMapper collegeDegreeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollegeDegreeMockMvc;

    private CollegeDegree collegeDegree;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollegeDegree createEntity(EntityManager em) {
        CollegeDegree collegeDegree = new CollegeDegree().name(DEFAULT_NAME);
        return collegeDegree;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollegeDegree createUpdatedEntity(EntityManager em) {
        CollegeDegree collegeDegree = new CollegeDegree().name(UPDATED_NAME);
        return collegeDegree;
    }

    @BeforeEach
    public void initTest() {
        collegeDegree = createEntity(em);
    }

    @Test
    @Transactional
    void createCollegeDegree() throws Exception {
        int databaseSizeBeforeCreate = collegeDegreeRepository.findAll().size();
        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);
        restCollegeDegreeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeCreate + 1);
        CollegeDegree testCollegeDegree = collegeDegreeList.get(collegeDegreeList.size() - 1);
        assertThat(testCollegeDegree.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCollegeDegreeWithExistingId() throws Exception {
        // Create the CollegeDegree with an existing ID
        collegeDegree.setId(1L);
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        int databaseSizeBeforeCreate = collegeDegreeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollegeDegreeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = collegeDegreeRepository.findAll().size();
        // set the field null
        collegeDegree.setName(null);

        // Create the CollegeDegree, which fails.
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        restCollegeDegreeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCollegeDegrees() throws Exception {
        // Initialize the database
        collegeDegreeRepository.saveAndFlush(collegeDegree);

        // Get all the collegeDegreeList
        restCollegeDegreeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collegeDegree.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCollegeDegree() throws Exception {
        // Initialize the database
        collegeDegreeRepository.saveAndFlush(collegeDegree);

        // Get the collegeDegree
        restCollegeDegreeMockMvc
            .perform(get(ENTITY_API_URL_ID, collegeDegree.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collegeDegree.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingCollegeDegree() throws Exception {
        // Get the collegeDegree
        restCollegeDegreeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollegeDegree() throws Exception {
        // Initialize the database
        collegeDegreeRepository.saveAndFlush(collegeDegree);

        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();

        // Update the collegeDegree
        CollegeDegree updatedCollegeDegree = collegeDegreeRepository.findById(collegeDegree.getId()).get();
        // Disconnect from session so that the updates on updatedCollegeDegree are not directly saved in db
        em.detach(updatedCollegeDegree);
        updatedCollegeDegree.name(UPDATED_NAME);
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(updatedCollegeDegree);

        restCollegeDegreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collegeDegreeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
        CollegeDegree testCollegeDegree = collegeDegreeList.get(collegeDegreeList.size() - 1);
        assertThat(testCollegeDegree.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingCollegeDegree() throws Exception {
        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();
        collegeDegree.setId(count.incrementAndGet());

        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegeDegreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collegeDegreeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollegeDegree() throws Exception {
        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();
        collegeDegree.setId(count.incrementAndGet());

        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeDegreeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollegeDegree() throws Exception {
        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();
        collegeDegree.setId(count.incrementAndGet());

        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeDegreeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollegeDegreeWithPatch() throws Exception {
        // Initialize the database
        collegeDegreeRepository.saveAndFlush(collegeDegree);

        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();

        // Update the collegeDegree using partial update
        CollegeDegree partialUpdatedCollegeDegree = new CollegeDegree();
        partialUpdatedCollegeDegree.setId(collegeDegree.getId());

        restCollegeDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollegeDegree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollegeDegree))
            )
            .andExpect(status().isOk());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
        CollegeDegree testCollegeDegree = collegeDegreeList.get(collegeDegreeList.size() - 1);
        assertThat(testCollegeDegree.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCollegeDegreeWithPatch() throws Exception {
        // Initialize the database
        collegeDegreeRepository.saveAndFlush(collegeDegree);

        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();

        // Update the collegeDegree using partial update
        CollegeDegree partialUpdatedCollegeDegree = new CollegeDegree();
        partialUpdatedCollegeDegree.setId(collegeDegree.getId());

        partialUpdatedCollegeDegree.name(UPDATED_NAME);

        restCollegeDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollegeDegree.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollegeDegree))
            )
            .andExpect(status().isOk());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
        CollegeDegree testCollegeDegree = collegeDegreeList.get(collegeDegreeList.size() - 1);
        assertThat(testCollegeDegree.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingCollegeDegree() throws Exception {
        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();
        collegeDegree.setId(count.incrementAndGet());

        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollegeDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collegeDegreeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollegeDegree() throws Exception {
        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();
        collegeDegree.setId(count.incrementAndGet());

        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollegeDegree() throws Exception {
        int databaseSizeBeforeUpdate = collegeDegreeRepository.findAll().size();
        collegeDegree.setId(count.incrementAndGet());

        // Create the CollegeDegree
        CollegeDegreeDTO collegeDegreeDTO = collegeDegreeMapper.toDto(collegeDegree);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollegeDegreeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collegeDegreeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollegeDegree in the database
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollegeDegree() throws Exception {
        // Initialize the database
        collegeDegreeRepository.saveAndFlush(collegeDegree);

        int databaseSizeBeforeDelete = collegeDegreeRepository.findAll().size();

        // Delete the collegeDegree
        restCollegeDegreeMockMvc
            .perform(delete(ENTITY_API_URL_ID, collegeDegree.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollegeDegree> collegeDegreeList = collegeDegreeRepository.findAll();
        assertThat(collegeDegreeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
