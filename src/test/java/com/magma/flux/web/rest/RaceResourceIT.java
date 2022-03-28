package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.Race;
import com.magma.flux.repository.RaceRepository;
import com.magma.flux.service.dto.RaceDTO;
import com.magma.flux.service.mapper.RaceMapper;
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
 * Integration tests for the {@link RaceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RaceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/races";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RaceRepository raceRepository;

    @Autowired
    private RaceMapper raceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRaceMockMvc;

    private Race race;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Race createEntity(EntityManager em) {
        Race race = new Race().name(DEFAULT_NAME);
        return race;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Race createUpdatedEntity(EntityManager em) {
        Race race = new Race().name(UPDATED_NAME);
        return race;
    }

    @BeforeEach
    public void initTest() {
        race = createEntity(em);
    }

    @Test
    @Transactional
    void createRace() throws Exception {
        int databaseSizeBeforeCreate = raceRepository.findAll().size();
        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);
        restRaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raceDTO)))
            .andExpect(status().isCreated());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate + 1);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createRaceWithExistingId() throws Exception {
        // Create the Race with an existing ID
        race.setId(1L);
        RaceDTO raceDTO = raceMapper.toDto(race);

        int databaseSizeBeforeCreate = raceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = raceRepository.findAll().size();
        // set the field null
        race.setName(null);

        // Create the Race, which fails.
        RaceDTO raceDTO = raceMapper.toDto(race);

        restRaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raceDTO)))
            .andExpect(status().isBadRequest());

        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRaces() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get all the raceList
        restRaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(race.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        // Get the race
        restRaceMockMvc
            .perform(get(ENTITY_API_URL_ID, race.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(race.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRace() throws Exception {
        // Get the race
        restRaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race
        Race updatedRace = raceRepository.findById(race.getId()).get();
        // Disconnect from session so that the updates on updatedRace are not directly saved in db
        em.detach(updatedRace);
        updatedRace.name(UPDATED_NAME);
        RaceDTO raceDTO = raceMapper.toDto(updatedRace);

        restRaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, raceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(raceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(raceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRaceWithPatch() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race using partial update
        Race partialUpdatedRace = new Race();
        partialUpdatedRace.setId(race.getId());

        partialUpdatedRace.name(UPDATED_NAME);

        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRace))
            )
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRaceWithPatch() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeUpdate = raceRepository.findAll().size();

        // Update the race using partial update
        Race partialUpdatedRace = new Race();
        partialUpdatedRace.setId(race.getId());

        partialUpdatedRace.name(UPDATED_NAME);

        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRace.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRace))
            )
            .andExpect(status().isOk());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
        Race testRace = raceList.get(raceList.size() - 1);
        assertThat(testRace.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, raceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(raceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRace() throws Exception {
        int databaseSizeBeforeUpdate = raceRepository.findAll().size();
        race.setId(count.incrementAndGet());

        // Create the Race
        RaceDTO raceDTO = raceMapper.toDto(race);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRaceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(raceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Race in the database
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRace() throws Exception {
        // Initialize the database
        raceRepository.saveAndFlush(race);

        int databaseSizeBeforeDelete = raceRepository.findAll().size();

        // Delete the race
        restRaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, race.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Race> raceList = raceRepository.findAll();
        assertThat(raceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
