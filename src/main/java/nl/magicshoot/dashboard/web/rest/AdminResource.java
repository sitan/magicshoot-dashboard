package nl.magicshoot.dashboard.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import nl.magicshoot.dashboard.domain.Admin;
import nl.magicshoot.dashboard.repository.AdminRepository;
import nl.magicshoot.dashboard.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link nl.magicshoot.dashboard.domain.Admin}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AdminResource {
    private final Logger log = LoggerFactory.getLogger(AdminResource.class);

    private static final String ENTITY_NAME = "admin";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AdminRepository adminRepository;

    public AdminResource(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * {@code POST  /admins} : Create a new admin.
     *
     * @param admin the admin to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new admin, or with status {@code 400 (Bad Request)} if the admin has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admins")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) throws URISyntaxException {
        log.debug("REST request to save Admin : {}", admin);
        if (admin.getId() != null) {
            throw new BadRequestAlertException("A new admin cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Admin result = adminRepository.save(admin);
        return ResponseEntity
            .created(new URI("/api/admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /admins} : Updates an existing admin.
     *
     * @param admin the admin to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated admin,
     * or with status {@code 400 (Bad Request)} if the admin is not valid,
     * or with status {@code 500 (Internal Server Error)} if the admin couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/admins")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) throws URISyntaxException {
        log.debug("REST request to update Admin : {}", admin);
        if (admin.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Admin result = adminRepository.save(admin);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, admin.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /admins} : get all the admins.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of admins in body.
     */
    @GetMapping("/admins")
    public List<Admin> getAllAdmins() {
        log.debug("REST request to get all Admins");
        return adminRepository.findAll();
    }

    /**
     * {@code GET  /admins/:id} : get the "id" admin.
     *
     * @param id the id of the admin to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the admin, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admins/{id}")
    public ResponseEntity<Admin> getAdmin(@PathVariable Long id) {
        log.debug("REST request to get Admin : {}", id);
        Optional<Admin> admin = adminRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(admin);
    }

    /**
     * {@code DELETE  /admins/:id} : delete the "id" admin.
     *
     * @param id the id of the admin to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admins/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        log.debug("REST request to delete Admin : {}", id);
        adminRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
