package uz.jaloliddin.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.jaloliddin.domain.BanknoteBox;
import uz.jaloliddin.repository.BanknoteBoxRepository;
import uz.jaloliddin.service.BanknoteBoxService;
import uz.jaloliddin.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.jaloliddin.domain.BanknoteBox}.
 */
@RestController
@RequestMapping("/api")
public class BanknoteBoxResource {

    private final Logger log = LoggerFactory.getLogger(BanknoteBoxResource.class);

    private static final String ENTITY_NAME = "banknoteBox";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BanknoteBoxService banknoteBoxService;

    private final BanknoteBoxRepository banknoteBoxRepository;

    public BanknoteBoxResource(BanknoteBoxService banknoteBoxService, BanknoteBoxRepository banknoteBoxRepository) {
        this.banknoteBoxService = banknoteBoxService;
        this.banknoteBoxRepository = banknoteBoxRepository;
    }

    /**
     * {@code POST  /banknote-boxes} : Create a new banknoteBox.
     *
     * @param banknoteBox the banknoteBox to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new banknoteBox, or with status {@code 400 (Bad Request)} if the banknoteBox has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/banknote-boxes")
    public ResponseEntity<BanknoteBox> createBanknoteBox(@Valid @RequestBody BanknoteBox banknoteBox) throws URISyntaxException {
        log.debug("REST request to save BanknoteBox : {}", banknoteBox);
        if (banknoteBox.getId() != null) {
            throw new BadRequestAlertException("A new banknoteBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BanknoteBox result = banknoteBoxService.save(banknoteBox);
        return ResponseEntity
            .created(new URI("/api/banknote-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /banknote-boxes/:id} : Updates an existing banknoteBox.
     *
     * @param id the id of the banknoteBox to save.
     * @param banknoteBox the banknoteBox to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banknoteBox,
     * or with status {@code 400 (Bad Request)} if the banknoteBox is not valid,
     * or with status {@code 500 (Internal Server Error)} if the banknoteBox couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/banknote-boxes/{id}")
    public ResponseEntity<BanknoteBox> updateBanknoteBox(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BanknoteBox banknoteBox
    ) throws URISyntaxException {
        log.debug("REST request to update BanknoteBox : {}, {}", id, banknoteBox);
        if (banknoteBox.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banknoteBox.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banknoteBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        BanknoteBox result = banknoteBoxService.save(banknoteBox);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banknoteBox.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /banknote-boxes/:id} : Partial updates given fields of an existing banknoteBox, field will ignore if it is null
     *
     * @param id the id of the banknoteBox to save.
     * @param banknoteBox the banknoteBox to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated banknoteBox,
     * or with status {@code 400 (Bad Request)} if the banknoteBox is not valid,
     * or with status {@code 404 (Not Found)} if the banknoteBox is not found,
     * or with status {@code 500 (Internal Server Error)} if the banknoteBox couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/banknote-boxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BanknoteBox> partialUpdateBanknoteBox(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BanknoteBox banknoteBox
    ) throws URISyntaxException {
        log.debug("REST request to partial update BanknoteBox partially : {}, {}", id, banknoteBox);
        if (banknoteBox.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, banknoteBox.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!banknoteBoxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BanknoteBox> result = banknoteBoxService.partialUpdate(banknoteBox);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, banknoteBox.getId().toString())
        );
    }

    /**
     * {@code GET  /banknote-boxes} : get all the banknoteBoxes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of banknoteBoxes in body.
     */
    @GetMapping("/banknote-boxes")
    public List<BanknoteBox> getAllBanknoteBoxes() {
        log.debug("REST request to get all BanknoteBoxes");
        return banknoteBoxService.findAll();
    }

    /**
     * {@code GET  /banknote-boxes/:id} : get the "id" banknoteBox.
     *
     * @param id the id of the banknoteBox to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the banknoteBox, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/banknote-boxes/{id}")
    public ResponseEntity<BanknoteBox> getBanknoteBox(@PathVariable Long id) {
        log.debug("REST request to get BanknoteBox : {}", id);
        Optional<BanknoteBox> banknoteBox = banknoteBoxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(banknoteBox);
    }

    /**
     * {@code DELETE  /banknote-boxes/:id} : delete the "id" banknoteBox.
     *
     * @param id the id of the banknoteBox to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/banknote-boxes/{id}")
    public ResponseEntity<Void> deleteBanknoteBox(@PathVariable Long id) {
        log.debug("REST request to delete BanknoteBox : {}", id);
        banknoteBoxService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
