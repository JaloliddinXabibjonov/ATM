package uz.jaloliddin.bankomat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jaloliddin.bankomat.domain.Bank;
import uz.jaloliddin.bankomat.repository.BankRepository;
import uz.jaloliddin.bankomat.service.BankService;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Bank}.
 */
@Service
@Transactional
public class BankServiceImpl implements BankService {

    private final Logger log = LoggerFactory.getLogger(BankServiceImpl.class);

    private final BankRepository bankRepository;

    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Bank save(Bank bank) {
        log.debug("Request to save Bank : {}", bank);
        return bankRepository.save(bank);
    }

    @Override
    public Optional<Bank> partialUpdate(Bank bank) {
        log.debug("Request to partially update Bank : {}", bank);

        return bankRepository
                .findById(bank.getId())
                .map(existingBank -> {
                    if (bank.getName() != null) {
                        existingBank.setName(bank.getName());
                    }

                    return existingBank;
                })
                .map(bankRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bank> findAll() {
        log.debug("Request to get all Banks");
        return bankRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bank> findOne(Long id) {
        log.debug("Request to get Bank : {}", id);
        return bankRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bank : {}", id);
        bankRepository.deleteById(id);
    }
}
