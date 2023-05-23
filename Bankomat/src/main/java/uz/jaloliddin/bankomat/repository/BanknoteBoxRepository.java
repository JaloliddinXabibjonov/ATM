package uz.jaloliddin.bankomat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.jaloliddin.bankomat.domain.BanknoteBox;

/**
 * Spring Data SQL repository for the BanknoteBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanknoteBoxRepository extends JpaRepository<BanknoteBox, Long> {}
