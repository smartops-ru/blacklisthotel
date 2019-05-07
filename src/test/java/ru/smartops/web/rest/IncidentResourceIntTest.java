package ru.smartops.web.rest;

import ru.smartops.BlacklisthotelApp;

import ru.smartops.domain.Incident;
import ru.smartops.repository.IncidentRepository;
import ru.smartops.service.IncidentService;
import ru.smartops.web.rest.errors.ExceptionTranslator;
import ru.smartops.service.dto.IncidentCriteria;
import ru.smartops.service.IncidentQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static ru.smartops.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.smartops.domain.enumeration.IncidentType;
/**
 * Test class for the IncidentResource REST controller.
 *
 * @see IncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlacklisthotelApp.class)
public class IncidentResourceIntTest {

    private static final IncidentType DEFAULT_TYPE = IncidentType.THEFT;
    private static final IncidentType UPDATED_TYPE = IncidentType.PETTY_TEFT;

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_HOTEL = "AAAAAAAAAA";
    private static final String UPDATED_HOTEL = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_NAME_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_NAME_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_PERSON_DOCUMENT_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PERSON_DOCUMENT_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private IncidentService incidentService;

    @Autowired
    private IncidentQueryService incidentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restIncidentMockMvc;

    private Incident incident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IncidentResource incidentResource = new IncidentResource(incidentService, incidentQueryService);
        this.restIncidentMockMvc = MockMvcBuilders.standaloneSetup(incidentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Incident createEntity(EntityManager em) {
        Incident incident = new Incident()
            .type(DEFAULT_TYPE)
            .date(DEFAULT_DATE)
            .place(DEFAULT_PLACE)
            .hotel(DEFAULT_HOTEL)
            .personNameHash(DEFAULT_PERSON_NAME_HASH)
            .personDocumentHash(DEFAULT_PERSON_DOCUMENT_HASH)
            .phoneNumberHash(DEFAULT_PHONE_NUMBER_HASH)
            .description(DEFAULT_DESCRIPTION);
        return incident;
    }

    @Before
    public void initTest() {
        incident = createEntity(em);
    }

    @Test
    @Transactional
    public void createIncident() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isCreated());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate + 1);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIncident.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testIncident.getPlace()).isEqualTo(DEFAULT_PLACE);
        assertThat(testIncident.getHotel()).isEqualTo(DEFAULT_HOTEL);
        assertThat(testIncident.getPersonNameHash()).isEqualTo(DEFAULT_PERSON_NAME_HASH);
        assertThat(testIncident.getPersonDocumentHash()).isEqualTo(DEFAULT_PERSON_DOCUMENT_HASH);
        assertThat(testIncident.getPhoneNumberHash()).isEqualTo(DEFAULT_PHONE_NUMBER_HASH);
        assertThat(testIncident.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = incidentRepository.findAll().size();

        // Create the Incident with an existing ID
        incident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentMockMvc.perform(post("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIncidents() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE.toString())))
            .andExpect(jsonPath("$.[*].hotel").value(hasItem(DEFAULT_HOTEL.toString())))
            .andExpect(jsonPath("$.[*].personNameHash").value(hasItem(DEFAULT_PERSON_NAME_HASH.toString())))
            .andExpect(jsonPath("$.[*].personDocumentHash").value(hasItem(DEFAULT_PERSON_DOCUMENT_HASH.toString())))
            .andExpect(jsonPath("$.[*].phoneNumberHash").value(hasItem(DEFAULT_PHONE_NUMBER_HASH.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getIncident() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", incident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(incident.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.place").value(DEFAULT_PLACE.toString()))
            .andExpect(jsonPath("$.hotel").value(DEFAULT_HOTEL.toString()))
            .andExpect(jsonPath("$.personNameHash").value(DEFAULT_PERSON_NAME_HASH.toString()))
            .andExpect(jsonPath("$.personDocumentHash").value(DEFAULT_PERSON_DOCUMENT_HASH.toString()))
            .andExpect(jsonPath("$.phoneNumberHash").value(DEFAULT_PHONE_NUMBER_HASH.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllIncidentsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where type equals to DEFAULT_TYPE
        defaultIncidentShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the incidentList where type equals to UPDATED_TYPE
        defaultIncidentShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultIncidentShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the incidentList where type equals to UPDATED_TYPE
        defaultIncidentShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where type is not null
        defaultIncidentShouldBeFound("type.specified=true");

        // Get all the incidentList where type is null
        defaultIncidentShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where date equals to DEFAULT_DATE
        defaultIncidentShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the incidentList where date equals to UPDATED_DATE
        defaultIncidentShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where date in DEFAULT_DATE or UPDATED_DATE
        defaultIncidentShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the incidentList where date equals to UPDATED_DATE
        defaultIncidentShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where date is not null
        defaultIncidentShouldBeFound("date.specified=true");

        // Get all the incidentList where date is null
        defaultIncidentShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where date greater than or equals to DEFAULT_DATE
        defaultIncidentShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the incidentList where date greater than or equals to UPDATED_DATE
        defaultIncidentShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where date less than or equals to DEFAULT_DATE
        defaultIncidentShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the incidentList where date less than or equals to UPDATED_DATE
        defaultIncidentShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllIncidentsByPlaceIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where place equals to DEFAULT_PLACE
        defaultIncidentShouldBeFound("place.equals=" + DEFAULT_PLACE);

        // Get all the incidentList where place equals to UPDATED_PLACE
        defaultIncidentShouldNotBeFound("place.equals=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPlaceIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where place in DEFAULT_PLACE or UPDATED_PLACE
        defaultIncidentShouldBeFound("place.in=" + DEFAULT_PLACE + "," + UPDATED_PLACE);

        // Get all the incidentList where place equals to UPDATED_PLACE
        defaultIncidentShouldNotBeFound("place.in=" + UPDATED_PLACE);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPlaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where place is not null
        defaultIncidentShouldBeFound("place.specified=true");

        // Get all the incidentList where place is null
        defaultIncidentShouldNotBeFound("place.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByHotelIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where hotel equals to DEFAULT_HOTEL
        defaultIncidentShouldBeFound("hotel.equals=" + DEFAULT_HOTEL);

        // Get all the incidentList where hotel equals to UPDATED_HOTEL
        defaultIncidentShouldNotBeFound("hotel.equals=" + UPDATED_HOTEL);
    }

    @Test
    @Transactional
    public void getAllIncidentsByHotelIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where hotel in DEFAULT_HOTEL or UPDATED_HOTEL
        defaultIncidentShouldBeFound("hotel.in=" + DEFAULT_HOTEL + "," + UPDATED_HOTEL);

        // Get all the incidentList where hotel equals to UPDATED_HOTEL
        defaultIncidentShouldNotBeFound("hotel.in=" + UPDATED_HOTEL);
    }

    @Test
    @Transactional
    public void getAllIncidentsByHotelIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where hotel is not null
        defaultIncidentShouldBeFound("hotel.specified=true");

        // Get all the incidentList where hotel is null
        defaultIncidentShouldNotBeFound("hotel.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByPersonNameHashIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where personNameHash equals to DEFAULT_PERSON_NAME_HASH
        defaultIncidentShouldBeFound("personNameHash.equals=" + DEFAULT_PERSON_NAME_HASH);

        // Get all the incidentList where personNameHash equals to UPDATED_PERSON_NAME_HASH
        defaultIncidentShouldNotBeFound("personNameHash.equals=" + UPDATED_PERSON_NAME_HASH);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPersonNameHashIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where personNameHash in DEFAULT_PERSON_NAME_HASH or UPDATED_PERSON_NAME_HASH
        defaultIncidentShouldBeFound("personNameHash.in=" + DEFAULT_PERSON_NAME_HASH + "," + UPDATED_PERSON_NAME_HASH);

        // Get all the incidentList where personNameHash equals to UPDATED_PERSON_NAME_HASH
        defaultIncidentShouldNotBeFound("personNameHash.in=" + UPDATED_PERSON_NAME_HASH);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPersonNameHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where personNameHash is not null
        defaultIncidentShouldBeFound("personNameHash.specified=true");

        // Get all the incidentList where personNameHash is null
        defaultIncidentShouldNotBeFound("personNameHash.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByPersonDocumentHashIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where personDocumentHash equals to DEFAULT_PERSON_DOCUMENT_HASH
        defaultIncidentShouldBeFound("personDocumentHash.equals=" + DEFAULT_PERSON_DOCUMENT_HASH);

        // Get all the incidentList where personDocumentHash equals to UPDATED_PERSON_DOCUMENT_HASH
        defaultIncidentShouldNotBeFound("personDocumentHash.equals=" + UPDATED_PERSON_DOCUMENT_HASH);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPersonDocumentHashIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where personDocumentHash in DEFAULT_PERSON_DOCUMENT_HASH or UPDATED_PERSON_DOCUMENT_HASH
        defaultIncidentShouldBeFound("personDocumentHash.in=" + DEFAULT_PERSON_DOCUMENT_HASH + "," + UPDATED_PERSON_DOCUMENT_HASH);

        // Get all the incidentList where personDocumentHash equals to UPDATED_PERSON_DOCUMENT_HASH
        defaultIncidentShouldNotBeFound("personDocumentHash.in=" + UPDATED_PERSON_DOCUMENT_HASH);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPersonDocumentHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where personDocumentHash is not null
        defaultIncidentShouldBeFound("personDocumentHash.specified=true");

        // Get all the incidentList where personDocumentHash is null
        defaultIncidentShouldNotBeFound("personDocumentHash.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByPhoneNumberHashIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where phoneNumberHash equals to DEFAULT_PHONE_NUMBER_HASH
        defaultIncidentShouldBeFound("phoneNumberHash.equals=" + DEFAULT_PHONE_NUMBER_HASH);

        // Get all the incidentList where phoneNumberHash equals to UPDATED_PHONE_NUMBER_HASH
        defaultIncidentShouldNotBeFound("phoneNumberHash.equals=" + UPDATED_PHONE_NUMBER_HASH);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPhoneNumberHashIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where phoneNumberHash in DEFAULT_PHONE_NUMBER_HASH or UPDATED_PHONE_NUMBER_HASH
        defaultIncidentShouldBeFound("phoneNumberHash.in=" + DEFAULT_PHONE_NUMBER_HASH + "," + UPDATED_PHONE_NUMBER_HASH);

        // Get all the incidentList where phoneNumberHash equals to UPDATED_PHONE_NUMBER_HASH
        defaultIncidentShouldNotBeFound("phoneNumberHash.in=" + UPDATED_PHONE_NUMBER_HASH);
    }

    @Test
    @Transactional
    public void getAllIncidentsByPhoneNumberHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where phoneNumberHash is not null
        defaultIncidentShouldBeFound("phoneNumberHash.specified=true");

        // Get all the incidentList where phoneNumberHash is null
        defaultIncidentShouldNotBeFound("phoneNumberHash.specified=false");
    }

    @Test
    @Transactional
    public void getAllIncidentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where description equals to DEFAULT_DESCRIPTION
        defaultIncidentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the incidentList where description equals to UPDATED_DESCRIPTION
        defaultIncidentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultIncidentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the incidentList where description equals to UPDATED_DESCRIPTION
        defaultIncidentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllIncidentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        incidentRepository.saveAndFlush(incident);

        // Get all the incidentList where description is not null
        defaultIncidentShouldBeFound("description.specified=true");

        // Get all the incidentList where description is null
        defaultIncidentShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultIncidentShouldBeFound(String filter) throws Exception {
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incident.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].place").value(hasItem(DEFAULT_PLACE)))
            .andExpect(jsonPath("$.[*].hotel").value(hasItem(DEFAULT_HOTEL)))
            .andExpect(jsonPath("$.[*].personNameHash").value(hasItem(DEFAULT_PERSON_NAME_HASH)))
            .andExpect(jsonPath("$.[*].personDocumentHash").value(hasItem(DEFAULT_PERSON_DOCUMENT_HASH)))
            .andExpect(jsonPath("$.[*].phoneNumberHash").value(hasItem(DEFAULT_PHONE_NUMBER_HASH)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restIncidentMockMvc.perform(get("/api/incidents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultIncidentShouldNotBeFound(String filter) throws Exception {
        restIncidentMockMvc.perform(get("/api/incidents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restIncidentMockMvc.perform(get("/api/incidents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingIncident() throws Exception {
        // Get the incident
        restIncidentMockMvc.perform(get("/api/incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIncident() throws Exception {
        // Initialize the database
        incidentService.save(incident);

        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Update the incident
        Incident updatedIncident = incidentRepository.findById(incident.getId()).get();
        // Disconnect from session so that the updates on updatedIncident are not directly saved in db
        em.detach(updatedIncident);
        updatedIncident
            .type(UPDATED_TYPE)
            .date(UPDATED_DATE)
            .place(UPDATED_PLACE)
            .hotel(UPDATED_HOTEL)
            .personNameHash(UPDATED_PERSON_NAME_HASH)
            .personDocumentHash(UPDATED_PERSON_DOCUMENT_HASH)
            .phoneNumberHash(UPDATED_PHONE_NUMBER_HASH)
            .description(UPDATED_DESCRIPTION);

        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedIncident)))
            .andExpect(status().isOk());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
        Incident testIncident = incidentList.get(incidentList.size() - 1);
        assertThat(testIncident.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIncident.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testIncident.getPlace()).isEqualTo(UPDATED_PLACE);
        assertThat(testIncident.getHotel()).isEqualTo(UPDATED_HOTEL);
        assertThat(testIncident.getPersonNameHash()).isEqualTo(UPDATED_PERSON_NAME_HASH);
        assertThat(testIncident.getPersonDocumentHash()).isEqualTo(UPDATED_PERSON_DOCUMENT_HASH);
        assertThat(testIncident.getPhoneNumberHash()).isEqualTo(UPDATED_PHONE_NUMBER_HASH);
        assertThat(testIncident.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingIncident() throws Exception {
        int databaseSizeBeforeUpdate = incidentRepository.findAll().size();

        // Create the Incident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentMockMvc.perform(put("/api/incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(incident)))
            .andExpect(status().isBadRequest());

        // Validate the Incident in the database
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIncident() throws Exception {
        // Initialize the database
        incidentService.save(incident);

        int databaseSizeBeforeDelete = incidentRepository.findAll().size();

        // Delete the incident
        restIncidentMockMvc.perform(delete("/api/incidents/{id}", incident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Incident> incidentList = incidentRepository.findAll();
        assertThat(incidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = new Incident();
        incident1.setId(1L);
        Incident incident2 = new Incident();
        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);
        incident2.setId(2L);
        assertThat(incident1).isNotEqualTo(incident2);
        incident1.setId(null);
        assertThat(incident1).isNotEqualTo(incident2);
    }
}
