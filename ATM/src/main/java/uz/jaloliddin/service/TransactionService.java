package uz.jaloliddin.service;

import java.util.List;
import java.util.Optional;
import uz.jaloliddin.domain.Transaction;

/**
 * Service Interface for managing {@link Transaction}.
 */
public interface TransactionService {
    /**
     * Save a transaction.
     *
     * @param transaction the entity to save.
     * @return the persisted entity.
     */
    Transaction save(Transaction transaction);

    /**
     * Partially updates a transaction.
     *
     * @param transaction the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Transaction> partialUpdate(Transaction transaction);

    /**
     * Get all the transactions.
     *
     * @return the list of entities.
     */
    List<Transaction> findAll();

    /**
     * Get the "id" transaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Transaction> findOne(Long id);

    /**
     * Delete the "id" transaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
