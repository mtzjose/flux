package com.magma.flux.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.magma.flux.IntegrationTest;
import com.magma.flux.domain.Person;
import com.magma.flux.repository.PersonRepository;
import com.magma.flux.service.dto.PersonDTO;
import com.magma.flux.service.mapper.PersonMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link PersonResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonResourceIT {

    private static final String DEFAULT_META = "AAAAAAAAAA";
    private static final String UPDATED_META = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PROFILE_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PROFILE_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PROFILE_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PROFILE_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLENAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLENAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIO = "AAAAAAAAAA";
    private static final String UPDATED_BIO = "BBBBBBBBBB";

    private static final Long DEFAULT_SCHOOL = 1L;
    private static final Long UPDATED_SCHOOL = 2L;

    private static final Long DEFAULT_MAJOR = 1L;
    private static final Long UPDATED_MAJOR = 2L;

    private static final String DEFAULT_SOCIAL_LINKS = "AAAAAAAAAA";
    private static final String UPDATED_SOCIAL_LINKS = "BBBBBBBBBB";

    private static final Long DEFAULT_NATIONALITY_ID = 1L;
    private static final Long UPDATED_NATIONALITY_ID = 2L;

    private static final Long DEFAULT_GENDER_ID = 1L;
    private static final Long UPDATED_GENDER_ID = 2L;

    private static final Long DEFAULT_PRONOUN_ID = 1L;
    private static final Long UPDATED_PRONOUN_ID = 2L;

    private static final Long DEFAULT_RACE_ID = 1L;
    private static final Long UPDATED_RACE_ID = 2L;

    private static final Long DEFAULT_ADDRESS_ID = 1L;
    private static final Long UPDATED_ADDRESS_ID = 2L;

    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTHDATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/people";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonMapper personMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonMockMvc;

    private Person person;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createEntity(EntityManager em) {
        Person person = new Person()
            .meta(DEFAULT_META)
            .profilePicture(DEFAULT_PROFILE_PICTURE)
            .profilePictureContentType(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE)
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .middlename(DEFAULT_MIDDLENAME)
            .bio(DEFAULT_BIO)
            .school(DEFAULT_SCHOOL)
            .major(DEFAULT_MAJOR)
            .socialLinks(DEFAULT_SOCIAL_LINKS)
            .nationalityId(DEFAULT_NATIONALITY_ID)
            .genderId(DEFAULT_GENDER_ID)
            .pronounId(DEFAULT_PRONOUN_ID)
            .raceId(DEFAULT_RACE_ID)
            .addressId(DEFAULT_ADDRESS_ID)
            .birthdate(DEFAULT_BIRTHDATE);
        return person;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Person createUpdatedEntity(EntityManager em) {
        Person person = new Person()
            .meta(UPDATED_META)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .profilePictureContentType(UPDATED_PROFILE_PICTURE_CONTENT_TYPE)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .middlename(UPDATED_MIDDLENAME)
            .bio(UPDATED_BIO)
            .school(UPDATED_SCHOOL)
            .major(UPDATED_MAJOR)
            .socialLinks(UPDATED_SOCIAL_LINKS)
            .nationalityId(UPDATED_NATIONALITY_ID)
            .genderId(UPDATED_GENDER_ID)
            .pronounId(UPDATED_PRONOUN_ID)
            .raceId(UPDATED_RACE_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .birthdate(UPDATED_BIRTHDATE);
        return person;
    }

    @BeforeEach
    public void initTest() {
        person = createEntity(em);
    }

    @Test
    @Transactional
    void createPerson() throws Exception {
        int databaseSizeBeforeCreate = personRepository.findAll().size();
        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isCreated());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate + 1);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getMeta()).isEqualTo(DEFAULT_META);
        assertThat(testPerson.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testPerson.getProfilePictureContentType()).isEqualTo(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE);
        assertThat(testPerson.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPerson.getMiddlename()).isEqualTo(DEFAULT_MIDDLENAME);
        assertThat(testPerson.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testPerson.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testPerson.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testPerson.getSocialLinks()).isEqualTo(DEFAULT_SOCIAL_LINKS);
        assertThat(testPerson.getNationalityId()).isEqualTo(DEFAULT_NATIONALITY_ID);
        assertThat(testPerson.getGenderId()).isEqualTo(DEFAULT_GENDER_ID);
        assertThat(testPerson.getPronounId()).isEqualTo(DEFAULT_PRONOUN_ID);
        assertThat(testPerson.getRaceId()).isEqualTo(DEFAULT_RACE_ID);
        assertThat(testPerson.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testPerson.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
    }

    @Test
    @Transactional
    void createPersonWithExistingId() throws Exception {
        // Create the Person with an existing ID
        person.setId(1L);
        PersonDTO personDTO = personMapper.toDto(person);

        int databaseSizeBeforeCreate = personRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPeople() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get all the personList
        restPersonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(person.getId().intValue())))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META.toString())))
            .andExpect(jsonPath("$.[*].profilePictureContentType").value(hasItem(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_PROFILE_PICTURE))))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].middlename").value(hasItem(DEFAULT_MIDDLENAME)))
            .andExpect(jsonPath("$.[*].bio").value(hasItem(DEFAULT_BIO.toString())))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.intValue())))
            .andExpect(jsonPath("$.[*].socialLinks").value(hasItem(DEFAULT_SOCIAL_LINKS.toString())))
            .andExpect(jsonPath("$.[*].nationalityId").value(hasItem(DEFAULT_NATIONALITY_ID.intValue())))
            .andExpect(jsonPath("$.[*].genderId").value(hasItem(DEFAULT_GENDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].pronounId").value(hasItem(DEFAULT_PRONOUN_ID.intValue())))
            .andExpect(jsonPath("$.[*].raceId").value(hasItem(DEFAULT_RACE_ID.intValue())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.intValue())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())));
    }

    @Test
    @Transactional
    void getPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        // Get the person
        restPersonMockMvc
            .perform(get(ENTITY_API_URL_ID, person.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(person.getId().intValue()))
            .andExpect(jsonPath("$.meta").value(DEFAULT_META.toString()))
            .andExpect(jsonPath("$.profilePictureContentType").value(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.profilePicture").value(Base64Utils.encodeToString(DEFAULT_PROFILE_PICTURE)))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.middlename").value(DEFAULT_MIDDLENAME))
            .andExpect(jsonPath("$.bio").value(DEFAULT_BIO.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.intValue()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.intValue()))
            .andExpect(jsonPath("$.socialLinks").value(DEFAULT_SOCIAL_LINKS.toString()))
            .andExpect(jsonPath("$.nationalityId").value(DEFAULT_NATIONALITY_ID.intValue()))
            .andExpect(jsonPath("$.genderId").value(DEFAULT_GENDER_ID.intValue()))
            .andExpect(jsonPath("$.pronounId").value(DEFAULT_PRONOUN_ID.intValue()))
            .andExpect(jsonPath("$.raceId").value(DEFAULT_RACE_ID.intValue()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID.intValue()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPerson() throws Exception {
        // Get the person
        restPersonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person
        Person updatedPerson = personRepository.findById(person.getId()).get();
        // Disconnect from session so that the updates on updatedPerson are not directly saved in db
        em.detach(updatedPerson);
        updatedPerson
            .meta(UPDATED_META)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .profilePictureContentType(UPDATED_PROFILE_PICTURE_CONTENT_TYPE)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .middlename(UPDATED_MIDDLENAME)
            .bio(UPDATED_BIO)
            .school(UPDATED_SCHOOL)
            .major(UPDATED_MAJOR)
            .socialLinks(UPDATED_SOCIAL_LINKS)
            .nationalityId(UPDATED_NATIONALITY_ID)
            .genderId(UPDATED_GENDER_ID)
            .pronounId(UPDATED_PRONOUN_ID)
            .raceId(UPDATED_RACE_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .birthdate(UPDATED_BIRTHDATE);
        PersonDTO personDTO = personMapper.toDto(updatedPerson);

        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testPerson.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testPerson.getProfilePictureContentType()).isEqualTo(UPDATED_PROFILE_PICTURE_CONTENT_TYPE);
        assertThat(testPerson.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPerson.getMiddlename()).isEqualTo(UPDATED_MIDDLENAME);
        assertThat(testPerson.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testPerson.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testPerson.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testPerson.getSocialLinks()).isEqualTo(UPDATED_SOCIAL_LINKS);
        assertThat(testPerson.getNationalityId()).isEqualTo(UPDATED_NATIONALITY_ID);
        assertThat(testPerson.getGenderId()).isEqualTo(UPDATED_GENDER_ID);
        assertThat(testPerson.getPronounId()).isEqualTo(UPDATED_PRONOUN_ID);
        assertThat(testPerson.getRaceId()).isEqualTo(UPDATED_RACE_ID);
        assertThat(testPerson.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testPerson.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void putNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .firstname(UPDATED_FIRSTNAME)
            .major(UPDATED_MAJOR)
            .socialLinks(UPDATED_SOCIAL_LINKS)
            .nationalityId(UPDATED_NATIONALITY_ID)
            .genderId(UPDATED_GENDER_ID)
            .pronounId(UPDATED_PRONOUN_ID)
            .raceId(UPDATED_RACE_ID);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getMeta()).isEqualTo(DEFAULT_META);
        assertThat(testPerson.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testPerson.getProfilePictureContentType()).isEqualTo(DEFAULT_PROFILE_PICTURE_CONTENT_TYPE);
        assertThat(testPerson.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testPerson.getMiddlename()).isEqualTo(DEFAULT_MIDDLENAME);
        assertThat(testPerson.getBio()).isEqualTo(DEFAULT_BIO);
        assertThat(testPerson.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testPerson.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testPerson.getSocialLinks()).isEqualTo(UPDATED_SOCIAL_LINKS);
        assertThat(testPerson.getNationalityId()).isEqualTo(UPDATED_NATIONALITY_ID);
        assertThat(testPerson.getGenderId()).isEqualTo(UPDATED_GENDER_ID);
        assertThat(testPerson.getPronounId()).isEqualTo(UPDATED_PRONOUN_ID);
        assertThat(testPerson.getRaceId()).isEqualTo(UPDATED_RACE_ID);
        assertThat(testPerson.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testPerson.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
    }

    @Test
    @Transactional
    void fullUpdatePersonWithPatch() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeUpdate = personRepository.findAll().size();

        // Update the person using partial update
        Person partialUpdatedPerson = new Person();
        partialUpdatedPerson.setId(person.getId());

        partialUpdatedPerson
            .meta(UPDATED_META)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .profilePictureContentType(UPDATED_PROFILE_PICTURE_CONTENT_TYPE)
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .middlename(UPDATED_MIDDLENAME)
            .bio(UPDATED_BIO)
            .school(UPDATED_SCHOOL)
            .major(UPDATED_MAJOR)
            .socialLinks(UPDATED_SOCIAL_LINKS)
            .nationalityId(UPDATED_NATIONALITY_ID)
            .genderId(UPDATED_GENDER_ID)
            .pronounId(UPDATED_PRONOUN_ID)
            .raceId(UPDATED_RACE_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .birthdate(UPDATED_BIRTHDATE);

        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPerson.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPerson))
            )
            .andExpect(status().isOk());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
        Person testPerson = personList.get(personList.size() - 1);
        assertThat(testPerson.getMeta()).isEqualTo(UPDATED_META);
        assertThat(testPerson.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testPerson.getProfilePictureContentType()).isEqualTo(UPDATED_PROFILE_PICTURE_CONTENT_TYPE);
        assertThat(testPerson.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testPerson.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testPerson.getMiddlename()).isEqualTo(UPDATED_MIDDLENAME);
        assertThat(testPerson.getBio()).isEqualTo(UPDATED_BIO);
        assertThat(testPerson.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testPerson.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testPerson.getSocialLinks()).isEqualTo(UPDATED_SOCIAL_LINKS);
        assertThat(testPerson.getNationalityId()).isEqualTo(UPDATED_NATIONALITY_ID);
        assertThat(testPerson.getGenderId()).isEqualTo(UPDATED_GENDER_ID);
        assertThat(testPerson.getPronounId()).isEqualTo(UPDATED_PRONOUN_ID);
        assertThat(testPerson.getRaceId()).isEqualTo(UPDATED_RACE_ID);
        assertThat(testPerson.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testPerson.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    void patchNonExistingPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPerson() throws Exception {
        int databaseSizeBeforeUpdate = personRepository.findAll().size();
        person.setId(count.incrementAndGet());

        // Create the Person
        PersonDTO personDTO = personMapper.toDto(person);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(personDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Person in the database
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePerson() throws Exception {
        // Initialize the database
        personRepository.saveAndFlush(person);

        int databaseSizeBeforeDelete = personRepository.findAll().size();

        // Delete the person
        restPersonMockMvc
            .perform(delete(ENTITY_API_URL_ID, person.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Person> personList = personRepository.findAll();
        assertThat(personList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
