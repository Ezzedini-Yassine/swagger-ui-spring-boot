package com.terrificventures.gearni_backend_spring_boot.web.rest;

import com.terrificventures.gearni_backend_spring_boot.domain.SupplierUser;
import com.terrificventures.gearni_backend_spring_boot.repository.SupplierUserRepository;
import com.terrificventures.gearni_backend_spring_boot.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.terrificventures.gearni_backend_spring_boot.domain.SupplierUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SupplierUserResource {

    private final Logger log = LoggerFactory.getLogger(SupplierUserResource.class);

    private static final String ENTITY_NAME = "supplierUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupplierUserRepository supplierUserRepository;

    public SupplierUserResource(SupplierUserRepository supplierUserRepository) {
        this.supplierUserRepository = supplierUserRepository;
    }

    /**
     * {@code POST  /supplier-users} : Create a new supplierUser.
     *
     * @param supplierUser the supplierUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supplierUser, or with status {@code 400 (Bad Request)} if the supplierUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supplier-users")
    public ResponseEntity<SupplierUser> createSupplierUser(@RequestBody SupplierUser supplierUser) throws URISyntaxException {
        log.debug("REST request to save SupplierUser : {}", supplierUser);
        if (supplierUser.getId() != null) {
            throw new BadRequestAlertException("A new supplierUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupplierUser result = supplierUserRepository.save(supplierUser);
        return ResponseEntity
            .created(new URI("/api/supplier-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supplier-users/:id} : Updates an existing supplierUser.
     *
     * @param id the id of the supplierUser to save.
     * @param supplierUser the supplierUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierUser,
     * or with status {@code 400 (Bad Request)} if the supplierUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supplierUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supplier-users/{id}")
    public ResponseEntity<SupplierUser> updateSupplierUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SupplierUser supplierUser
    ) throws URISyntaxException {
        log.debug("REST request to update SupplierUser : {}, {}", id, supplierUser);
        if (supplierUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplierUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplierUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SupplierUser result = supplierUserRepository.save(supplierUser);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /supplier-users/:id} : Partial updates given fields of an existing supplierUser, field will ignore if it is null
     *
     * @param id the id of the supplierUser to save.
     * @param supplierUser the supplierUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supplierUser,
     * or with status {@code 400 (Bad Request)} if the supplierUser is not valid,
     * or with status {@code 404 (Not Found)} if the supplierUser is not found,
     * or with status {@code 500 (Internal Server Error)} if the supplierUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/supplier-users/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SupplierUser> partialUpdateSupplierUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SupplierUser supplierUser
    ) throws URISyntaxException {
        log.debug("REST request to partial update SupplierUser partially : {}, {}", id, supplierUser);
        if (supplierUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, supplierUser.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!supplierUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SupplierUser> result = supplierUserRepository
            .findById(supplierUser.getId())
            .map(existingSupplierUser -> {
                if (supplierUser.getName() != null) {
                    existingSupplierUser.setName(supplierUser.getName());
                }
                if (supplierUser.getProfilePicture() != null) {
                    existingSupplierUser.setProfilePicture(supplierUser.getProfilePicture());
                }
                if (supplierUser.getEmail() != null) {
                    existingSupplierUser.setEmail(supplierUser.getEmail());
                }
                if (supplierUser.getPhoneNumber() != null) {
                    existingSupplierUser.setPhoneNumber(supplierUser.getPhoneNumber());
                }
                if (supplierUser.getIsApproved() != null) {
                    existingSupplierUser.setIsApproved(supplierUser.getIsApproved());
                }
                if (supplierUser.getRefreshToken() != null) {
                    existingSupplierUser.setRefreshToken(supplierUser.getRefreshToken());
                }
                if (supplierUser.getRefreshTokenExpires() != null) {
                    existingSupplierUser.setRefreshTokenExpires(supplierUser.getRefreshTokenExpires());
                }
                if (supplierUser.getRole() != null) {
                    existingSupplierUser.setRole(supplierUser.getRole());
                }

                return existingSupplierUser;
            })
            .map(supplierUserRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, supplierUser.getId().toString())
        );
    }

    /**
     * {@code GET  /supplier-users} : get all the supplierUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supplierUsers in body.
     */
    @GetMapping("/supplier-users")
    public List<SupplierUser> getAllSupplierUsers() {
        log.debug("REST request to get all SupplierUsers");
        return supplierUserRepository.findAll();
    }

    /**
     * {@code GET  /supplier-users/:id} : get the "id" supplierUser.
     *
     * @param id the id of the supplierUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supplierUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supplier-users/{id}")
    public ResponseEntity<SupplierUser> getSupplierUser(@PathVariable Long id) {
        log.debug("REST request to get SupplierUser : {}", id);
        Optional<SupplierUser> supplierUser = supplierUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(supplierUser);
    }

    /**
     * {@code DELETE  /supplier-users/:id} : delete the "id" supplierUser.
     *
     * @param id the id of the supplierUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supplier-users/{id}")
    public ResponseEntity<Void> deleteSupplierUser(@PathVariable Long id) {
        log.debug("REST request to delete SupplierUser : {}", id);
        supplierUserRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
