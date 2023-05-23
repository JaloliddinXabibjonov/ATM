package uz.jaloliddin.bankomat.service;

import uz.jaloliddin.bankomat.domain.BanknoteBox;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BanknoteBox}.
 */
public interface BanknoteBoxService {
    /**
     * Save a banknoteBox.
     *
     * @param banknoteBox the entity to save.
     * @return the persisted entity.
     */
    BanknoteBox save(BanknoteBox banknoteBox);

    /**
     * Partially updates a banknoteBox.
     *
     * @param banknoteBox the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BanknoteBox> partialUpdate(BanknoteBox banknoteBox);

    /**
     * Get all the banknoteBoxes.
     *
     * @return the list of entities.
     */
    List<BanknoteBox> findAll();

    /**
     * Get the "id" banknoteBox.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BanknoteBox> findOne(Long id);

    /**
     * Delete the "id" banknoteBox.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
