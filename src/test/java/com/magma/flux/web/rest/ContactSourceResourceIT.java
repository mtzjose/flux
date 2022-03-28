package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.ContactSource;
import com.magma.flux.repository.ContactSourceRepository;
import com.magma.flux.service.dto.ContactSourceDTO;
import com.magma.flux.service.mapper.ContactSourceMapper;
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
 * Integration tests for the {@link ContactSourceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContactSourceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/contact-sources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ContactSourceRepository contactSourceRepository;

    @Autowired
    private ContactSourceMapper contactSourceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactSourceMockMvc;

    private ContactSource contactSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactSource createEntity(EntityManager em) {
        ContactSource contactSource = new ContactSource().name(DEFAULT_NAME);
        return contactSource;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContactSource createUpdatedEntity(EntityManager em) {
        ContactSource contactSource = new ContactSource().name(UPDATED_NAME);
        return contactSource;
    }

    @BeforeEach
    public void initTest() {
        contactSource = createEntity(em);
    }

    @Test
    @Transactional
    void createContactSource() throws Exception {
        int databaseSizeBeforeCreate = contactSourceRepository.findAll().size();
        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);
        restContactSourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeCreate + 1);
        ContactSource testContactSource = contactSourceList.get(contactSourceList.size() - 1);
        assertThat(testContactSource.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createContactSourceWithExistingId() throws Exception {
        // Create the ContactSource with an existing ID
        contactSource.setId(1L);
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        int databaseSizeBeforeCreate = contactSourceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactSourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactSourceRepository.findAll().size();
        // set the field null
        contactSource.setName(null);

        // Create the ContactSource, which fails.
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        restContactSourceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isBadRequest());

        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContactSources() throws Exception {
        // Initialize the database
        contactSourceRepository.saveAndFlush(contactSource);

        // Get all the contactSourceList
        restContactSourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactSource.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getContactSource() throws Exception {
        // Initialize the database
        contactSourceRepository.saveAndFlush(contactSource);

        // Get the contactSource
        restContactSourceMockMvc
            .perform(get(ENTITY_API_URL_ID, contactSource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contactSource.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingContactSource() throws Exception {
        // Get the contactSource
        restContactSourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewContactSource() throws Exception {
        // Initialize the database
        contactSourceRepository.saveAndFlush(contactSource);

        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();

        // Update the contactSource
        ContactSource updatedContactSource = contactSourceRepository.findById(contactSource.getId()).get();
        // Disconnect from session so that the updates on updatedContactSource are not directly saved in db
        em.detach(updatedContactSource);
        updatedContactSource.name(UPDATED_NAME);
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(updatedContactSource);

        restContactSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
        ContactSource testContactSource = contactSourceList.get(contactSourceList.size() - 1);
        assertThat(testContactSource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingContactSource() throws Exception {
        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();
        contactSource.setId(count.incrementAndGet());

        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contactSourceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContactSource() throws Exception {
        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();
        contactSource.setId(count.incrementAndGet());

        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContactSource() throws Exception {
        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();
        contactSource.setId(count.incrementAndGet());

        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactSourceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContactSourceWithPatch() throws Exception {
        // Initialize the database
        contactSourceRepository.saveAndFlush(contactSource);

        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();

        // Update the contactSource using partial update
        ContactSource partialUpdatedContactSource = new ContactSource();
        partialUpdatedContactSource.setId(contactSource.getId());

        partialUpdatedContactSource.name(UPDATED_NAME);

        restContactSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactSource))
            )
            .andExpect(status().isOk());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
        ContactSource testContactSource = contactSourceList.get(contactSourceList.size() - 1);
        assertThat(testContactSource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateContactSourceWithPatch() throws Exception {
        // Initialize the database
        contactSourceRepository.saveAndFlush(contactSource);

        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();

        // Update the contactSource using partial update
        ContactSource partialUpdatedContactSource = new ContactSource();
        partialUpdatedContactSource.setId(contactSource.getId());

        partialUpdatedContactSource.name(UPDATED_NAME);

        restContactSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContactSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedContactSource))
            )
            .andExpect(status().isOk());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
        ContactSource testContactSource = contactSourceList.get(contactSourceList.size() - 1);
        assertThat(testContactSource.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingContactSource() throws Exception {
        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();
        contactSource.setId(count.incrementAndGet());

        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contactSourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContactSource() throws Exception {
        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();
        contactSource.setId(count.incrementAndGet());

        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContactSource() throws Exception {
        int databaseSizeBeforeUpdate = contactSourceRepository.findAll().size();
        contactSource.setId(count.incrementAndGet());

        // Create the ContactSource
        ContactSourceDTO contactSourceDTO = contactSourceMapper.toDto(contactSource);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContactSourceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(contactSourceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContactSource in the database
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContactSource() throws Exception {
        // Initialize the database
        contactSourceRepository.saveAndFlush(contactSource);

        int databaseSizeBeforeDelete = contactSourceRepository.findAll().size();

        // Delete the contactSource
        restContactSourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, contactSource.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContactSource> contactSourceList = contactSourceRepository.findAll();
        assertThat(contactSourceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
