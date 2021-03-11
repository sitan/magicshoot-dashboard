package nl.magicshoot.dashboard.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import javax.persistence.EntityManager;
import nl.magicshoot.dashboard.DashboardApp;
import nl.magicshoot.dashboard.domain.MediaOut;
import nl.magicshoot.dashboard.domain.enumeration.MediaType;
import nl.magicshoot.dashboard.repository.MediaOutRepository;
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
 * Integration tests for the {@link MediaOutResource} REST controller.
 */
@SpringBootTest(classes = DashboardApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MediaOutResourceIT {
    private static final Integer DEFAULT_MEDIA_OUT_ID = 1;
    private static final Integer UPDATED_MEDIA_OUT_ID = 2;

    private static final String DEFAULT_MEDIA_OUT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_OUT_NAME = "BBBBBBBBBB";

    private static final MediaType DEFAULT_MEDIA_OUT_TYPE = MediaType.PHOTO;
    private static final MediaType UPDATED_MEDIA_OUT_TYPE = MediaType.VIDEO;

    @Autowired
    private MediaOutRepository mediaOutRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMediaOutMockMvc;

    private MediaOut mediaOut;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaOut createEntity(EntityManager em) {
        MediaOut mediaOut = new MediaOut()
            .mediaOutId(DEFAULT_MEDIA_OUT_ID)
            .mediaOutName(DEFAULT_MEDIA_OUT_NAME)
            .mediaOutType(DEFAULT_MEDIA_OUT_TYPE);
        return mediaOut;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MediaOut createUpdatedEntity(EntityManager em) {
        MediaOut mediaOut = new MediaOut()
            .mediaOutId(UPDATED_MEDIA_OUT_ID)
            .mediaOutName(UPDATED_MEDIA_OUT_NAME)
            .mediaOutType(UPDATED_MEDIA_OUT_TYPE);
        return mediaOut;
    }

    @BeforeEach
    public void initTest() {
        mediaOut = createEntity(em);
    }

    @Test
    @Transactional
    public void createMediaOut() throws Exception {
        int databaseSizeBeforeCreate = mediaOutRepository.findAll().size();
        // Create the MediaOut
        restMediaOutMockMvc
            .perform(post("/api/media-outs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaOut)))
            .andExpect(status().isCreated());

        // Validate the MediaOut in the database
        List<MediaOut> mediaOutList = mediaOutRepository.findAll();
        assertThat(mediaOutList).hasSize(databaseSizeBeforeCreate + 1);
        MediaOut testMediaOut = mediaOutList.get(mediaOutList.size() - 1);
        assertThat(testMediaOut.getMediaOutId()).isEqualTo(DEFAULT_MEDIA_OUT_ID);
        assertThat(testMediaOut.getMediaOutName()).isEqualTo(DEFAULT_MEDIA_OUT_NAME);
        assertThat(testMediaOut.getMediaOutType()).isEqualTo(DEFAULT_MEDIA_OUT_TYPE);
    }

    @Test
    @Transactional
    public void createMediaOutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaOutRepository.findAll().size();

        // Create the MediaOut with an existing ID
        mediaOut.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaOutMockMvc
            .perform(post("/api/media-outs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaOut)))
            .andExpect(status().isBadRequest());

        // Validate the MediaOut in the database
        List<MediaOut> mediaOutList = mediaOutRepository.findAll();
        assertThat(mediaOutList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMediaOuts() throws Exception {
        // Initialize the database
        mediaOutRepository.saveAndFlush(mediaOut);

        // Get all the mediaOutList
        restMediaOutMockMvc
            .perform(get("/api/media-outs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mediaOut.getId().intValue())))
            .andExpect(jsonPath("$.[*].mediaOutId").value(hasItem(DEFAULT_MEDIA_OUT_ID)))
            .andExpect(jsonPath("$.[*].mediaOutName").value(hasItem(DEFAULT_MEDIA_OUT_NAME)))
            .andExpect(jsonPath("$.[*].mediaOutType").value(hasItem(DEFAULT_MEDIA_OUT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getMediaOut() throws Exception {
        // Initialize the database
        mediaOutRepository.saveAndFlush(mediaOut);

        // Get the mediaOut
        restMediaOutMockMvc
            .perform(get("/api/media-outs/{id}", mediaOut.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mediaOut.getId().intValue()))
            .andExpect(jsonPath("$.mediaOutId").value(DEFAULT_MEDIA_OUT_ID))
            .andExpect(jsonPath("$.mediaOutName").value(DEFAULT_MEDIA_OUT_NAME))
            .andExpect(jsonPath("$.mediaOutType").value(DEFAULT_MEDIA_OUT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMediaOut() throws Exception {
        // Get the mediaOut
        restMediaOutMockMvc.perform(get("/api/media-outs/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMediaOut() throws Exception {
        // Initialize the database
        mediaOutRepository.saveAndFlush(mediaOut);

        int databaseSizeBeforeUpdate = mediaOutRepository.findAll().size();

        // Update the mediaOut
        MediaOut updatedMediaOut = mediaOutRepository.findById(mediaOut.getId()).get();
        // Disconnect from session so that the updates on updatedMediaOut are not directly saved in db
        em.detach(updatedMediaOut);
        updatedMediaOut.mediaOutId(UPDATED_MEDIA_OUT_ID).mediaOutName(UPDATED_MEDIA_OUT_NAME).mediaOutType(UPDATED_MEDIA_OUT_TYPE);

        restMediaOutMockMvc
            .perform(
                put("/api/media-outs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(updatedMediaOut))
            )
            .andExpect(status().isOk());

        // Validate the MediaOut in the database
        List<MediaOut> mediaOutList = mediaOutRepository.findAll();
        assertThat(mediaOutList).hasSize(databaseSizeBeforeUpdate);
        MediaOut testMediaOut = mediaOutList.get(mediaOutList.size() - 1);
        assertThat(testMediaOut.getMediaOutId()).isEqualTo(UPDATED_MEDIA_OUT_ID);
        assertThat(testMediaOut.getMediaOutName()).isEqualTo(UPDATED_MEDIA_OUT_NAME);
        assertThat(testMediaOut.getMediaOutType()).isEqualTo(UPDATED_MEDIA_OUT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingMediaOut() throws Exception {
        int databaseSizeBeforeUpdate = mediaOutRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMediaOutMockMvc
            .perform(put("/api/media-outs").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mediaOut)))
            .andExpect(status().isBadRequest());

        // Validate the MediaOut in the database
        List<MediaOut> mediaOutList = mediaOutRepository.findAll();
        assertThat(mediaOutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMediaOut() throws Exception {
        // Initialize the database
        mediaOutRepository.saveAndFlush(mediaOut);

        int databaseSizeBeforeDelete = mediaOutRepository.findAll().size();

        // Delete the mediaOut
        restMediaOutMockMvc
            .perform(delete("/api/media-outs/{id}", mediaOut.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MediaOut> mediaOutList = mediaOutRepository.findAll();
        assertThat(mediaOutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
