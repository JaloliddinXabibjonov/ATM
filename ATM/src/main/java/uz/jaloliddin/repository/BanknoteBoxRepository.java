package uz.jaloliddin.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.jaloliddin.domain.BanknoteBox;

/**
 * Spring Data SQL repository for the BanknoteBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanknoteBoxRepository extends JpaRepository<BanknoteBox, Long> {}
