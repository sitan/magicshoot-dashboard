package nl.magicshoot.dashboard.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.magicshoot.dashboard.domain.MediaOut;
import nl.magicshoot.dashboard.repository.MediaOutRepository;
import nl.magicshoot.dashboard.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link nl.magicshoot.dashboard.domain.MediaOut}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MediaOutResource {
    private final Logger log = LoggerFactory.getLogger(MediaOutResource.class);

    private static final String ENTITY_NAME = "mediaOut";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MediaOutRepository mediaOutRepository;

    public MediaOutResource(MediaOutRepository mediaOutRepository) {
        this.mediaOutRepository = mediaOutRepository;
    }

    /**
     * {@code POST  /media-outs} : Create a new mediaOut.
     *
     * @param mediaOut the mediaOut to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mediaOut, or with status {@code 400 (Bad Request)} if the mediaOut has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/media-outs")
    public ResponseEntity<MediaOut> createMediaOut(@RequestBody MediaOut mediaOut) throws URISyntaxException {
        log.debug("REST request to save MediaOut : {}", mediaOut);
        if (mediaOut.getId() != null) {
            throw new BadRequestAlertException("A new mediaOut cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MediaOut result = mediaOutRepository.save(mediaOut);
        return ResponseEntity
            .created(new URI("/api/media-outs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /media-outs} : Updates an existing mediaOut.
     *
     * @param mediaOut the mediaOut to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mediaOut,
     * or with status {@code 400 (Bad Request)} if the mediaOut is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mediaOut couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/media-outs")
    public ResponseEntity<MediaOut> updateMediaOut(@RequestBody MediaOut mediaOut) throws URISyntaxException {
        log.debug("REST request to update MediaOut : {}", mediaOut);
        if (mediaOut.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MediaOut result = mediaOutRepository.save(mediaOut);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mediaOut.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /media-outs} : get all the mediaOuts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mediaOuts in body.
     */
    @GetMapping("/media-outs")
    public List<MediaOut> getAllMediaOuts() {
        log.debug("REST request to get all MediaOuts");
        return mediaOutRepository.findAll();
    }

    /**
     * {@code GET  /media-outs/:id} : get the "id" mediaOut.
     *
     * @param id the id of the mediaOut to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mediaOut, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/media-outs/{id}")
    public ResponseEntity<MediaOut> getMediaOut(@PathVariable Long id) {
        log.debug("REST request to get MediaOut : {}", id);
        Optional<MediaOut> mediaOut = mediaOutRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mediaOut);
    }

    /**
     * {@code DELETE  /media-outs/:id} : delete the "id" mediaOut.
     *
     * @param id the id of the mediaOut to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/media-outs/{id}")
    public ResponseEntity<Void> deleteMediaOut(@PathVariable Long id) {
        log.debug("REST request to delete MediaOut : {}", id);
        mediaOutRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
