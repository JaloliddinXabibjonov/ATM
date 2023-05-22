package uz.jaloliddin.service;

import java.util.List;
import java.util.Optional;
import uz.jaloliddin.domain.Bankomat;

/**
 * Service Interface for managing {@link Bankomat}.
 */
public interface BankomatService {
    /**
     * Save a bankomat.
     *
     * @param bankomat the entity to save.
     * @return the persisted entity.
     */
    Bankomat save(Bankomat bankomat);

    /**
     * Partially updates a bankomat.
     *
     * @param bankomat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Bankomat> partialUpdate(Bankomat bankomat);

    /**
     * Get all the bankomats.
     *
     * @return the list of entities.
     */
    List<Bankomat> findAll();

    /**
     * Get the "id" bankomat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Bankomat> findOne(Long id);

    /**
     * Delete the "id" bankomat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
