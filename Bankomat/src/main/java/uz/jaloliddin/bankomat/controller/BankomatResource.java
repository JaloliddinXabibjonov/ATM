package uz.jaloliddin.bankomat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.jaloliddin.domain.Bankomat;
import uz.jaloliddin.repository.BankomatRepository;
import uz.jaloliddin.service.BankomatService;
import uz.jaloliddin.web.rest.errors.BadRequestAlertException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link uz.jaloliddin.domain.Bankomat}.
 */
@RestController
@RequestMapping("/api")
public class BankomatResource {

    private final Logger log = LoggerFactory.getLogger(BankomatResource.class);

    private static final String ENTITY_NAME = "bankomat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BankomatService bankomatService;

    private final BankomatRepository bankomatRepository;

    public BankomatResource(BankomatService bankomatService, BankomatRepository bankomatRepository) {
        this.bankomatService = bankomatService;
        this.bankomatRepository = bankomatRepository;
    }

    /**
     * {@code POST  /bankomats} : Create a new bankomat.
     *
     * @param bankomat the bankomat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new bankomat, or with status {@code 400 (Bad Request)} if the bankomat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/bankomats")
    public ResponseEntity<Bankomat> createBankomat(@Valid @RequestBody Bankomat bankomat) throws URISyntaxException {
        log.debug("REST request to save Bankomat : {}", bankomat);
        if (bankomat.getId() != null) {
            throw new BadRequestAlertException("A new bankomat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bankomat result = bankomatService.save(bankomat);
        return ResponseEntity
            .created(new URI("/api/bankomats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /bankomats/:id} : Updates an existing bankomat.
     *
     * @param id the id of the bankomat to save.
     * @param bankomat the bankomat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankomat,
     * or with status {@code 400 (Bad Request)} if the bankomat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the bankomat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/bankomats/{id}")
    public ResponseEntity<Bankomat> updateBankomat(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Bankomat bankomat
    ) throws URISyntaxException {
        log.debug("REST request to update Bankomat : {}, {}", id, bankomat);
        if (bankomat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankomat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankomatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Bankomat result = bankomatService.save(bankomat);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankomat.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /bankomats/:id} : Partial updates given fields of an existing bankomat, field will ignore if it is null
     *
     * @param id the id of the bankomat to save.
     * @param bankomat the bankomat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated bankomat,
     * or with status {@code 400 (Bad Request)} if the bankomat is not valid,
     * or with status {@code 404 (Not Found)} if the bankomat is not found,
     * or with status {@code 500 (Internal Server Error)} if the bankomat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/bankomats/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Bankomat> partialUpdateBankomat(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Bankomat bankomat
    ) throws URISyntaxException {
        log.debug("REST request to partial update Bankomat partially : {}, {}", id, bankomat);
        if (bankomat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, bankomat.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!bankomatRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Bankomat> result = bankomatService.partialUpdate(bankomat);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, bankomat.getId().toString())
        );
    }

    /**
     * {@code GET  /bankomats} : get all the bankomats.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of bankomats in body.
     */
    @GetMapping("/bankomats")
    public List<Bankomat> getAllBankomats() {
        log.debug("REST request to get all Bankomats");
        return bankomatService.findAll();
    }

    /**
     * {@code GET  /bankomats/:id} : get the "id" bankomat.
     *
     * @param id the id of the bankomat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the bankomat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/bankomats/{id}")
    public ResponseEntity<Bankomat> getBankomat(@PathVariable Long id) {
        log.debug("REST request to get Bankomat : {}", id);
        Optional<Bankomat> bankomat = bankomatService.findOne(id);
        return ResponseUtil.wrapOrNotFound(bankomat);
    }

    /**
     * {@code DELETE  /bankomats/:id} : delete the "id" bankomat.
     *
     * @param id the id of the bankomat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/bankomats/{id}")
    public ResponseEntity<Void> deleteBankomat(@PathVariable Long id) {
        log.debug("REST request to delete Bankomat : {}", id);
        bankomatService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
