package nl.magicshoot.dashboard.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.magicshoot.dashboard.domain.Print;
import nl.magicshoot.dashboard.repository.PrintRepository;
import nl.magicshoot.dashboard.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link nl.magicshoot.dashboard.domain.Print}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PrintResource {
    private final Logger log = LoggerFactory.getLogger(PrintResource.class);

    private static final String ENTITY_NAME = "print";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrintRepository printRepository;

    public PrintResource(PrintRepository printRepository) {
        this.printRepository = printRepository;
    }

    /**
     * {@code POST  /prints} : Create a new print.
     *
     * @param print the print to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new print, or with status {@code 400 (Bad Request)} if the print has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/prints")
    public ResponseEntity<Print> createPrint(@RequestBody Print print) throws URISyntaxException {
        log.debug("REST request to save Print : {}", print);
        if (print.getId() != null) {
            throw new BadRequestAlertException("A new print cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Print result = printRepository.save(print);
        return ResponseEntity
            .created(new URI("/api/prints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /prints} : Updates an existing print.
     *
     * @param print the print to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated print,
     * or with status {@code 400 (Bad Request)} if the print is not valid,
     * or with status {@code 500 (Internal Server Error)} if the print couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/prints")
    public ResponseEntity<Print> updatePrint(@RequestBody Print print) throws URISyntaxException {
        log.debug("REST request to update Print : {}", print);
        if (print.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Print result = printRepository.save(print);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, print.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /prints} : get all the prints.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prints in body.
     */
    @GetMapping("/prints")
    public List<Print> getAllPrints() {
        log.debug("REST request to get all Prints");
        return printRepository.findAll();
    }

    /**
     * {@code GET  /prints/:id} : get the "id" print.
     *
     * @param id the id of the print to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the print, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/prints/{id}")
    public ResponseEntity<Print> getPrint(@PathVariable Long id) {
        log.debug("REST request to get Print : {}", id);
        Optional<Print> print = printRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(print);
    }

    /**
     * {@code DELETE  /prints/:id} : delete the "id" print.
     *
     * @param id the id of the print to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/prints/{id}")
    public ResponseEntity<Void> deletePrint(@PathVariable Long id) {
        log.debug("REST request to delete Print : {}", id);
        printRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
