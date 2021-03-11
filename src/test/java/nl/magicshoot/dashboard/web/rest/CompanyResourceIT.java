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
import nl.magicshoot.dashboard.domain.Company;
import nl.magicshoot.dashboard.repository.CompanyRepository;
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
 * Integration tests for the {@link CompanyResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompanyResourceIT {
    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final Integer DEFAULT_RESELLER_ID = 1;
    private static final Integer UPDATED_RESELLER_ID = 2;

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ADRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ADRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_BILLING_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_BILLING_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ADRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ADRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_PLACE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_PHONE = "BBBBBBBBBB";

    private static final Float DEFAULT_VAT_PERCENTAGE = 1F;
    private static final Float UPDATED_VAT_PERCENTAGE = 2F;

    private static final String DEFAULT_VAT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_VAT_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_COMPANY_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPANY_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_COMPANY_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_COMPANY_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyMockMvc;

    private Company company;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createEntity(EntityManager em) {
        Company company = new Company()
            .companyId(DEFAULT_COMPANY_ID)
            .resellerId(DEFAULT_RESELLER_ID)
            .companyName(DEFAULT_COMPANY_NAME)
            .billingAdress1(DEFAULT_BILLING_ADRESS_1)
            .billingAdress2(DEFAULT_BILLING_ADRESS_2)
            .billingZip(DEFAULT_BILLING_ZIP)
            .billingPlace(DEFAULT_BILLING_PLACE)
            .billingCountry(DEFAULT_BILLING_COUNTRY)
            .billingEmail(DEFAULT_BILLING_EMAIL)
            .billingPhone(DEFAULT_BILLING_PHONE)
            .shippingAdress1(DEFAULT_SHIPPING_ADRESS_1)
            .shippingAdress2(DEFAULT_SHIPPING_ADRESS_2)
            .shippingZip(DEFAULT_SHIPPING_ZIP)
            .shippingPlace(DEFAULT_SHIPPING_PLACE)
            .shippingCountry(DEFAULT_SHIPPING_COUNTRY)
            .shippingEmail(DEFAULT_SHIPPING_EMAIL)
            .shippingPhone(DEFAULT_SHIPPING_PHONE)
            .vatPercentage(DEFAULT_VAT_PERCENTAGE)
            .vatNumber(DEFAULT_VAT_NUMBER)
            .companyCreatedAt(DEFAULT_COMPANY_CREATED_AT)
            .companyModifiedAt(DEFAULT_COMPANY_MODIFIED_AT);
        return company;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Company createUpdatedEntity(EntityManager em) {
        Company company = new Company()
            .companyId(UPDATED_COMPANY_ID)
            .resellerId(UPDATED_RESELLER_ID)
            .companyName(UPDATED_COMPANY_NAME)
            .billingAdress1(UPDATED_BILLING_ADRESS_1)
            .billingAdress2(UPDATED_BILLING_ADRESS_2)
            .billingZip(UPDATED_BILLING_ZIP)
            .billingPlace(UPDATED_BILLING_PLACE)
            .billingCountry(UPDATED_BILLING_COUNTRY)
            .billingEmail(UPDATED_BILLING_EMAIL)
            .billingPhone(UPDATED_BILLING_PHONE)
            .shippingAdress1(UPDATED_SHIPPING_ADRESS_1)
            .shippingAdress2(UPDATED_SHIPPING_ADRESS_2)
            .shippingZip(UPDATED_SHIPPING_ZIP)
            .shippingPlace(UPDATED_SHIPPING_PLACE)
            .shippingCountry(UPDATED_SHIPPING_COUNTRY)
            .shippingEmail(UPDATED_SHIPPING_EMAIL)
            .shippingPhone(UPDATED_SHIPPING_PHONE)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .vatNumber(UPDATED_VAT_NUMBER)
            .companyCreatedAt(UPDATED_COMPANY_CREATED_AT)
            .companyModifiedAt(UPDATED_COMPANY_MODIFIED_AT);
        return company;
    }

    @BeforeEach
    public void initTest() {
        company = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompany() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();
        // Create the Company
        restCompanyMockMvc
            .perform(post("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isCreated());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate + 1);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testCompany.getResellerId()).isEqualTo(DEFAULT_RESELLER_ID);
        assertThat(testCompany.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testCompany.getBillingAdress1()).isEqualTo(DEFAULT_BILLING_ADRESS_1);
        assertThat(testCompany.getBillingAdress2()).isEqualTo(DEFAULT_BILLING_ADRESS_2);
        assertThat(testCompany.getBillingZip()).isEqualTo(DEFAULT_BILLING_ZIP);
        assertThat(testCompany.getBillingPlace()).isEqualTo(DEFAULT_BILLING_PLACE);
        assertThat(testCompany.getBillingCountry()).isEqualTo(DEFAULT_BILLING_COUNTRY);
        assertThat(testCompany.getBillingEmail()).isEqualTo(DEFAULT_BILLING_EMAIL);
        assertThat(testCompany.getBillingPhone()).isEqualTo(DEFAULT_BILLING_PHONE);
        assertThat(testCompany.getShippingAdress1()).isEqualTo(DEFAULT_SHIPPING_ADRESS_1);
        assertThat(testCompany.getShippingAdress2()).isEqualTo(DEFAULT_SHIPPING_ADRESS_2);
        assertThat(testCompany.getShippingZip()).isEqualTo(DEFAULT_SHIPPING_ZIP);
        assertThat(testCompany.getShippingPlace()).isEqualTo(DEFAULT_SHIPPING_PLACE);
        assertThat(testCompany.getShippingCountry()).isEqualTo(DEFAULT_SHIPPING_COUNTRY);
        assertThat(testCompany.getShippingEmail()).isEqualTo(DEFAULT_SHIPPING_EMAIL);
        assertThat(testCompany.getShippingPhone()).isEqualTo(DEFAULT_SHIPPING_PHONE);
        assertThat(testCompany.getVatPercentage()).isEqualTo(DEFAULT_VAT_PERCENTAGE);
        assertThat(testCompany.getVatNumber()).isEqualTo(DEFAULT_VAT_NUMBER);
        assertThat(testCompany.getCompanyCreatedAt()).isEqualTo(DEFAULT_COMPANY_CREATED_AT);
        assertThat(testCompany.getCompanyModifiedAt()).isEqualTo(DEFAULT_COMPANY_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void createCompanyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyRepository.findAll().size();

        // Create the Company with an existing ID
        company.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyMockMvc
            .perform(post("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanies() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get all the companyList
        restCompanyMockMvc
            .perform(get("/api/companies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(company.getId().intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].resellerId").value(hasItem(DEFAULT_RESELLER_ID)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].billingAdress1").value(hasItem(DEFAULT_BILLING_ADRESS_1)))
            .andExpect(jsonPath("$.[*].billingAdress2").value(hasItem(DEFAULT_BILLING_ADRESS_2)))
            .andExpect(jsonPath("$.[*].billingZip").value(hasItem(DEFAULT_BILLING_ZIP)))
            .andExpect(jsonPath("$.[*].billingPlace").value(hasItem(DEFAULT_BILLING_PLACE)))
            .andExpect(jsonPath("$.[*].billingCountry").value(hasItem(DEFAULT_BILLING_COUNTRY)))
            .andExpect(jsonPath("$.[*].billingEmail").value(hasItem(DEFAULT_BILLING_EMAIL)))
            .andExpect(jsonPath("$.[*].billingPhone").value(hasItem(DEFAULT_BILLING_PHONE)))
            .andExpect(jsonPath("$.[*].shippingAdress1").value(hasItem(DEFAULT_SHIPPING_ADRESS_1)))
            .andExpect(jsonPath("$.[*].shippingAdress2").value(hasItem(DEFAULT_SHIPPING_ADRESS_2)))
            .andExpect(jsonPath("$.[*].shippingZip").value(hasItem(DEFAULT_SHIPPING_ZIP)))
            .andExpect(jsonPath("$.[*].shippingPlace").value(hasItem(DEFAULT_SHIPPING_PLACE)))
            .andExpect(jsonPath("$.[*].shippingCountry").value(hasItem(DEFAULT_SHIPPING_COUNTRY)))
            .andExpect(jsonPath("$.[*].shippingEmail").value(hasItem(DEFAULT_SHIPPING_EMAIL)))
            .andExpect(jsonPath("$.[*].shippingPhone").value(hasItem(DEFAULT_SHIPPING_PHONE)))
            .andExpect(jsonPath("$.[*].vatPercentage").value(hasItem(DEFAULT_VAT_PERCENTAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].vatNumber").value(hasItem(DEFAULT_VAT_NUMBER)))
            .andExpect(jsonPath("$.[*].companyCreatedAt").value(hasItem(DEFAULT_COMPANY_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].companyModifiedAt").value(hasItem(DEFAULT_COMPANY_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    public void getCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        // Get the company
        restCompanyMockMvc
            .perform(get("/api/companies/{id}", company.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(company.getId().intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.resellerId").value(DEFAULT_RESELLER_ID))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.billingAdress1").value(DEFAULT_BILLING_ADRESS_1))
            .andExpect(jsonPath("$.billingAdress2").value(DEFAULT_BILLING_ADRESS_2))
            .andExpect(jsonPath("$.billingZip").value(DEFAULT_BILLING_ZIP))
            .andExpect(jsonPath("$.billingPlace").value(DEFAULT_BILLING_PLACE))
            .andExpect(jsonPath("$.billingCountry").value(DEFAULT_BILLING_COUNTRY))
            .andExpect(jsonPath("$.billingEmail").value(DEFAULT_BILLING_EMAIL))
            .andExpect(jsonPath("$.billingPhone").value(DEFAULT_BILLING_PHONE))
            .andExpect(jsonPath("$.shippingAdress1").value(DEFAULT_SHIPPING_ADRESS_1))
            .andExpect(jsonPath("$.shippingAdress2").value(DEFAULT_SHIPPING_ADRESS_2))
            .andExpect(jsonPath("$.shippingZip").value(DEFAULT_SHIPPING_ZIP))
            .andExpect(jsonPath("$.shippingPlace").value(DEFAULT_SHIPPING_PLACE))
            .andExpect(jsonPath("$.shippingCountry").value(DEFAULT_SHIPPING_COUNTRY))
            .andExpect(jsonPath("$.shippingEmail").value(DEFAULT_SHIPPING_EMAIL))
            .andExpect(jsonPath("$.shippingPhone").value(DEFAULT_SHIPPING_PHONE))
            .andExpect(jsonPath("$.vatPercentage").value(DEFAULT_VAT_PERCENTAGE.doubleValue()))
            .andExpect(jsonPath("$.vatNumber").value(DEFAULT_VAT_NUMBER))
            .andExpect(jsonPath("$.companyCreatedAt").value(DEFAULT_COMPANY_CREATED_AT.toString()))
            .andExpect(jsonPath("$.companyModifiedAt").value(DEFAULT_COMPANY_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompany() throws Exception {
        // Get the company
        restCompanyMockMvc.perform(get("/api/companies/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // Update the company
        Company updatedCompany = companyRepository.findById(company.getId()).get();
        // Disconnect from session so that the updates on updatedCompany are not directly saved in db
        em.detach(updatedCompany);
        updatedCompany
            .companyId(UPDATED_COMPANY_ID)
            .resellerId(UPDATED_RESELLER_ID)
            .companyName(UPDATED_COMPANY_NAME)
            .billingAdress1(UPDATED_BILLING_ADRESS_1)
            .billingAdress2(UPDATED_BILLING_ADRESS_2)
            .billingZip(UPDATED_BILLING_ZIP)
            .billingPlace(UPDATED_BILLING_PLACE)
            .billingCountry(UPDATED_BILLING_COUNTRY)
            .billingEmail(UPDATED_BILLING_EMAIL)
            .billingPhone(UPDATED_BILLING_PHONE)
            .shippingAdress1(UPDATED_SHIPPING_ADRESS_1)
            .shippingAdress2(UPDATED_SHIPPING_ADRESS_2)
            .shippingZip(UPDATED_SHIPPING_ZIP)
            .shippingPlace(UPDATED_SHIPPING_PLACE)
            .shippingCountry(UPDATED_SHIPPING_COUNTRY)
            .shippingEmail(UPDATED_SHIPPING_EMAIL)
            .shippingPhone(UPDATED_SHIPPING_PHONE)
            .vatPercentage(UPDATED_VAT_PERCENTAGE)
            .vatNumber(UPDATED_VAT_NUMBER)
            .companyCreatedAt(UPDATED_COMPANY_CREATED_AT)
            .companyModifiedAt(UPDATED_COMPANY_MODIFIED_AT);

        restCompanyMockMvc
            .perform(
                put("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedCompany))
            )
            .andExpect(status().isOk());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
        Company testCompany = companyList.get(companyList.size() - 1);
        assertThat(testCompany.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testCompany.getResellerId()).isEqualTo(UPDATED_RESELLER_ID);
        assertThat(testCompany.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testCompany.getBillingAdress1()).isEqualTo(UPDATED_BILLING_ADRESS_1);
        assertThat(testCompany.getBillingAdress2()).isEqualTo(UPDATED_BILLING_ADRESS_2);
        assertThat(testCompany.getBillingZip()).isEqualTo(UPDATED_BILLING_ZIP);
        assertThat(testCompany.getBillingPlace()).isEqualTo(UPDATED_BILLING_PLACE);
        assertThat(testCompany.getBillingCountry()).isEqualTo(UPDATED_BILLING_COUNTRY);
        assertThat(testCompany.getBillingEmail()).isEqualTo(UPDATED_BILLING_EMAIL);
        assertThat(testCompany.getBillingPhone()).isEqualTo(UPDATED_BILLING_PHONE);
        assertThat(testCompany.getShippingAdress1()).isEqualTo(UPDATED_SHIPPING_ADRESS_1);
        assertThat(testCompany.getShippingAdress2()).isEqualTo(UPDATED_SHIPPING_ADRESS_2);
        assertThat(testCompany.getShippingZip()).isEqualTo(UPDATED_SHIPPING_ZIP);
        assertThat(testCompany.getShippingPlace()).isEqualTo(UPDATED_SHIPPING_PLACE);
        assertThat(testCompany.getShippingCountry()).isEqualTo(UPDATED_SHIPPING_COUNTRY);
        assertThat(testCompany.getShippingEmail()).isEqualTo(UPDATED_SHIPPING_EMAIL);
        assertThat(testCompany.getShippingPhone()).isEqualTo(UPDATED_SHIPPING_PHONE);
        assertThat(testCompany.getVatPercentage()).isEqualTo(UPDATED_VAT_PERCENTAGE);
        assertThat(testCompany.getVatNumber()).isEqualTo(UPDATED_VAT_NUMBER);
        assertThat(testCompany.getCompanyCreatedAt()).isEqualTo(UPDATED_COMPANY_CREATED_AT);
        assertThat(testCompany.getCompanyModifiedAt()).isEqualTo(UPDATED_COMPANY_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingCompany() throws Exception {
        int databaseSizeBeforeUpdate = companyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyMockMvc
            .perform(put("/api/companies").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(company)))
            .andExpect(status().isBadRequest());

        // Validate the Company in the database
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompany() throws Exception {
        // Initialize the database
        companyRepository.saveAndFlush(company);

        int databaseSizeBeforeDelete = companyRepository.findAll().size();

        // Delete the company
        restCompanyMockMvc
            .perform(delete("/api/companies/{id}", company.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Company> companyList = companyRepository.findAll();
        assertThat(companyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
