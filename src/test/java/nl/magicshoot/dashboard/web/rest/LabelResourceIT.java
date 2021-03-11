package nl.magicshoot.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import nl.magicshoot.dashboard.DashboardApp;
import nl.magicshoot.dashboard.domain.Label;
import nl.magicshoot.dashboard.domain.enumeration.LabelType;
import nl.magicshoot.dashboard.repository.LabelRepository;
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
 * Integration tests for the {@link LabelResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class LabelResourceIT {
    private static final Integer DEFAULT_LABEL_ID = 1;
    private static final Integer UPDATED_LABEL_ID = 2;

    private static final String DEFAULT_LABEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LABEL_NAME = "BBBBBBBBBB";

    private static final LabelType DEFAULT_LABEL_TYPE = LabelType.THERMISCH;
    private static final LabelType UPDATED_LABEL_TYPE = LabelType.INKT;

    private static final Integer DEFAULT_LABEL_WIDTH = 1;
    private static final Integer UPDATED_LABEL_WIDTH = 2;

    private static final Integer DEFAULT_LABEL_HEIGHT = 1;
    private static final Integer UPDATED_LABEL_HEIGHT = 2;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLabelMockMvc;

    private Label label;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Label createEntity(EntityManager em) {
        Label label = new Label()
            .labelId(DEFAULT_LABEL_ID)
            .labelName(DEFAULT_LABEL_NAME)
            .labelType(DEFAULT_LABEL_TYPE)
            .labelWidth(DEFAULT_LABEL_WIDTH)
            .labelHeight(DEFAULT_LABEL_HEIGHT);
        return label;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Label createUpdatedEntity(EntityManager em) {
        Label label = new Label()
            .labelId(UPDATED_LABEL_ID)
            .labelName(UPDATED_LABEL_NAME)
            .labelType(UPDATED_LABEL_TYPE)
            .labelWidth(UPDATED_LABEL_WIDTH)
            .labelHeight(UPDATED_LABEL_HEIGHT);
        return label;
    }

    @BeforeEach
    public void initTest() {
        label = createEntity(em);
    }

    @Test
    @Transactional
    public void createLabel() throws Exception {
        int databaseSizeBeforeCreate = labelRepository.findAll().size();
        // Create the Label
        restLabelMockMvc
            .perform(post("/api/labels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(label)))
            .andExpect(status().isCreated());

        // Validate the Label in the database
        List<Label> labelList = labelRepository.findAll();
        assertThat(labelList).hasSize(databaseSizeBeforeCreate + 1);
        Label testLabel = labelList.get(labelList.size() - 1);
        assertThat(testLabel.getLabelId()).isEqualTo(DEFAULT_LABEL_ID);
        assertThat(testLabel.getLabelName()).isEqualTo(DEFAULT_LABEL_NAME);
        assertThat(testLabel.getLabelType()).isEqualTo(DEFAULT_LABEL_TYPE);
        assertThat(testLabel.getLabelWidth()).isEqualTo(DEFAULT_LABEL_WIDTH);
        assertThat(testLabel.getLabelHeight()).isEqualTo(DEFAULT_LABEL_HEIGHT);
    }

    @Test
    @Transactional
    public void createLabelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = labelRepository.findAll().size();

        // Create the Label with an existing ID
        label.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLabelMockMvc
            .perform(post("/api/labels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(label)))
            .andExpect(status().isBadRequest());

        // Validate the Label in the database
        List<Label> labelList = labelRepository.findAll();
        assertThat(labelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLabels() throws Exception {
        // Initialize the database
        labelRepository.saveAndFlush(label);

        // Get all the labelList
        restLabelMockMvc
            .perform(get("/api/labels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(label.getId().intValue())))
            .andExpect(jsonPath("$.[*].labelId").value(hasItem(DEFAULT_LABEL_ID)))
            .andExpect(jsonPath("$.[*].labelName").value(hasItem(DEFAULT_LABEL_NAME)))
            .andExpect(jsonPath("$.[*].labelType").value(hasItem(DEFAULT_LABEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].labelWidth").value(hasItem(DEFAULT_LABEL_WIDTH)))
            .andExpect(jsonPath("$.[*].labelHeight").value(hasItem(DEFAULT_LABEL_HEIGHT)));
    }

    @Test
    @Transactional
    public void getLabel() throws Exception {
        // Initialize the database
        labelRepository.saveAndFlush(label);

        // Get the label
        restLabelMockMvc
            .perform(get("/api/labels/{id}", label.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(label.getId().intValue()))
            .andExpect(jsonPath("$.labelId").value(DEFAULT_LABEL_ID))
            .andExpect(jsonPath("$.labelName").value(DEFAULT_LABEL_NAME))
            .andExpect(jsonPath("$.labelType").value(DEFAULT_LABEL_TYPE.toString()))
            .andExpect(jsonPath("$.labelWidth").value(DEFAULT_LABEL_WIDTH))
            .andExpect(jsonPath("$.labelHeight").value(DEFAULT_LABEL_HEIGHT));
    }

    @Test
    @Transactional
    public void getNonExistingLabel() throws Exception {
        // Get the label
        restLabelMockMvc.perform(get("/api/labels/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLabel() throws Exception {
        // Initialize the database
        labelRepository.saveAndFlush(label);

        int databaseSizeBeforeUpdate = labelRepository.findAll().size();

        // Update the label
        Label updatedLabel = labelRepository.findById(label.getId()).get();
        // Disconnect from session so that the updates on updatedLabel are not directly saved in db
        em.detach(updatedLabel);
        updatedLabel
            .labelId(UPDATED_LABEL_ID)
            .labelName(UPDATED_LABEL_NAME)
            .labelType(UPDATED_LABEL_TYPE)
            .labelWidth(UPDATED_LABEL_WIDTH)
            .labelHeight(UPDATED_LABEL_HEIGHT);

        restLabelMockMvc
            .perform(put("/api/labels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedLabel)))
            .andExpect(status().isOk());

        // Validate the Label in the database
        List<Label> labelList = labelRepository.findAll();
        assertThat(labelList).hasSize(databaseSizeBeforeUpdate);
        Label testLabel = labelList.get(labelList.size() - 1);
        assertThat(testLabel.getLabelId()).isEqualTo(UPDATED_LABEL_ID);
        assertThat(testLabel.getLabelName()).isEqualTo(UPDATED_LABEL_NAME);
        assertThat(testLabel.getLabelType()).isEqualTo(UPDATED_LABEL_TYPE);
        assertThat(testLabel.getLabelWidth()).isEqualTo(UPDATED_LABEL_WIDTH);
        assertThat(testLabel.getLabelHeight()).isEqualTo(UPDATED_LABEL_HEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingLabel() throws Exception {
        int databaseSizeBeforeUpdate = labelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLabelMockMvc
            .perform(put("/api/labels").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(label)))
            .andExpect(status().isBadRequest());

        // Validate the Label in the database
        List<Label> labelList = labelRepository.findAll();
        assertThat(labelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLabel() throws Exception {
        // Initialize the database
        labelRepository.saveAndFlush(label);

        int databaseSizeBeforeDelete = labelRepository.findAll().size();

        // Delete the label
        restLabelMockMvc
            .perform(delete("/api/labels/{id}", label.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Label> labelList = labelRepository.findAll();
        assertThat(labelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
