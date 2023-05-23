package uz.jaloliddin.bankomat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.jaloliddin.bankomat.domain.Card;
import uz.jaloliddin.bankomat.repository.CardRepository;
import uz.jaloliddin.bankomat.service.CardService;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Card}.
 */
@Service
@Transactional
public class CardServiceImpl implements CardService {

    private final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

    private final CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card save(Card card) {
        log.debug("Request to save Card : {}", card);
        return cardRepository.save(card);
    }

    @Override
    public Optional<Card> partialUpdate(Card card) {
        log.debug("Request to partially update Card : {}", card);

        return cardRepository
                .findById(card.getId())
                .map(existingCard -> {
                    if (card.getCardNumber() != null) {
                        existingCard.setCardNumber(card.getCardNumber());
                    }
                    if (card.getCvv() != null) {
                        existingCard.setCvv(card.getCvv());
                    }
                    if (card.getLastName() != null) {
                        existingCard.setLastName(card.getLastName());
                    }
                    if (card.getFirstName() != null) {
                        existingCard.setFirstName(card.getFirstName());
                    }
                    if (card.getExpiryDate() != null) {
                        existingCard.setExpiryDate(card.getExpiryDate());
                    }
                    if (card.getPassword() != null) {
                        existingCard.setPassword(card.getPassword());
                    }
                    if (card.getType() != null) {
                        existingCard.setType(card.getType());
                    }
                    if (card.getActive() != null) {
                        existingCard.setActive(card.getActive());
                    }

                    return existingCard;
                })
                .map(cardRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Card> findAll() {
        log.debug("Request to get all Cards");
        return cardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Card> findOne(Long id) {
        log.debug("Request to get Card : {}", id);
        return cardRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Card : {}", id);
        cardRepository.deleteById(id);
    }
}
