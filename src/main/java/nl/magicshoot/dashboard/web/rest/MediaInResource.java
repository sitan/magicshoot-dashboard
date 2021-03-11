package nl.magicshoot.dashboard.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.magicshoot.dashboard.domain.MediaIn;
import nl.magicshoot.dashboard.repository.MediaInRepository;
import nl.magicshoot.dashboard.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link nl.magicshoot.dashboard.domain.MediaIn}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MediaInResource {
    private final Logger log = LoggerFactory.getLogger(MediaInResource.class);

    private static final String ENTITY_NAME = "mediaIn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MediaInRepository mediaInRepository;

    public MediaInResource(MediaInRepository mediaInRepository) {
        this.mediaInRepository = mediaInRepository;
    }

    /**
     * {@code POST  /media-ins} : Create a new mediaIn.
     *
     * @param mediaIn the mediaIn to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mediaIn, or with status {@code 400 (Bad Request)} if the mediaIn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/media-ins")
    public ResponseEntity<MediaIn> createMediaIn(@RequestBody MediaIn mediaIn) throws URISyntaxException {
        log.debug("REST request to save MediaIn : {}", mediaIn);
        if (mediaIn.getId() != null) {
            throw new BadRequestAlertException("A new mediaIn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaIn result = mediaInRepository.save(mediaIn);
        return ResponseEntity
            .created(new URI("/api/media-ins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /media-ins} : Updates an existing mediaIn.
     *
     * @param mediaIn the mediaIn to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaIn,
     * or with status {@code 400 (Bad Request)} if the mediaIn is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mediaIn couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/media-ins")
    public ResponseEntity<MediaIn> updateMediaIn(@RequestBody MediaIn mediaIn) throws URISyntaxException {
        log.debug("REST request to update MediaIn : {}", mediaIn);
        if (mediaIn.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MediaIn result = mediaInRepository.save(mediaIn);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mediaIn.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /media-ins} : get all the mediaIns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mediaIns in body.
     */
    @GetMapping("/media-ins")
    public List<MediaIn> getAllMediaIns() {
        log.debug("REST request to get all MediaIns");
        return mediaInRepository.findAll();
    }

    /**
     * {@code GET  /media-ins/:id} : get the "id" mediaIn.
     *
     * @param id the id of the mediaIn to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mediaIn, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/media-ins/{id}")
    public ResponseEntity<MediaIn> getMediaIn(@PathVariable Long id) {
        log.debug("REST request to get MediaIn : {}", id);
        Optional<MediaIn> mediaIn = mediaInRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mediaIn);
    }

    /**
     * {@code DELETE  /media-ins/:id} : delete the "id" mediaIn.
     *
     * @param id the id of the mediaIn to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/media-ins/{id}")
    public ResponseEntity<Void> deleteMediaIn(@PathVariable Long id) {
        log.debug("REST request to delete MediaIn : {}", id);
        mediaInRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
