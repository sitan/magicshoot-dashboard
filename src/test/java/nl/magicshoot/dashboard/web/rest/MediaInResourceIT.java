package nl.magicshoot.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import nl.magicshoot.dashboard.DashboardApp;
import nl.magicshoot.dashboard.domain.MediaIn;
import nl.magicshoot.dashboard.domain.enumeration.MediaType;
import nl.magicshoot.dashboard.domain.enumeration.MediaUse;
import nl.magicshoot.dashboard.repository.MediaInRepository;
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
 * Integration tests for the {@link MediaInResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MediaInResourceIT {
    private static final Integer DEFAULT_MEDIA_IN_ID = 1;
    private static final Integer UPDATED_MEDIA_IN_ID = 2;

    private static final String DEFAULT_MEDIA_IN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_IN_NAME = "BBBBBBBBBB";

    private static final MediaUse DEFAULT_MEDIA_USE = MediaUse.FOREGROUND;
    private static final MediaUse UPDATED_MEDIA_USE = MediaUse.BACKGROUND;

    private static final MediaType DEFAULT_MEDIA_IN_TYPE = MediaType.PHOTO;
    private static final MediaType UPDATED_MEDIA_IN_TYPE = MediaType.VIDEO;

    @Autowired
    private MediaInRepository mediaInRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMediaInMockMvc;

    private MediaIn mediaIn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaIn createEntity(EntityManager em) {
        MediaIn mediaIn = new MediaIn()
            .mediaInId(DEFAULT_MEDIA_IN_ID)
            .mediaInName(DEFAULT_MEDIA_IN_NAME)
            .mediaUse(DEFAULT_MEDIA_USE)
            .mediaInType(DEFAULT_MEDIA_IN_TYPE);
        return mediaIn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaIn createUpdatedEntity(EntityManager em) {
        MediaIn mediaIn = new MediaIn()
            .mediaInId(UPDATED_MEDIA_IN_ID)
            .mediaInName(UPDATED_MEDIA_IN_NAME)
            .mediaUse(UPDATED_MEDIA_USE)
            .mediaInType(UPDATED_MEDIA_IN_TYPE);
        return mediaIn;
    }

    @BeforeEach
    public void initTest() {
        mediaIn = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaIn() throws Exception {
        int databaseSizeBeforeCreate = mediaInRepository.findAll().size();
        // Create the MediaIn
        restMediaInMockMvc
            .perform(post("/api/media-ins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaIn)))
            .andExpect(status().isCreated());

        // Validate the MediaIn in the database
        List<MediaIn> mediaInList = mediaInRepository.findAll();
        assertThat(mediaInList).hasSize(databaseSizeBeforeCreate + 1);
        MediaIn testMediaIn = mediaInList.get(mediaInList.size() - 1);
        assertThat(testMediaIn.getMediaInId()).isEqualTo(DEFAULT_MEDIA_IN_ID);
        assertThat(testMediaIn.getMediaInName()).isEqualTo(DEFAULT_MEDIA_IN_NAME);
        assertThat(testMediaIn.getMediaUse()).isEqualTo(DEFAULT_MEDIA_USE);
        assertThat(testMediaIn.getMediaInType()).isEqualTo(DEFAULT_MEDIA_IN_TYPE);
    }

    @Test
    @Transactional
    public void createMediaInWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaInRepository.findAll().size();

        // Create the MediaIn with an existing ID
        mediaIn.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaInMockMvc
            .perform(post("/api/media-ins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaIn)))
            .andExpect(status().isBadRequest());

        // Validate the MediaIn in the database
        List<MediaIn> mediaInList = mediaInRepository.findAll();
        assertThat(mediaInList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMediaIns() throws Exception {
        // Initialize the database
        mediaInRepository.saveAndFlush(mediaIn);

        // Get all the mediaInList
        restMediaInMockMvc
            .perform(get("/api/media-ins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaInId").value(hasItem(DEFAULT_MEDIA_IN_ID)))
            .andExpect(jsonPath("$.[*].mediaInName").value(hasItem(DEFAULT_MEDIA_IN_NAME)))
            .andExpect(jsonPath("$.[*].mediaUse").value(hasItem(DEFAULT_MEDIA_USE.toString())))
            .andExpect(jsonPath("$.[*].mediaInType").value(hasItem(DEFAULT_MEDIA_IN_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMediaIn() throws Exception {
        // Initialize the database
        mediaInRepository.saveAndFlush(mediaIn);

        // Get the mediaIn
        restMediaInMockMvc
            .perform(get("/api/media-ins/{id}", mediaIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mediaIn.getId().intValue()))
            .andExpect(jsonPath("$.mediaInId").value(DEFAULT_MEDIA_IN_ID))
            .andExpect(jsonPath("$.mediaInName").value(DEFAULT_MEDIA_IN_NAME))
            .andExpect(jsonPath("$.mediaUse").value(DEFAULT_MEDIA_USE.toString()))
            .andExpect(jsonPath("$.mediaInType").value(DEFAULT_MEDIA_IN_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaIn() throws Exception {
        // Get the mediaIn
        restMediaInMockMvc.perform(get("/api/media-ins/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaIn() throws Exception {
        // Initialize the database
        mediaInRepository.saveAndFlush(mediaIn);

        int databaseSizeBeforeUpdate = mediaInRepository.findAll().size();

        // Update the mediaIn
        MediaIn updatedMediaIn = mediaInRepository.findById(mediaIn.getId()).get();
        // Disconnect from session so that the updates on updatedMediaIn are not directly saved in db
        em.detach(updatedMediaIn);
        updatedMediaIn
            .mediaInId(UPDATED_MEDIA_IN_ID)
            .mediaInName(UPDATED_MEDIA_IN_NAME)
            .mediaUse(UPDATED_MEDIA_USE)
            .mediaInType(UPDATED_MEDIA_IN_TYPE);

        restMediaInMockMvc
            .perform(
                put("/api/media-ins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedMediaIn))
            )
            .andExpect(status().isOk());

        // Validate the MediaIn in the database
        List<MediaIn> mediaInList = mediaInRepository.findAll();
        assertThat(mediaInList).hasSize(databaseSizeBeforeUpdate);
        MediaIn testMediaIn = mediaInList.get(mediaInList.size() - 1);
        assertThat(testMediaIn.getMediaInId()).isEqualTo(UPDATED_MEDIA_IN_ID);
        assertThat(testMediaIn.getMediaInName()).isEqualTo(UPDATED_MEDIA_IN_NAME);
        assertThat(testMediaIn.getMediaUse()).isEqualTo(UPDATED_MEDIA_USE);
        assertThat(testMediaIn.getMediaInType()).isEqualTo(UPDATED_MEDIA_IN_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaIn() throws Exception {
        int databaseSizeBeforeUpdate = mediaInRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaInMockMvc
            .perform(put("/api/media-ins").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaIn)))
            .andExpect(status().isBadRequest());

        // Validate the MediaIn in the database
        List<MediaIn> mediaInList = mediaInRepository.findAll();
        assertThat(mediaInList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMediaIn() throws Exception {
        // Initialize the database
        mediaInRepository.saveAndFlush(mediaIn);

        int databaseSizeBeforeDelete = mediaInRepository.findAll().size();

        // Delete the mediaIn
        restMediaInMockMvc
            .perform(delete("/api/media-ins/{id}", mediaIn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MediaIn> mediaInList = mediaInRepository.findAll();
        assertThat(mediaInList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
