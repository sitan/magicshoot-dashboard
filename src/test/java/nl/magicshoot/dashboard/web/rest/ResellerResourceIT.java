package nl.magicshoot.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import nl.magicshoot.dashboard.DashboardApp;
import nl.magicshoot.dashboard.domain.Reseller;
import nl.magicshoot.dashboard.repository.ResellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ResellerResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResellerResourceIT {
    private static final Integer DEFAULT_RESELLER_ID = 1;
    private static final Integer UPDATED_RESELLER_ID = 2;

    private static final Integer DEFAULT_ADMIN_ID = 1;
    private static final Integer UPDATED_ADMIN_ID = 2;

    private static final String DEFAULT_RESELLER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RESELLER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_RESELLER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_RESELLER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_RESELLER_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_RESELLER_PASSWORD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RESELLER_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESELLER_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RESELLER_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RESELLER_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ResellerRepository resellerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResellerMockMvc;

    private Reseller reseller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reseller createEntity(EntityManager em) {
        Reseller reseller = new Reseller()
            .resellerId(DEFAULT_RESELLER_ID)
            .adminId(DEFAULT_ADMIN_ID)
            .resellerName(DEFAULT_RESELLER_NAME)
            .resellerEmail(DEFAULT_RESELLER_EMAIL)
            .resellerPassword(DEFAULT_RESELLER_PASSWORD)
            .resellerCreatedAt(DEFAULT_RESELLER_CREATED_AT)
            .resellerModifiedAt(DEFAULT_RESELLER_MODIFIED_AT);
        return reseller;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reseller createUpdatedEntity(EntityManager em) {
        Reseller reseller = new Reseller()
            .resellerId(UPDATED_RESELLER_ID)
            .adminId(UPDATED_ADMIN_ID)
            .resellerName(UPDATED_RESELLER_NAME)
            .resellerEmail(UPDATED_RESELLER_EMAIL)
            .resellerPassword(UPDATED_RESELLER_PASSWORD)
            .resellerCreatedAt(UPDATED_RESELLER_CREATED_AT)
            .resellerModifiedAt(UPDATED_RESELLER_MODIFIED_AT);
        return reseller;
    }

    @BeforeEach
    public void initTest() {
        reseller = createEntity(em);
    }

    @Test
    @Transactional
    public void createReseller() throws Exception {
        int databaseSizeBeforeCreate = resellerRepository.findAll().size();
        // Create the Reseller
        restResellerMockMvc
            .perform(post("/api/resellers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseller)))
            .andExpect(status().isCreated());

        // Validate the Reseller in the database
        List<Reseller> resellerList = resellerRepository.findAll();
        assertThat(resellerList).hasSize(databaseSizeBeforeCreate + 1);
        Reseller testReseller = resellerList.get(resellerList.size() - 1);
        assertThat(testReseller.getResellerId()).isEqualTo(DEFAULT_RESELLER_ID);
        assertThat(testReseller.getAdminId()).isEqualTo(DEFAULT_ADMIN_ID);
        assertThat(testReseller.getResellerName()).isEqualTo(DEFAULT_RESELLER_NAME);
        assertThat(testReseller.getResellerEmail()).isEqualTo(DEFAULT_RESELLER_EMAIL);
        assertThat(testReseller.getResellerPassword()).isEqualTo(DEFAULT_RESELLER_PASSWORD);
        assertThat(testReseller.getResellerCreatedAt()).isEqualTo(DEFAULT_RESELLER_CREATED_AT);
        assertThat(testReseller.getResellerModifiedAt()).isEqualTo(DEFAULT_RESELLER_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void createResellerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resellerRepository.findAll().size();

        // Create the Reseller with an existing ID
        reseller.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResellerMockMvc
            .perform(post("/api/resellers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseller)))
            .andExpect(status().isBadRequest());

        // Validate the Reseller in the database
        List<Reseller> resellerList = resellerRepository.findAll();
        assertThat(resellerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllResellers() throws Exception {
        // Initialize the database
        resellerRepository.saveAndFlush(reseller);

        // Get all the resellerList
        restResellerMockMvc
            .perform(get("/api/resellers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reseller.getId().intValue())))
            .andExpect(jsonPath("$.[*].resellerId").value(hasItem(DEFAULT_RESELLER_ID)))
            .andExpect(jsonPath("$.[*].adminId").value(hasItem(DEFAULT_ADMIN_ID)))
            .andExpect(jsonPath("$.[*].resellerName").value(hasItem(DEFAULT_RESELLER_NAME)))
            .andExpect(jsonPath("$.[*].resellerEmail").value(hasItem(DEFAULT_RESELLER_EMAIL)))
            .andExpect(jsonPath("$.[*].resellerPassword").value(hasItem(DEFAULT_RESELLER_PASSWORD)))
            .andExpect(jsonPath("$.[*].resellerCreatedAt").value(hasItem(DEFAULT_RESELLER_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].resellerModifiedAt").value(hasItem(DEFAULT_RESELLER_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    public void getReseller() throws Exception {
        // Initialize the database
        resellerRepository.saveAndFlush(reseller);

        // Get the reseller
        restResellerMockMvc
            .perform(get("/api/resellers/{id}", reseller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reseller.getId().intValue()))
            .andExpect(jsonPath("$.resellerId").value(DEFAULT_RESELLER_ID))
            .andExpect(jsonPath("$.adminId").value(DEFAULT_ADMIN_ID))
            .andExpect(jsonPath("$.resellerName").value(DEFAULT_RESELLER_NAME))
            .andExpect(jsonPath("$.resellerEmail").value(DEFAULT_RESELLER_EMAIL))
            .andExpect(jsonPath("$.resellerPassword").value(DEFAULT_RESELLER_PASSWORD))
            .andExpect(jsonPath("$.resellerCreatedAt").value(DEFAULT_RESELLER_CREATED_AT.toString()))
            .andExpect(jsonPath("$.resellerModifiedAt").value(DEFAULT_RESELLER_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingReseller() throws Exception {
        // Get the reseller
        restResellerMockMvc.perform(get("/api/resellers/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReseller() throws Exception {
        // Initialize the database
        resellerRepository.saveAndFlush(reseller);

        int databaseSizeBeforeUpdate = resellerRepository.findAll().size();

        // Update the reseller
        Reseller updatedReseller = resellerRepository.findById(reseller.getId()).get();
        // Disconnect from session so that the updates on updatedReseller are not directly saved in db
        em.detach(updatedReseller);
        updatedReseller
            .resellerId(UPDATED_RESELLER_ID)
            .adminId(UPDATED_ADMIN_ID)
            .resellerName(UPDATED_RESELLER_NAME)
            .resellerEmail(UPDATED_RESELLER_EMAIL)
            .resellerPassword(UPDATED_RESELLER_PASSWORD)
            .resellerCreatedAt(UPDATED_RESELLER_CREATED_AT)
            .resellerModifiedAt(UPDATED_RESELLER_MODIFIED_AT);

        restResellerMockMvc
            .perform(
                put("/api/resellers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedReseller))
            )
            .andExpect(status().isOk());

        // Validate the Reseller in the database
        List<Reseller> resellerList = resellerRepository.findAll();
        assertThat(resellerList).hasSize(databaseSizeBeforeUpdate);
        Reseller testReseller = resellerList.get(resellerList.size() - 1);
        assertThat(testReseller.getResellerId()).isEqualTo(UPDATED_RESELLER_ID);
        assertThat(testReseller.getAdminId()).isEqualTo(UPDATED_ADMIN_ID);
        assertThat(testReseller.getResellerName()).isEqualTo(UPDATED_RESELLER_NAME);
        assertThat(testReseller.getResellerEmail()).isEqualTo(UPDATED_RESELLER_EMAIL);
        assertThat(testReseller.getResellerPassword()).isEqualTo(UPDATED_RESELLER_PASSWORD);
        assertThat(testReseller.getResellerCreatedAt()).isEqualTo(UPDATED_RESELLER_CREATED_AT);
        assertThat(testReseller.getResellerModifiedAt()).isEqualTo(UPDATED_RESELLER_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingReseller() throws Exception {
        int databaseSizeBeforeUpdate = resellerRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResellerMockMvc
            .perform(put("/api/resellers").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseller)))
            .andExpect(status().isBadRequest());

        // Validate the Reseller in the database
        List<Reseller> resellerList = resellerRepository.findAll();
        assertThat(resellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReseller() throws Exception {
        // Initialize the database
        resellerRepository.saveAndFlush(reseller);

        int databaseSizeBeforeDelete = resellerRepository.findAll().size();

        // Delete the reseller
        restResellerMockMvc
            .perform(delete("/api/resellers/{id}", reseller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reseller> resellerList = resellerRepository.findAll();
        assertThat(resellerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
