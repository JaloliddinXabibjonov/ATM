package uz.jaloliddin.service.impl;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jaloliddin.domain.BanknoteBox;
import uz.jaloliddin.repository.BanknoteBoxRepository;
import uz.jaloliddin.service.BanknoteBoxService;

/**
 * Service Implementation for managing {@link BanknoteBox}.
 */
@Service
@Transactional
public class BanknoteBoxServiceImpl implements BanknoteBoxService {

    private final Logger log = LoggerFactory.getLogger(BanknoteBoxServiceImpl.class);

    private final BanknoteBoxRepository banknoteBoxRepository;

    public BanknoteBoxServiceImpl(BanknoteBoxRepository banknoteBoxRepository) {
        this.banknoteBoxRepository = banknoteBoxRepository;
    }

    @Override
    public BanknoteBox save(BanknoteBox banknoteBox) {
        log.debug("Request to save BanknoteBox : {}", banknoteBox);
        return banknoteBoxRepository.save(banknoteBox);
    }

    @Override
    public Optional<BanknoteBox> partialUpdate(BanknoteBox banknoteBox) {
        log.debug("Request to partially update BanknoteBox : {}", banknoteBox);

        return banknoteBoxRepository
            .findById(banknoteBox.getId())
            .map(existingBanknoteBox -> {
                if (banknoteBox.getCurrency() != null) {
                    existingBanknoteBox.setCurrency(banknoteBox.getCurrency());
                }
                if (banknoteBox.getAmount() != null) {
                    existingBanknoteBox.setAmount(banknoteBox.getAmount());
                }
                if (banknoteBox.getValue() != null) {
                    existingBanknoteBox.setValue(banknoteBox.getValue());
                }

                return existingBanknoteBox;
            })
            .map(banknoteBoxRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BanknoteBox> findAll() {
        log.debug("Request to get all BanknoteBoxes");
        return banknoteBoxRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BanknoteBox> findOne(Long id) {
        log.debug("Request to get BanknoteBox : {}", id);
        return banknoteBoxRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BanknoteBox : {}", id);
        banknoteBoxRepository.deleteById(id);
    }
}
