package nl.magicshoot.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import nl.magicshoot.dashboard.DashboardApp;
import nl.magicshoot.dashboard.domain.Print;
import nl.magicshoot.dashboard.domain.enumeration.PrintType;
import nl.magicshoot.dashboard.repository.PrintRepository;
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
 * Integration tests for the {@link PrintResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrintResourceIT {
    private static final Integer DEFAULT_PRINT_ID = 1;
    private static final Integer UPDATED_PRINT_ID = 2;

    private static final String DEFAULT_PRINT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRINT_NAME = "BBBBBBBBBB";

    private static final PrintType DEFAULT_PRINT_TYPE = PrintType.GLANZEND;
    private static final PrintType UPDATED_PRINT_TYPE = PrintType.MAT;

    private static final Integer DEFAULT_PRINT_WIDTH = 1;
    private static final Integer UPDATED_PRINT_WIDTH = 2;

    private static final Integer DEFAULT_PRINT_HEIGHT = 1;
    private static final Integer UPDATED_PRINT_HEIGHT = 2;

    @Autowired
    private PrintRepository printRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrintMockMvc;

    private Print print;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Print createEntity(EntityManager em) {
        Print print = new Print()
            .printId(DEFAULT_PRINT_ID)
            .printName(DEFAULT_PRINT_NAME)
            .printType(DEFAULT_PRINT_TYPE)
            .printWidth(DEFAULT_PRINT_WIDTH)
            .printHeight(DEFAULT_PRINT_HEIGHT);
        return print;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Print createUpdatedEntity(EntityManager em) {
        Print print = new Print()
            .printId(UPDATED_PRINT_ID)
            .printName(UPDATED_PRINT_NAME)
            .printType(UPDATED_PRINT_TYPE)
            .printWidth(UPDATED_PRINT_WIDTH)
            .printHeight(UPDATED_PRINT_HEIGHT);
        return print;
    }

    @BeforeEach
    public void initTest() {
        print = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrint() throws Exception {
        int databaseSizeBeforeCreate = printRepository.findAll().size();
        // Create the Print
        restPrintMockMvc
            .perform(post("/api/prints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(print)))
            .andExpect(status().isCreated());

        // Validate the Print in the database
        List<Print> printList = printRepository.findAll();
        assertThat(printList).hasSize(databaseSizeBeforeCreate + 1);
        Print testPrint = printList.get(printList.size() - 1);
        assertThat(testPrint.getPrintId()).isEqualTo(DEFAULT_PRINT_ID);
        assertThat(testPrint.getPrintName()).isEqualTo(DEFAULT_PRINT_NAME);
        assertThat(testPrint.getPrintType()).isEqualTo(DEFAULT_PRINT_TYPE);
        assertThat(testPrint.getPrintWidth()).isEqualTo(DEFAULT_PRINT_WIDTH);
        assertThat(testPrint.getPrintHeight()).isEqualTo(DEFAULT_PRINT_HEIGHT);
    }

    @Test
    @Transactional
    public void createPrintWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = printRepository.findAll().size();

        // Create the Print with an existing ID
        print.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrintMockMvc
            .perform(post("/api/prints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(print)))
            .andExpect(status().isBadRequest());

        // Validate the Print in the database
        List<Print> printList = printRepository.findAll();
        assertThat(printList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrints() throws Exception {
        // Initialize the database
        printRepository.saveAndFlush(print);

        // Get all the printList
        restPrintMockMvc
            .perform(get("/api/prints?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(print.getId().intValue())))
            .andExpect(jsonPath("$.[*].printId").value(hasItem(DEFAULT_PRINT_ID)))
            .andExpect(jsonPath("$.[*].printName").value(hasItem(DEFAULT_PRINT_NAME)))
            .andExpect(jsonPath("$.[*].printType").value(hasItem(DEFAULT_PRINT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].printWidth").value(hasItem(DEFAULT_PRINT_WIDTH)))
            .andExpect(jsonPath("$.[*].printHeight").value(hasItem(DEFAULT_PRINT_HEIGHT)));
    }

    @Test
    @Transactional
    public void getPrint() throws Exception {
        // Initialize the database
        printRepository.saveAndFlush(print);

        // Get the print
        restPrintMockMvc
            .perform(get("/api/prints/{id}", print.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(print.getId().intValue()))
            .andExpect(jsonPath("$.printId").value(DEFAULT_PRINT_ID))
            .andExpect(jsonPath("$.printName").value(DEFAULT_PRINT_NAME))
            .andExpect(jsonPath("$.printType").value(DEFAULT_PRINT_TYPE.toString()))
            .andExpect(jsonPath("$.printWidth").value(DEFAULT_PRINT_WIDTH))
            .andExpect(jsonPath("$.printHeight").value(DEFAULT_PRINT_HEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingPrint() throws Exception {
        // Get the print
        restPrintMockMvc.perform(get("/api/prints/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrint() throws Exception {
        // Initialize the database
        printRepository.saveAndFlush(print);

        int databaseSizeBeforeUpdate = printRepository.findAll().size();

        // Update the print
        Print updatedPrint = printRepository.findById(print.getId()).get();
        // Disconnect from session so that the updates on updatedPrint are not directly saved in db
        em.detach(updatedPrint);
        updatedPrint
            .printId(UPDATED_PRINT_ID)
            .printName(UPDATED_PRINT_NAME)
            .printType(UPDATED_PRINT_TYPE)
            .printWidth(UPDATED_PRINT_WIDTH)
            .printHeight(UPDATED_PRINT_HEIGHT);

        restPrintMockMvc
            .perform(put("/api/prints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedPrint)))
            .andExpect(status().isOk());

        // Validate the Print in the database
        List<Print> printList = printRepository.findAll();
        assertThat(printList).hasSize(databaseSizeBeforeUpdate);
        Print testPrint = printList.get(printList.size() - 1);
        assertThat(testPrint.getPrintId()).isEqualTo(UPDATED_PRINT_ID);
        assertThat(testPrint.getPrintName()).isEqualTo(UPDATED_PRINT_NAME);
        assertThat(testPrint.getPrintType()).isEqualTo(UPDATED_PRINT_TYPE);
        assertThat(testPrint.getPrintWidth()).isEqualTo(UPDATED_PRINT_WIDTH);
        assertThat(testPrint.getPrintHeight()).isEqualTo(UPDATED_PRINT_HEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingPrint() throws Exception {
        int databaseSizeBeforeUpdate = printRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrintMockMvc
            .perform(put("/api/prints").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(print)))
            .andExpect(status().isBadRequest());

        // Validate the Print in the database
        List<Print> printList = printRepository.findAll();
        assertThat(printList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrint() throws Exception {
        // Initialize the database
        printRepository.saveAndFlush(print);

        int databaseSizeBeforeDelete = printRepository.findAll().size();

        // Delete the print
        restPrintMockMvc
            .perform(delete("/api/prints/{id}", print.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Print> printList = printRepository.findAll();
        assertThat(printList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
