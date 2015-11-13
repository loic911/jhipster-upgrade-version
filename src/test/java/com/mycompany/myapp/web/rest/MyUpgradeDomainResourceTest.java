package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.Application;
import com.mycompany.myapp.domain.MyUpgradeDomain;
import com.mycompany.myapp.repository.MyUpgradeDomainRepository;
import com.mycompany.myapp.repository.search.MyUpgradeDomainSearchRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the MyUpgradeDomainResource REST controller.
 *
 * @see MyUpgradeDomainResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class MyUpgradeDomainResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final ZonedDateTime DEFAULT_MY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_MY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_MY_DATE_STR = dateTimeFormatter.format(DEFAULT_MY_DATE);

    @Inject
    private MyUpgradeDomainRepository myUpgradeDomainRepository;

    @Inject
    private MyUpgradeDomainSearchRepository myUpgradeDomainSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restMyUpgradeDomainMockMvc;

    private MyUpgradeDomain myUpgradeDomain;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MyUpgradeDomainResource myUpgradeDomainResource = new MyUpgradeDomainResource();
        ReflectionTestUtils.setField(myUpgradeDomainResource, "myUpgradeDomainRepository", myUpgradeDomainRepository);
        ReflectionTestUtils.setField(myUpgradeDomainResource, "myUpgradeDomainSearchRepository", myUpgradeDomainSearchRepository);
        this.restMyUpgradeDomainMockMvc = MockMvcBuilders.standaloneSetup(myUpgradeDomainResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        myUpgradeDomain = new MyUpgradeDomain();
        myUpgradeDomain.setName(DEFAULT_NAME);
        myUpgradeDomain.setMyDate(DEFAULT_MY_DATE);
    }

    @Test
    @Transactional
    public void createMyUpgradeDomain() throws Exception {
        int databaseSizeBeforeCreate = myUpgradeDomainRepository.findAll().size();

        // Create the MyUpgradeDomain

        restMyUpgradeDomainMockMvc.perform(post("/api/myUpgradeDomains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myUpgradeDomain)))
                .andExpect(status().isCreated());

        // Validate the MyUpgradeDomain in the database
        List<MyUpgradeDomain> myUpgradeDomains = myUpgradeDomainRepository.findAll();
        assertThat(myUpgradeDomains).hasSize(databaseSizeBeforeCreate + 1);
        MyUpgradeDomain testMyUpgradeDomain = myUpgradeDomains.get(myUpgradeDomains.size() - 1);
        assertThat(testMyUpgradeDomain.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMyUpgradeDomain.getMyDate()).isEqualTo(DEFAULT_MY_DATE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = myUpgradeDomainRepository.findAll().size();
        // set the field null
        myUpgradeDomain.setName(null);

        // Create the MyUpgradeDomain, which fails.

        restMyUpgradeDomainMockMvc.perform(post("/api/myUpgradeDomains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myUpgradeDomain)))
                .andExpect(status().isBadRequest());

        List<MyUpgradeDomain> myUpgradeDomains = myUpgradeDomainRepository.findAll();
        assertThat(myUpgradeDomains).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMyUpgradeDomains() throws Exception {
        // Initialize the database
        myUpgradeDomainRepository.saveAndFlush(myUpgradeDomain);

        // Get all the myUpgradeDomains
        restMyUpgradeDomainMockMvc.perform(get("/api/myUpgradeDomains"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(myUpgradeDomain.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].myDate").value(hasItem(DEFAULT_MY_DATE_STR)));
    }

    @Test
    @Transactional
    public void getMyUpgradeDomain() throws Exception {
        // Initialize the database
        myUpgradeDomainRepository.saveAndFlush(myUpgradeDomain);

        // Get the myUpgradeDomain
        restMyUpgradeDomainMockMvc.perform(get("/api/myUpgradeDomains/{id}", myUpgradeDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(myUpgradeDomain.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.myDate").value(DEFAULT_MY_DATE_STR));
    }

    @Test
    @Transactional
    public void getNonExistingMyUpgradeDomain() throws Exception {
        // Get the myUpgradeDomain
        restMyUpgradeDomainMockMvc.perform(get("/api/myUpgradeDomains/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyUpgradeDomain() throws Exception {
        // Initialize the database
        myUpgradeDomainRepository.saveAndFlush(myUpgradeDomain);

		int databaseSizeBeforeUpdate = myUpgradeDomainRepository.findAll().size();

        // Update the myUpgradeDomain
        myUpgradeDomain.setName(UPDATED_NAME);
        myUpgradeDomain.setMyDate(UPDATED_MY_DATE);

        restMyUpgradeDomainMockMvc.perform(put("/api/myUpgradeDomains")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(myUpgradeDomain)))
                .andExpect(status().isOk());

        // Validate the MyUpgradeDomain in the database
        List<MyUpgradeDomain> myUpgradeDomains = myUpgradeDomainRepository.findAll();
        assertThat(myUpgradeDomains).hasSize(databaseSizeBeforeUpdate);
        MyUpgradeDomain testMyUpgradeDomain = myUpgradeDomains.get(myUpgradeDomains.size() - 1);
        assertThat(testMyUpgradeDomain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMyUpgradeDomain.getMyDate()).isEqualTo(UPDATED_MY_DATE);
    }

    @Test
    @Transactional
    public void deleteMyUpgradeDomain() throws Exception {
        // Initialize the database
        myUpgradeDomainRepository.saveAndFlush(myUpgradeDomain);

		int databaseSizeBeforeDelete = myUpgradeDomainRepository.findAll().size();

        // Get the myUpgradeDomain
        restMyUpgradeDomainMockMvc.perform(delete("/api/myUpgradeDomains/{id}", myUpgradeDomain.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<MyUpgradeDomain> myUpgradeDomains = myUpgradeDomainRepository.findAll();
        assertThat(myUpgradeDomains).hasSize(databaseSizeBeforeDelete - 1);
    }
}
