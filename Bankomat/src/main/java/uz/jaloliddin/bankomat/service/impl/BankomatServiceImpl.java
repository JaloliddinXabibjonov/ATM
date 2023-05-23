package uz.jaloliddin.bankomat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jaloliddin.bankomat.domain.Bankomat;
import uz.jaloliddin.bankomat.repository.BankomatRepository;
import uz.jaloliddin.bankomat.service.BankomatService;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Bankomat}.
 */
@Service
@Transactional
public class BankomatServiceImpl implements BankomatService {

    private final Logger log = LoggerFactory.getLogger(BankomatServiceImpl.class);

    private final BankomatRepository bankomatRepository;

    public BankomatServiceImpl(BankomatRepository bankomatRepository) {
        this.bankomatRepository = bankomatRepository;
    }

    @Override
    public Bankomat save(Bankomat bankomat) {
        log.debug("Request to save Bankomat : {}", bankomat);
        return bankomatRepository.save(bankomat);
    }

    @Override
    public Optional<Bankomat> partialUpdate(Bankomat bankomat) {
        log.debug("Request to partially update Bankomat : {}", bankomat);

        return bankomatRepository
                .findById(bankomat.getId())
                .map(existingBankomat -> {
                    if (bankomat.getType() != null) {
                        existingBankomat.setType(bankomat.getType());
                    }
                    if (bankomat.getMaximumWithdrawalAmount() != null) {
                        existingBankomat.setMaximumWithdrawalAmount(bankomat.getMaximumWithdrawalAmount());
                    }
                    if (bankomat.getCommissionOfOtherBank() != null) {
                        existingBankomat.setCommissionOfOtherBank(bankomat.getCommissionOfOtherBank());
                    }
                    if (bankomat.getCommissionOfThisBank() != null) {
                        existingBankomat.setCommissionOfThisBank(bankomat.getCommissionOfThisBank());
                    }
                    if (bankomat.getAmountForNotification() != null) {
                        existingBankomat.setAmountForNotification(bankomat.getAmountForNotification());
                    }
                    if (bankomat.getAddress() != null) {
                        existingBankomat.setAddress(bankomat.getAddress());
                    }

                    return existingBankomat;
                })
                .map(bankomatRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bankomat> findAll() {
        log.debug("Request to get all Bankomats");
        return bankomatRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bankomat> findOne(Long id) {
        log.debug("Request to get Bankomat : {}", id);
        return bankomatRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bankomat : {}", id);
        bankomatRepository.deleteById(id);
    }
}
