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
import nl.magicshoot.dashboard.domain.Admin;
import nl.magicshoot.dashboard.repository.AdminRepository;
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
 * Integration tests for the {@link AdminResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AdminResourceIT {
    private static final Integer DEFAULT_ADMIN_ID = 1;
    private static final Integer UPDATED_ADMIN_ID = 2;

    private static final String DEFAULT_ADMIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ADMIN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADMIN_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ADMIN_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADMIN_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_ADMIN_PASSWORD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ADMIN_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMIN_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ADMIN_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMIN_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminMockMvc;

    private Admin admin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createEntity(EntityManager em) {
        Admin admin = new Admin()
            .adminId(DEFAULT_ADMIN_ID)
            .adminName(DEFAULT_ADMIN_NAME)
            .adminEmail(DEFAULT_ADMIN_EMAIL)
            .adminPassword(DEFAULT_ADMIN_PASSWORD)
            .adminCreatedAt(DEFAULT_ADMIN_CREATED_AT)
            .adminModifiedAt(DEFAULT_ADMIN_MODIFIED_AT);
        return admin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createUpdatedEntity(EntityManager em) {
        Admin admin = new Admin()
            .adminId(UPDATED_ADMIN_ID)
            .adminName(UPDATED_ADMIN_NAME)
            .adminEmail(UPDATED_ADMIN_EMAIL)
            .adminPassword(UPDATED_ADMIN_PASSWORD)
            .adminCreatedAt(UPDATED_ADMIN_CREATED_AT)
            .adminModifiedAt(UPDATED_ADMIN_MODIFIED_AT);
        return admin;
    }

    @BeforeEach
    public void initTest() {
        admin = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdmin() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();
        // Create the Admin
        restAdminMockMvc
            .perform(post("/api/admins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isCreated());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate + 1);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getAdminId()).isEqualTo(DEFAULT_ADMIN_ID);
        assertThat(testAdmin.getAdminName()).isEqualTo(DEFAULT_ADMIN_NAME);
        assertThat(testAdmin.getAdminEmail()).isEqualTo(DEFAULT_ADMIN_EMAIL);
        assertThat(testAdmin.getAdminPassword()).isEqualTo(DEFAULT_ADMIN_PASSWORD);
        assertThat(testAdmin.getAdminCreatedAt()).isEqualTo(DEFAULT_ADMIN_CREATED_AT);
        assertThat(testAdmin.getAdminModifiedAt()).isEqualTo(DEFAULT_ADMIN_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void createAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();

        // Create the Admin with an existing ID
        admin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminMockMvc
            .perform(post("/api/admins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAdmins() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList
        restAdminMockMvc
            .perform(get("/api/admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admin.getId().intValue())))
            .andExpect(jsonPath("$.[*].adminId").value(hasItem(DEFAULT_ADMIN_ID)))
            .andExpect(jsonPath("$.[*].adminName").value(hasItem(DEFAULT_ADMIN_NAME)))
            .andExpect(jsonPath("$.[*].adminEmail").value(hasItem(DEFAULT_ADMIN_EMAIL)))
            .andExpect(jsonPath("$.[*].adminPassword").value(hasItem(DEFAULT_ADMIN_PASSWORD)))
            .andExpect(jsonPath("$.[*].adminCreatedAt").value(hasItem(DEFAULT_ADMIN_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].adminModifiedAt").value(hasItem(DEFAULT_ADMIN_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    public void getAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get the admin
        restAdminMockMvc
            .perform(get("/api/admins/{id}", admin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(admin.getId().intValue()))
            .andExpect(jsonPath("$.adminId").value(DEFAULT_ADMIN_ID))
            .andExpect(jsonPath("$.adminName").value(DEFAULT_ADMIN_NAME))
            .andExpect(jsonPath("$.adminEmail").value(DEFAULT_ADMIN_EMAIL))
            .andExpect(jsonPath("$.adminPassword").value(DEFAULT_ADMIN_PASSWORD))
            .andExpect(jsonPath("$.adminCreatedAt").value(DEFAULT_ADMIN_CREATED_AT.toString()))
            .andExpect(jsonPath("$.adminModifiedAt").value(DEFAULT_ADMIN_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAdmin() throws Exception {
        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // Update the admin
        Admin updatedAdmin = adminRepository.findById(admin.getId()).get();
        // Disconnect from session so that the updates on updatedAdmin are not directly saved in db
        em.detach(updatedAdmin);
        updatedAdmin
            .adminId(UPDATED_ADMIN_ID)
            .adminName(UPDATED_ADMIN_NAME)
            .adminEmail(UPDATED_ADMIN_EMAIL)
            .adminPassword(UPDATED_ADMIN_PASSWORD)
            .adminCreatedAt(UPDATED_ADMIN_CREATED_AT)
            .adminModifiedAt(UPDATED_ADMIN_MODIFIED_AT);

        restAdminMockMvc
            .perform(put("/api/admins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedAdmin)))
            .andExpect(status().isOk());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getAdminId()).isEqualTo(UPDATED_ADMIN_ID);
        assertThat(testAdmin.getAdminName()).isEqualTo(UPDATED_ADMIN_NAME);
        assertThat(testAdmin.getAdminEmail()).isEqualTo(UPDATED_ADMIN_EMAIL);
        assertThat(testAdmin.getAdminPassword()).isEqualTo(UPDATED_ADMIN_PASSWORD);
        assertThat(testAdmin.getAdminCreatedAt()).isEqualTo(UPDATED_ADMIN_CREATED_AT);
        assertThat(testAdmin.getAdminModifiedAt()).isEqualTo(UPDATED_ADMIN_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingAdmin() throws Exception {
        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminMockMvc
            .perform(put("/api/admins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        int databaseSizeBeforeDelete = adminRepository.findAll().size();

        // Delete the admin
        restAdminMockMvc
            .perform(delete("/api/admins/{id}", admin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
