package uz.jaloliddin.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.jaloliddin.IntegrationTest;
import uz.jaloliddin.domain.BanknoteBox;
import uz.jaloliddin.domain.enumeration.Currency;
import uz.jaloliddin.repository.BanknoteBoxRepository;

/**
 * Integration tests for the {@link BanknoteBoxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanknoteBoxResourceIT {

    private static final Currency DEFAULT_CURRENCY = Currency.USD;
    private static final Currency UPDATED_CURRENCY = Currency.SUM;

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final String ENTITY_API_URL = "/api/banknote-boxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BanknoteBoxRepository banknoteBoxRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBanknoteBoxMockMvc;

    private BanknoteBox banknoteBox;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BanknoteBox createEntity(EntityManager em) {
        BanknoteBox banknoteBox = new BanknoteBox().currency(DEFAULT_CURRENCY).amount(DEFAULT_AMOUNT).value(DEFAULT_VALUE);
        return banknoteBox;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BanknoteBox createUpdatedEntity(EntityManager em) {
        BanknoteBox banknoteBox = new BanknoteBox().currency(UPDATED_CURRENCY).amount(UPDATED_AMOUNT).value(UPDATED_VALUE);
        return banknoteBox;
    }

    @BeforeEach
    public void initTest() {
        banknoteBox = createEntity(em);
    }

    @Test
    @Transactional
    void createBanknoteBox() throws Exception {
        int databaseSizeBeforeCreate = banknoteBoxRepository.findAll().size();
        // Create the BanknoteBox
        restBanknoteBoxMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isCreated());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeCreate + 1);
        BanknoteBox testBanknoteBox = banknoteBoxList.get(banknoteBoxList.size() - 1);
        assertThat(testBanknoteBox.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testBanknoteBox.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBanknoteBox.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    void createBanknoteBoxWithExistingId() throws Exception {
        // Create the BanknoteBox with an existing ID
        banknoteBox.setId(1L);

        int databaseSizeBeforeCreate = banknoteBoxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanknoteBoxMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCurrencyIsRequired() throws Exception {
        int databaseSizeBeforeTest = banknoteBoxRepository.findAll().size();
        // set the field null
        banknoteBox.setCurrency(null);

        // Create the BanknoteBox, which fails.

        restBanknoteBoxMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = banknoteBoxRepository.findAll().size();
        // set the field null
        banknoteBox.setAmount(null);

        // Create the BanknoteBox, which fails.

        restBanknoteBoxMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = banknoteBoxRepository.findAll().size();
        // set the field null
        banknoteBox.setValue(null);

        // Create the BanknoteBox, which fails.

        restBanknoteBoxMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBanknoteBoxes() throws Exception {
        // Initialize the database
        banknoteBoxRepository.saveAndFlush(banknoteBox);

        // Get all the banknoteBoxList
        restBanknoteBoxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banknoteBox.getId().intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @Test
    @Transactional
    void getBanknoteBox() throws Exception {
        // Initialize the database
        banknoteBoxRepository.saveAndFlush(banknoteBox);

        // Get the banknoteBox
        restBanknoteBoxMockMvc
            .perform(get(ENTITY_API_URL_ID, banknoteBox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banknoteBox.getId().intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingBanknoteBox() throws Exception {
        // Get the banknoteBox
        restBanknoteBoxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBanknoteBox() throws Exception {
        // Initialize the database
        banknoteBoxRepository.saveAndFlush(banknoteBox);

        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();

        // Update the banknoteBox
        BanknoteBox updatedBanknoteBox = banknoteBoxRepository.findById(banknoteBox.getId()).get();
        // Disconnect from session so that the updates on updatedBanknoteBox are not directly saved in db
        em.detach(updatedBanknoteBox);
        updatedBanknoteBox.currency(UPDATED_CURRENCY).amount(UPDATED_AMOUNT).value(UPDATED_VALUE);

        restBanknoteBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBanknoteBox.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBanknoteBox))
            )
            .andExpect(status().isOk());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
        BanknoteBox testBanknoteBox = banknoteBoxList.get(banknoteBoxList.size() - 1);
        assertThat(testBanknoteBox.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBanknoteBox.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBanknoteBox.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void putNonExistingBanknoteBox() throws Exception {
        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();
        banknoteBox.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanknoteBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banknoteBox.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanknoteBox() throws Exception {
        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();
        banknoteBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanknoteBoxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanknoteBox() throws Exception {
        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();
        banknoteBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanknoteBoxMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBanknoteBoxWithPatch() throws Exception {
        // Initialize the database
        banknoteBoxRepository.saveAndFlush(banknoteBox);

        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();

        // Update the banknoteBox using partial update
        BanknoteBox partialUpdatedBanknoteBox = new BanknoteBox();
        partialUpdatedBanknoteBox.setId(banknoteBox.getId());

        partialUpdatedBanknoteBox.currency(UPDATED_CURRENCY).value(UPDATED_VALUE);

        restBanknoteBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanknoteBox.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanknoteBox))
            )
            .andExpect(status().isOk());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
        BanknoteBox testBanknoteBox = banknoteBoxList.get(banknoteBoxList.size() - 1);
        assertThat(testBanknoteBox.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBanknoteBox.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testBanknoteBox.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void fullUpdateBanknoteBoxWithPatch() throws Exception {
        // Initialize the database
        banknoteBoxRepository.saveAndFlush(banknoteBox);

        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();

        // Update the banknoteBox using partial update
        BanknoteBox partialUpdatedBanknoteBox = new BanknoteBox();
        partialUpdatedBanknoteBox.setId(banknoteBox.getId());

        partialUpdatedBanknoteBox.currency(UPDATED_CURRENCY).amount(UPDATED_AMOUNT).value(UPDATED_VALUE);

        restBanknoteBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanknoteBox.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanknoteBox))
            )
            .andExpect(status().isOk());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
        BanknoteBox testBanknoteBox = banknoteBoxList.get(banknoteBoxList.size() - 1);
        assertThat(testBanknoteBox.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testBanknoteBox.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testBanknoteBox.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    void patchNonExistingBanknoteBox() throws Exception {
        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();
        banknoteBox.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanknoteBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banknoteBox.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanknoteBox() throws Exception {
        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();
        banknoteBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanknoteBoxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isBadRequest());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanknoteBox() throws Exception {
        int databaseSizeBeforeUpdate = banknoteBoxRepository.findAll().size();
        banknoteBox.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanknoteBoxMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banknoteBox))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BanknoteBox in the database
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanknoteBox() throws Exception {
        // Initialize the database
        banknoteBoxRepository.saveAndFlush(banknoteBox);

        int databaseSizeBeforeDelete = banknoteBoxRepository.findAll().size();

        // Delete the banknoteBox
        restBanknoteBoxMockMvc
            .perform(delete(ENTITY_API_URL_ID, banknoteBox.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BanknoteBox> banknoteBoxList = banknoteBoxRepository.findAll();
        assertThat(banknoteBoxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
