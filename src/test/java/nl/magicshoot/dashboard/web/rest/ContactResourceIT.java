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
import nl.magicshoot.dashboard.domain.Contact;
import nl.magicshoot.dashboard.repository.ContactRepository;
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
 * Integration tests for the {@link ContactResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContactResourceIT {
    private static final Integer DEFAULT_CONTACT_ID = 1;
    private static final Integer UPDATED_CONTACT_ID = 2;

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final String DEFAULT_CONTACT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PASSWORD = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CONTACT_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTACT_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CONTACT_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CONTACT_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContactMockMvc;

    private Contact contact;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createEntity(EntityManager em) {
        Contact contact = new Contact()
            .contactId(DEFAULT_CONTACT_ID)
            .contactName(DEFAULT_CONTACT_NAME)
            .companyId(DEFAULT_COMPANY_ID)
            .contactTelephone(DEFAULT_CONTACT_TELEPHONE)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .contactPassword(DEFAULT_CONTACT_PASSWORD)
            .contactCreatedAt(DEFAULT_CONTACT_CREATED_AT)
            .contactModifiedAt(DEFAULT_CONTACT_MODIFIED_AT);
        return contact;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Contact createUpdatedEntity(EntityManager em) {
        Contact contact = new Contact()
            .contactId(UPDATED_CONTACT_ID)
            .contactName(UPDATED_CONTACT_NAME)
            .companyId(UPDATED_COMPANY_ID)
            .contactTelephone(UPDATED_CONTACT_TELEPHONE)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPassword(UPDATED_CONTACT_PASSWORD)
            .contactCreatedAt(UPDATED_CONTACT_CREATED_AT)
            .contactModifiedAt(UPDATED_CONTACT_MODIFIED_AT);
        return contact;
    }

    @BeforeEach
    public void initTest() {
        contact = createEntity(em);
    }

    @Test
    @Transactional
    public void createContact() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();
        // Create the Contact
        restContactMockMvc
            .perform(post("/api/contacts").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isCreated());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate + 1);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testContact.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testContact.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testContact.getContactTelephone()).isEqualTo(DEFAULT_CONTACT_TELEPHONE);
        assertThat(testContact.getContactEmail()).isEqualTo(DEFAULT_CONTACT_EMAIL);
        assertThat(testContact.getContactPassword()).isEqualTo(DEFAULT_CONTACT_PASSWORD);
        assertThat(testContact.getContactCreatedAt()).isEqualTo(DEFAULT_CONTACT_CREATED_AT);
        assertThat(testContact.getContactModifiedAt()).isEqualTo(DEFAULT_CONTACT_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void createContactWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactRepository.findAll().size();

        // Create the Contact with an existing ID
        contact.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactMockMvc
            .perform(post("/api/contacts").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContacts() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get all the contactList
        restContactMockMvc
            .perform(get("/api/contacts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contact.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID)))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].contactTelephone").value(hasItem(DEFAULT_CONTACT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactPassword").value(hasItem(DEFAULT_CONTACT_PASSWORD)))
            .andExpect(jsonPath("$.[*].contactCreatedAt").value(hasItem(DEFAULT_CONTACT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].contactModifiedAt").value(hasItem(DEFAULT_CONTACT_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    public void getContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        // Get the contact
        restContactMockMvc
            .perform(get("/api/contacts/{id}", contact.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contact.getId().intValue()))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.contactTelephone").value(DEFAULT_CONTACT_TELEPHONE))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.contactPassword").value(DEFAULT_CONTACT_PASSWORD))
            .andExpect(jsonPath("$.contactCreatedAt").value(DEFAULT_CONTACT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.contactModifiedAt").value(DEFAULT_CONTACT_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContact() throws Exception {
        // Get the contact
        restContactMockMvc.perform(get("/api/contacts/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // Update the contact
        Contact updatedContact = contactRepository.findById(contact.getId()).get();
        // Disconnect from session so that the updates on updatedContact are not directly saved in db
        em.detach(updatedContact);
        updatedContact
            .contactId(UPDATED_CONTACT_ID)
            .contactName(UPDATED_CONTACT_NAME)
            .companyId(UPDATED_COMPANY_ID)
            .contactTelephone(UPDATED_CONTACT_TELEPHONE)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPassword(UPDATED_CONTACT_PASSWORD)
            .contactCreatedAt(UPDATED_CONTACT_CREATED_AT)
            .contactModifiedAt(UPDATED_CONTACT_MODIFIED_AT);

        restContactMockMvc
            .perform(
                put("/api/contacts").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedContact))
            )
            .andExpect(status().isOk());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
        Contact testContact = contactList.get(contactList.size() - 1);
        assertThat(testContact.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testContact.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testContact.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testContact.getContactTelephone()).isEqualTo(UPDATED_CONTACT_TELEPHONE);
        assertThat(testContact.getContactEmail()).isEqualTo(UPDATED_CONTACT_EMAIL);
        assertThat(testContact.getContactPassword()).isEqualTo(UPDATED_CONTACT_PASSWORD);
        assertThat(testContact.getContactCreatedAt()).isEqualTo(UPDATED_CONTACT_CREATED_AT);
        assertThat(testContact.getContactModifiedAt()).isEqualTo(UPDATED_CONTACT_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingContact() throws Exception {
        int databaseSizeBeforeUpdate = contactRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactMockMvc
            .perform(put("/api/contacts").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(contact)))
            .andExpect(status().isBadRequest());

        // Validate the Contact in the database
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContact() throws Exception {
        // Initialize the database
        contactRepository.saveAndFlush(contact);

        int databaseSizeBeforeDelete = contactRepository.findAll().size();

        // Delete the contact
        restContactMockMvc
            .perform(delete("/api/contacts/{id}", contact.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Contact> contactList = contactRepository.findAll();
        assertThat(contactList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
