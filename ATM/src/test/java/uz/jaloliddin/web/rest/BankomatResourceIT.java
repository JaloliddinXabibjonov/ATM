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
import uz.jaloliddin.domain.Bankomat;
import uz.jaloliddin.domain.enumeration.Type;
import uz.jaloliddin.repository.BankomatRepository;

/**
 * Integration tests for the {@link BankomatResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankomatResourceIT {

    private static final Type DEFAULT_TYPE = Type.UZCARD;
    private static final Type UPDATED_TYPE = Type.HUMO;

    private static final Double DEFAULT_MAXIMUM_WITHDRAWAL_AMOUNT = 1D;
    private static final Double UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT = 2D;

    private static final Double DEFAULT_COMMISSION_OF_OTHER_BANK = 1D;
    private static final Double UPDATED_COMMISSION_OF_OTHER_BANK = 2D;

    private static final Double DEFAULT_COMMISSION_OF_THIS_BANK = 1D;
    private static final Double UPDATED_COMMISSION_OF_THIS_BANK = 2D;

    private static final Long DEFAULT_AMOUNT_FOR_NOTIFICATION = 1L;
    private static final Long UPDATED_AMOUNT_FOR_NOTIFICATION = 2L;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bankomats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankomatRepository bankomatRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankomatMockMvc;

    private Bankomat bankomat;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankomat createEntity(EntityManager em) {
        Bankomat bankomat = new Bankomat()
            .type(DEFAULT_TYPE)
            .maximumWithdrawalAmount(DEFAULT_MAXIMUM_WITHDRAWAL_AMOUNT)
            .commissionOfOtherBank(DEFAULT_COMMISSION_OF_OTHER_BANK)
            .commissionOfThisBank(DEFAULT_COMMISSION_OF_THIS_BANK)
            .amountForNotification(DEFAULT_AMOUNT_FOR_NOTIFICATION)
            .address(DEFAULT_ADDRESS);
        return bankomat;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bankomat createUpdatedEntity(EntityManager em) {
        Bankomat bankomat = new Bankomat()
            .type(UPDATED_TYPE)
            .maximumWithdrawalAmount(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT)
            .commissionOfOtherBank(UPDATED_COMMISSION_OF_OTHER_BANK)
            .commissionOfThisBank(UPDATED_COMMISSION_OF_THIS_BANK)
            .amountForNotification(UPDATED_AMOUNT_FOR_NOTIFICATION)
            .address(UPDATED_ADDRESS);
        return bankomat;
    }

    @BeforeEach
    public void initTest() {
        bankomat = createEntity(em);
    }

    @Test
    @Transactional
    void createBankomat() throws Exception {
        int databaseSizeBeforeCreate = bankomatRepository.findAll().size();
        // Create the Bankomat
        restBankomatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isCreated());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeCreate + 1);
        Bankomat testBankomat = bankomatList.get(bankomatList.size() - 1);
        assertThat(testBankomat.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testBankomat.getMaximumWithdrawalAmount()).isEqualTo(DEFAULT_MAXIMUM_WITHDRAWAL_AMOUNT);
        assertThat(testBankomat.getCommissionOfOtherBank()).isEqualTo(DEFAULT_COMMISSION_OF_OTHER_BANK);
        assertThat(testBankomat.getCommissionOfThisBank()).isEqualTo(DEFAULT_COMMISSION_OF_THIS_BANK);
        assertThat(testBankomat.getAmountForNotification()).isEqualTo(DEFAULT_AMOUNT_FOR_NOTIFICATION);
        assertThat(testBankomat.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void createBankomatWithExistingId() throws Exception {
        // Create the Bankomat with an existing ID
        bankomat.setId(1L);

        int databaseSizeBeforeCreate = bankomatRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankomatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankomatRepository.findAll().size();
        // set the field null
        bankomat.setType(null);

        // Create the Bankomat, which fails.

        restBankomatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaximumWithdrawalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankomatRepository.findAll().size();
        // set the field null
        bankomat.setMaximumWithdrawalAmount(null);

        // Create the Bankomat, which fails.

        restBankomatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommissionOfOtherBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankomatRepository.findAll().size();
        // set the field null
        bankomat.setCommissionOfOtherBank(null);

        // Create the Bankomat, which fails.

        restBankomatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCommissionOfThisBankIsRequired() throws Exception {
        int databaseSizeBeforeTest = bankomatRepository.findAll().size();
        // set the field null
        bankomat.setCommissionOfThisBank(null);

        // Create the Bankomat, which fails.

        restBankomatMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBankomats() throws Exception {
        // Initialize the database
        bankomatRepository.saveAndFlush(bankomat);

        // Get all the bankomatList
        restBankomatMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bankomat.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].maximumWithdrawalAmount").value(hasItem(DEFAULT_MAXIMUM_WITHDRAWAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionOfOtherBank").value(hasItem(DEFAULT_COMMISSION_OF_OTHER_BANK.doubleValue())))
            .andExpect(jsonPath("$.[*].commissionOfThisBank").value(hasItem(DEFAULT_COMMISSION_OF_THIS_BANK.doubleValue())))
            .andExpect(jsonPath("$.[*].amountForNotification").value(hasItem(DEFAULT_AMOUNT_FOR_NOTIFICATION.intValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)));
    }

    @Test
    @Transactional
    void getBankomat() throws Exception {
        // Initialize the database
        bankomatRepository.saveAndFlush(bankomat);

        // Get the bankomat
        restBankomatMockMvc
            .perform(get(ENTITY_API_URL_ID, bankomat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bankomat.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.maximumWithdrawalAmount").value(DEFAULT_MAXIMUM_WITHDRAWAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.commissionOfOtherBank").value(DEFAULT_COMMISSION_OF_OTHER_BANK.doubleValue()))
            .andExpect(jsonPath("$.commissionOfThisBank").value(DEFAULT_COMMISSION_OF_THIS_BANK.doubleValue()))
            .andExpect(jsonPath("$.amountForNotification").value(DEFAULT_AMOUNT_FOR_NOTIFICATION.intValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS));
    }

    @Test
    @Transactional
    void getNonExistingBankomat() throws Exception {
        // Get the bankomat
        restBankomatMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBankomat() throws Exception {
        // Initialize the database
        bankomatRepository.saveAndFlush(bankomat);

        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();

        // Update the bankomat
        Bankomat updatedBankomat = bankomatRepository.findById(bankomat.getId()).get();
        // Disconnect from session so that the updates on updatedBankomat are not directly saved in db
        em.detach(updatedBankomat);
        updatedBankomat
            .type(UPDATED_TYPE)
            .maximumWithdrawalAmount(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT)
            .commissionOfOtherBank(UPDATED_COMMISSION_OF_OTHER_BANK)
            .commissionOfThisBank(UPDATED_COMMISSION_OF_THIS_BANK)
            .amountForNotification(UPDATED_AMOUNT_FOR_NOTIFICATION)
            .address(UPDATED_ADDRESS);

        restBankomatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBankomat.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBankomat))
            )
            .andExpect(status().isOk());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
        Bankomat testBankomat = bankomatList.get(bankomatList.size() - 1);
        assertThat(testBankomat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBankomat.getMaximumWithdrawalAmount()).isEqualTo(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT);
        assertThat(testBankomat.getCommissionOfOtherBank()).isEqualTo(UPDATED_COMMISSION_OF_OTHER_BANK);
        assertThat(testBankomat.getCommissionOfThisBank()).isEqualTo(UPDATED_COMMISSION_OF_THIS_BANK);
        assertThat(testBankomat.getAmountForNotification()).isEqualTo(UPDATED_AMOUNT_FOR_NOTIFICATION);
        assertThat(testBankomat.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void putNonExistingBankomat() throws Exception {
        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();
        bankomat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankomatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankomat.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBankomat() throws Exception {
        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();
        bankomat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankomatMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBankomat() throws Exception {
        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();
        bankomat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankomatMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankomatWithPatch() throws Exception {
        // Initialize the database
        bankomatRepository.saveAndFlush(bankomat);

        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();

        // Update the bankomat using partial update
        Bankomat partialUpdatedBankomat = new Bankomat();
        partialUpdatedBankomat.setId(bankomat.getId());

        partialUpdatedBankomat
            .type(UPDATED_TYPE)
            .maximumWithdrawalAmount(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT)
            .commissionOfThisBank(UPDATED_COMMISSION_OF_THIS_BANK);

        restBankomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankomat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankomat))
            )
            .andExpect(status().isOk());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
        Bankomat testBankomat = bankomatList.get(bankomatList.size() - 1);
        assertThat(testBankomat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBankomat.getMaximumWithdrawalAmount()).isEqualTo(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT);
        assertThat(testBankomat.getCommissionOfOtherBank()).isEqualTo(DEFAULT_COMMISSION_OF_OTHER_BANK);
        assertThat(testBankomat.getCommissionOfThisBank()).isEqualTo(UPDATED_COMMISSION_OF_THIS_BANK);
        assertThat(testBankomat.getAmountForNotification()).isEqualTo(DEFAULT_AMOUNT_FOR_NOTIFICATION);
        assertThat(testBankomat.getAddress()).isEqualTo(DEFAULT_ADDRESS);
    }

    @Test
    @Transactional
    void fullUpdateBankomatWithPatch() throws Exception {
        // Initialize the database
        bankomatRepository.saveAndFlush(bankomat);

        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();

        // Update the bankomat using partial update
        Bankomat partialUpdatedBankomat = new Bankomat();
        partialUpdatedBankomat.setId(bankomat.getId());

        partialUpdatedBankomat
            .type(UPDATED_TYPE)
            .maximumWithdrawalAmount(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT)
            .commissionOfOtherBank(UPDATED_COMMISSION_OF_OTHER_BANK)
            .commissionOfThisBank(UPDATED_COMMISSION_OF_THIS_BANK)
            .amountForNotification(UPDATED_AMOUNT_FOR_NOTIFICATION)
            .address(UPDATED_ADDRESS);

        restBankomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBankomat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBankomat))
            )
            .andExpect(status().isOk());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
        Bankomat testBankomat = bankomatList.get(bankomatList.size() - 1);
        assertThat(testBankomat.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testBankomat.getMaximumWithdrawalAmount()).isEqualTo(UPDATED_MAXIMUM_WITHDRAWAL_AMOUNT);
        assertThat(testBankomat.getCommissionOfOtherBank()).isEqualTo(UPDATED_COMMISSION_OF_OTHER_BANK);
        assertThat(testBankomat.getCommissionOfThisBank()).isEqualTo(UPDATED_COMMISSION_OF_THIS_BANK);
        assertThat(testBankomat.getAmountForNotification()).isEqualTo(UPDATED_AMOUNT_FOR_NOTIFICATION);
        assertThat(testBankomat.getAddress()).isEqualTo(UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void patchNonExistingBankomat() throws Exception {
        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();
        bankomat.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankomat.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBankomat() throws Exception {
        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();
        bankomat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankomatMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBankomat() throws Exception {
        int databaseSizeBeforeUpdate = bankomatRepository.findAll().size();
        bankomat.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankomatMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankomat))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bankomat in the database
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBankomat() throws Exception {
        // Initialize the database
        bankomatRepository.saveAndFlush(bankomat);

        int databaseSizeBeforeDelete = bankomatRepository.findAll().size();

        // Delete the bankomat
        restBankomatMockMvc
            .perform(delete(ENTITY_API_URL_ID, bankomat.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bankomat> bankomatList = bankomatRepository.findAll();
        assertThat(bankomatList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
