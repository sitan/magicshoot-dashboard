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
import nl.magicshoot.dashboard.domain.Quote;
import nl.magicshoot.dashboard.repository.QuoteRepository;
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
 * Integration tests for the {@link QuoteResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuoteResourceIT {
    private static final Integer DEFAULT_QUOTE_ID = 1;
    private static final Integer UPDATED_QUOTE_ID = 2;

    private static final Integer DEFAULT_CONTACT_ID = 1;
    private static final Integer UPDATED_CONTACT_ID = 2;

    private static final Integer DEFAULT_COMPANY_ID = 1;
    private static final Integer UPDATED_COMPANY_ID = 2;

    private static final String DEFAULT_QUOTE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_QUOTE_DESCRIPTION = "BBBBBBBBBB";

    private static final Float DEFAULT_QUOTE_PRICE = 1F;
    private static final Float UPDATED_QUOTE_PRICE = 2F;

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_QUOTE_CREATED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTE_CREATED_AT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_QUOTE_MODIFIED_AT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_QUOTE_MODIFIED_AT = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuoteMockMvc;

    private Quote quote;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote createEntity(EntityManager em) {
        Quote quote = new Quote()
            .quoteId(DEFAULT_QUOTE_ID)
            .contactId(DEFAULT_CONTACT_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .quoteDescription(DEFAULT_QUOTE_DESCRIPTION)
            .quotePrice(DEFAULT_QUOTE_PRICE)
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .quoteCreatedAt(DEFAULT_QUOTE_CREATED_AT)
            .quoteModifiedAt(DEFAULT_QUOTE_MODIFIED_AT);
        return quote;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Quote createUpdatedEntity(EntityManager em) {
        Quote quote = new Quote()
            .quoteId(UPDATED_QUOTE_ID)
            .contactId(UPDATED_CONTACT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .quoteDescription(UPDATED_QUOTE_DESCRIPTION)
            .quotePrice(UPDATED_QUOTE_PRICE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .quoteCreatedAt(UPDATED_QUOTE_CREATED_AT)
            .quoteModifiedAt(UPDATED_QUOTE_MODIFIED_AT);
        return quote;
    }

    @BeforeEach
    public void initTest() {
        quote = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuote() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();
        // Create the Quote
        restQuoteMockMvc
            .perform(post("/api/quotes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isCreated());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate + 1);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getQuoteId()).isEqualTo(DEFAULT_QUOTE_ID);
        assertThat(testQuote.getContactId()).isEqualTo(DEFAULT_CONTACT_ID);
        assertThat(testQuote.getCompanyId()).isEqualTo(DEFAULT_COMPANY_ID);
        assertThat(testQuote.getQuoteDescription()).isEqualTo(DEFAULT_QUOTE_DESCRIPTION);
        assertThat(testQuote.getQuotePrice()).isEqualTo(DEFAULT_QUOTE_PRICE);
        assertThat(testQuote.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testQuote.getQuoteCreatedAt()).isEqualTo(DEFAULT_QUOTE_CREATED_AT);
        assertThat(testQuote.getQuoteModifiedAt()).isEqualTo(DEFAULT_QUOTE_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void createQuoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quoteRepository.findAll().size();

        // Create the Quote with an existing ID
        quote.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuoteMockMvc
            .perform(post("/api/quotes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuotes() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get all the quoteList
        restQuoteMockMvc
            .perform(get("/api/quotes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quote.getId().intValue())))
            .andExpect(jsonPath("$.[*].quoteId").value(hasItem(DEFAULT_QUOTE_ID)))
            .andExpect(jsonPath("$.[*].contactId").value(hasItem(DEFAULT_CONTACT_ID)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID)))
            .andExpect(jsonPath("$.[*].quoteDescription").value(hasItem(DEFAULT_QUOTE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].quotePrice").value(hasItem(DEFAULT_QUOTE_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].quoteCreatedAt").value(hasItem(DEFAULT_QUOTE_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].quoteModifiedAt").value(hasItem(DEFAULT_QUOTE_MODIFIED_AT.toString())));
    }

    @Test
    @Transactional
    public void getQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        // Get the quote
        restQuoteMockMvc
            .perform(get("/api/quotes/{id}", quote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quote.getId().intValue()))
            .andExpect(jsonPath("$.quoteId").value(DEFAULT_QUOTE_ID))
            .andExpect(jsonPath("$.contactId").value(DEFAULT_CONTACT_ID))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID))
            .andExpect(jsonPath("$.quoteDescription").value(DEFAULT_QUOTE_DESCRIPTION))
            .andExpect(jsonPath("$.quotePrice").value(DEFAULT_QUOTE_PRICE.doubleValue()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.quoteCreatedAt").value(DEFAULT_QUOTE_CREATED_AT.toString()))
            .andExpect(jsonPath("$.quoteModifiedAt").value(DEFAULT_QUOTE_MODIFIED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuote() throws Exception {
        // Get the quote
        restQuoteMockMvc.perform(get("/api/quotes/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // Update the quote
        Quote updatedQuote = quoteRepository.findById(quote.getId()).get();
        // Disconnect from session so that the updates on updatedQuote are not directly saved in db
        em.detach(updatedQuote);
        updatedQuote
            .quoteId(UPDATED_QUOTE_ID)
            .contactId(UPDATED_CONTACT_ID)
            .companyId(UPDATED_COMPANY_ID)
            .quoteDescription(UPDATED_QUOTE_DESCRIPTION)
            .quotePrice(UPDATED_QUOTE_PRICE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .quoteCreatedAt(UPDATED_QUOTE_CREATED_AT)
            .quoteModifiedAt(UPDATED_QUOTE_MODIFIED_AT);

        restQuoteMockMvc
            .perform(put("/api/quotes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedQuote)))
            .andExpect(status().isOk());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
        Quote testQuote = quoteList.get(quoteList.size() - 1);
        assertThat(testQuote.getQuoteId()).isEqualTo(UPDATED_QUOTE_ID);
        assertThat(testQuote.getContactId()).isEqualTo(UPDATED_CONTACT_ID);
        assertThat(testQuote.getCompanyId()).isEqualTo(UPDATED_COMPANY_ID);
        assertThat(testQuote.getQuoteDescription()).isEqualTo(UPDATED_QUOTE_DESCRIPTION);
        assertThat(testQuote.getQuotePrice()).isEqualTo(UPDATED_QUOTE_PRICE);
        assertThat(testQuote.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testQuote.getQuoteCreatedAt()).isEqualTo(UPDATED_QUOTE_CREATED_AT);
        assertThat(testQuote.getQuoteModifiedAt()).isEqualTo(UPDATED_QUOTE_MODIFIED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuote() throws Exception {
        int databaseSizeBeforeUpdate = quoteRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuoteMockMvc
            .perform(put("/api/quotes").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quote)))
            .andExpect(status().isBadRequest());

        // Validate the Quote in the database
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuote() throws Exception {
        // Initialize the database
        quoteRepository.saveAndFlush(quote);

        int databaseSizeBeforeDelete = quoteRepository.findAll().size();

        // Delete the quote
        restQuoteMockMvc
            .perform(delete("/api/quotes/{id}", quote.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Quote> quoteList = quoteRepository.findAll();
        assertThat(quoteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
