package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.Founder;
import com.magma.flux.repository.FounderRepository;
import com.magma.flux.service.dto.FounderDTO;
import com.magma.flux.service.mapper.FounderMapper;
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
 * Integration tests for the {@link FounderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FounderResourceIT {

    private static final Long DEFAULT_PERSON_ID = 1L;
    private static final Long UPDATED_PERSON_ID = 2L;

    private static final String ENTITY_API_URL = "/api/founders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FounderRepository founderRepository;

    @Autowired
    private FounderMapper founderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFounderMockMvc;

    private Founder founder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Founder createEntity(EntityManager em) {
        Founder founder = new Founder().personId(DEFAULT_PERSON_ID);
        return founder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Founder createUpdatedEntity(EntityManager em) {
        Founder founder = new Founder().personId(UPDATED_PERSON_ID);
        return founder;
    }

    @BeforeEach
    public void initTest() {
        founder = createEntity(em);
    }

    @Test
    @Transactional
    void createFounder() throws Exception {
        int databaseSizeBeforeCreate = founderRepository.findAll().size();
        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);
        restFounderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderDTO)))
            .andExpect(status().isCreated());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeCreate + 1);
        Founder testFounder = founderList.get(founderList.size() - 1);
        assertThat(testFounder.getPersonId()).isEqualTo(DEFAULT_PERSON_ID);
    }

    @Test
    @Transactional
    void createFounderWithExistingId() throws Exception {
        // Create the Founder with an existing ID
        founder.setId(1L);
        FounderDTO founderDTO = founderMapper.toDto(founder);

        int databaseSizeBeforeCreate = founderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFounderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkPersonIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = founderRepository.findAll().size();
        // set the field null
        founder.setPersonId(null);

        // Create the Founder, which fails.
        FounderDTO founderDTO = founderMapper.toDto(founder);

        restFounderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderDTO)))
            .andExpect(status().isBadRequest());

        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFounders() throws Exception {
        // Initialize the database
        founderRepository.saveAndFlush(founder);

        // Get all the founderList
        restFounderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(founder.getId().intValue())))
            .andExpect(jsonPath("$.[*].personId").value(hasItem(DEFAULT_PERSON_ID.intValue())));
    }

    @Test
    @Transactional
    void getFounder() throws Exception {
        // Initialize the database
        founderRepository.saveAndFlush(founder);

        // Get the founder
        restFounderMockMvc
            .perform(get(ENTITY_API_URL_ID, founder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(founder.getId().intValue()))
            .andExpect(jsonPath("$.personId").value(DEFAULT_PERSON_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFounder() throws Exception {
        // Get the founder
        restFounderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFounder() throws Exception {
        // Initialize the database
        founderRepository.saveAndFlush(founder);

        int databaseSizeBeforeUpdate = founderRepository.findAll().size();

        // Update the founder
        Founder updatedFounder = founderRepository.findById(founder.getId()).get();
        // Disconnect from session so that the updates on updatedFounder are not directly saved in db
        em.detach(updatedFounder);
        updatedFounder.personId(UPDATED_PERSON_ID);
        FounderDTO founderDTO = founderMapper.toDto(updatedFounder);

        restFounderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, founderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(founderDTO))
            )
            .andExpect(status().isOk());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
        Founder testFounder = founderList.get(founderList.size() - 1);
        assertThat(testFounder.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
    }

    @Test
    @Transactional
    void putNonExistingFounder() throws Exception {
        int databaseSizeBeforeUpdate = founderRepository.findAll().size();
        founder.setId(count.incrementAndGet());

        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFounderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, founderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(founderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFounder() throws Exception {
        int databaseSizeBeforeUpdate = founderRepository.findAll().size();
        founder.setId(count.incrementAndGet());

        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(founderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFounder() throws Exception {
        int databaseSizeBeforeUpdate = founderRepository.findAll().size();
        founder.setId(count.incrementAndGet());

        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(founderDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFounderWithPatch() throws Exception {
        // Initialize the database
        founderRepository.saveAndFlush(founder);

        int databaseSizeBeforeUpdate = founderRepository.findAll().size();

        // Update the founder using partial update
        Founder partialUpdatedFounder = new Founder();
        partialUpdatedFounder.setId(founder.getId());

        partialUpdatedFounder.personId(UPDATED_PERSON_ID);

        restFounderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFounder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFounder))
            )
            .andExpect(status().isOk());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
        Founder testFounder = founderList.get(founderList.size() - 1);
        assertThat(testFounder.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
    }

    @Test
    @Transactional
    void fullUpdateFounderWithPatch() throws Exception {
        // Initialize the database
        founderRepository.saveAndFlush(founder);

        int databaseSizeBeforeUpdate = founderRepository.findAll().size();

        // Update the founder using partial update
        Founder partialUpdatedFounder = new Founder();
        partialUpdatedFounder.setId(founder.getId());

        partialUpdatedFounder.personId(UPDATED_PERSON_ID);

        restFounderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFounder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFounder))
            )
            .andExpect(status().isOk());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
        Founder testFounder = founderList.get(founderList.size() - 1);
        assertThat(testFounder.getPersonId()).isEqualTo(UPDATED_PERSON_ID);
    }

    @Test
    @Transactional
    void patchNonExistingFounder() throws Exception {
        int databaseSizeBeforeUpdate = founderRepository.findAll().size();
        founder.setId(count.incrementAndGet());

        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFounderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, founderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(founderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFounder() throws Exception {
        int databaseSizeBeforeUpdate = founderRepository.findAll().size();
        founder.setId(count.incrementAndGet());

        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(founderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFounder() throws Exception {
        int databaseSizeBeforeUpdate = founderRepository.findAll().size();
        founder.setId(count.incrementAndGet());

        // Create the Founder
        FounderDTO founderDTO = founderMapper.toDto(founder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFounderMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(founderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Founder in the database
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFounder() throws Exception {
        // Initialize the database
        founderRepository.saveAndFlush(founder);

        int databaseSizeBeforeDelete = founderRepository.findAll().size();

        // Delete the founder
        restFounderMockMvc
            .perform(delete(ENTITY_API_URL_ID, founder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Founder> founderList = founderRepository.findAll();
        assertThat(founderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
