package nl.magicshoot.dashboard.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.magicshoot.dashboard.domain.Reseller;
import nl.magicshoot.dashboard.repository.ResellerRepository;
import nl.magicshoot.dashboard.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link nl.magicshoot.dashboard.domain.Reseller}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ResellerResource {
    private final Logger log = LoggerFactory.getLogger(ResellerResource.class);

    private static final String ENTITY_NAME = "reseller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ResellerRepository resellerRepository;

    public ResellerResource(ResellerRepository resellerRepository) {
        this.resellerRepository = resellerRepository;
    }

    /**
     * {@code POST  /resellers} : Create a new reseller.
     *
     * @param reseller the reseller to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reseller, or with status {@code 400 (Bad Request)} if the reseller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/resellers")
    public ResponseEntity<Reseller> createReseller(@RequestBody Reseller reseller) throws URISyntaxException {
        log.debug("REST request to save Reseller : {}", reseller);
        if (reseller.getId() != null) {
            throw new BadRequestAlertException("A new reseller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Reseller result = resellerRepository.save(reseller);
        return ResponseEntity
            .created(new URI("/api/resellers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /resellers} : Updates an existing reseller.
     *
     * @param reseller the reseller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reseller,
     * or with status {@code 400 (Bad Request)} if the reseller is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reseller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/resellers")
    public ResponseEntity<Reseller> updateReseller(@RequestBody Reseller reseller) throws URISyntaxException {
        log.debug("REST request to update Reseller : {}", reseller);
        if (reseller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Reseller result = resellerRepository.save(reseller);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reseller.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /resellers} : get all the resellers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of resellers in body.
     */
    @GetMapping("/resellers")
    public List<Reseller> getAllResellers() {
        log.debug("REST request to get all Resellers");
        return resellerRepository.findAll();
    }

    /**
     * {@code GET  /resellers/:id} : get the "id" reseller.
     *
     * @param id the id of the reseller to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reseller, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/resellers/{id}")
    public ResponseEntity<Reseller> getReseller(@PathVariable Long id) {
        log.debug("REST request to get Reseller : {}", id);
        Optional<Reseller> reseller = resellerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(reseller);
    }

    /**
     * {@code DELETE  /resellers/:id} : delete the "id" reseller.
     *
     * @param id the id of the reseller to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/resellers/{id}")
    public ResponseEntity<Void> deleteReseller(@PathVariable Long id) {
        log.debug("REST request to delete Reseller : {}", id);
        resellerRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
