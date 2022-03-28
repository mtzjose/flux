package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.Pronoun;
import com.magma.flux.repository.PronounRepository;
import com.magma.flux.service.dto.PronounDTO;
import com.magma.flux.service.mapper.PronounMapper;
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
 * Integration tests for the {@link PronounResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PronounResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pronouns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PronounRepository pronounRepository;

    @Autowired
    private PronounMapper pronounMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPronounMockMvc;

    private Pronoun pronoun;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pronoun createEntity(EntityManager em) {
        Pronoun pronoun = new Pronoun().name(DEFAULT_NAME);
        return pronoun;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pronoun createUpdatedEntity(EntityManager em) {
        Pronoun pronoun = new Pronoun().name(UPDATED_NAME);
        return pronoun;
    }

    @BeforeEach
    public void initTest() {
        pronoun = createEntity(em);
    }

    @Test
    @Transactional
    void createPronoun() throws Exception {
        int databaseSizeBeforeCreate = pronounRepository.findAll().size();
        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);
        restPronounMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pronounDTO)))
            .andExpect(status().isCreated());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeCreate + 1);
        Pronoun testPronoun = pronounList.get(pronounList.size() - 1);
        assertThat(testPronoun.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createPronounWithExistingId() throws Exception {
        // Create the Pronoun with an existing ID
        pronoun.setId(1L);
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        int databaseSizeBeforeCreate = pronounRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPronounMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pronounDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pronounRepository.findAll().size();
        // set the field null
        pronoun.setName(null);

        // Create the Pronoun, which fails.
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        restPronounMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pronounDTO)))
            .andExpect(status().isBadRequest());

        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPronouns() throws Exception {
        // Initialize the database
        pronounRepository.saveAndFlush(pronoun);

        // Get all the pronounList
        restPronounMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pronoun.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getPronoun() throws Exception {
        // Initialize the database
        pronounRepository.saveAndFlush(pronoun);

        // Get the pronoun
        restPronounMockMvc
            .perform(get(ENTITY_API_URL_ID, pronoun.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pronoun.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingPronoun() throws Exception {
        // Get the pronoun
        restPronounMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPronoun() throws Exception {
        // Initialize the database
        pronounRepository.saveAndFlush(pronoun);

        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();

        // Update the pronoun
        Pronoun updatedPronoun = pronounRepository.findById(pronoun.getId()).get();
        // Disconnect from session so that the updates on updatedPronoun are not directly saved in db
        em.detach(updatedPronoun);
        updatedPronoun.name(UPDATED_NAME);
        PronounDTO pronounDTO = pronounMapper.toDto(updatedPronoun);

        restPronounMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pronounDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pronounDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
        Pronoun testPronoun = pronounList.get(pronounList.size() - 1);
        assertThat(testPronoun.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingPronoun() throws Exception {
        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();
        pronoun.setId(count.incrementAndGet());

        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPronounMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pronounDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pronounDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPronoun() throws Exception {
        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();
        pronoun.setId(count.incrementAndGet());

        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPronounMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pronounDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPronoun() throws Exception {
        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();
        pronoun.setId(count.incrementAndGet());

        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPronounMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pronounDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePronounWithPatch() throws Exception {
        // Initialize the database
        pronounRepository.saveAndFlush(pronoun);

        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();

        // Update the pronoun using partial update
        Pronoun partialUpdatedPronoun = new Pronoun();
        partialUpdatedPronoun.setId(pronoun.getId());

        partialUpdatedPronoun.name(UPDATED_NAME);

        restPronounMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPronoun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPronoun))
            )
            .andExpect(status().isOk());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
        Pronoun testPronoun = pronounList.get(pronounList.size() - 1);
        assertThat(testPronoun.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdatePronounWithPatch() throws Exception {
        // Initialize the database
        pronounRepository.saveAndFlush(pronoun);

        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();

        // Update the pronoun using partial update
        Pronoun partialUpdatedPronoun = new Pronoun();
        partialUpdatedPronoun.setId(pronoun.getId());

        partialUpdatedPronoun.name(UPDATED_NAME);

        restPronounMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPronoun.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPronoun))
            )
            .andExpect(status().isOk());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
        Pronoun testPronoun = pronounList.get(pronounList.size() - 1);
        assertThat(testPronoun.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingPronoun() throws Exception {
        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();
        pronoun.setId(count.incrementAndGet());

        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPronounMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pronounDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pronounDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPronoun() throws Exception {
        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();
        pronoun.setId(count.incrementAndGet());

        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPronounMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pronounDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPronoun() throws Exception {
        int databaseSizeBeforeUpdate = pronounRepository.findAll().size();
        pronoun.setId(count.incrementAndGet());

        // Create the Pronoun
        PronounDTO pronounDTO = pronounMapper.toDto(pronoun);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPronounMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pronounDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pronoun in the database
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePronoun() throws Exception {
        // Initialize the database
        pronounRepository.saveAndFlush(pronoun);

        int databaseSizeBeforeDelete = pronounRepository.findAll().size();

        // Delete the pronoun
        restPronounMockMvc
            .perform(delete(ENTITY_API_URL_ID, pronoun.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pronoun> pronounList = pronounRepository.findAll();
        assertThat(pronounList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
