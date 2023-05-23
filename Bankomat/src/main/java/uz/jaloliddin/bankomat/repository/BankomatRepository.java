package uz.jaloliddin.bankomat.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import uz.jaloliddin.bankomat.domain.Bankomat;

/**
 * Spring Data SQL repository for the Bankomat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankomatRepository extends JpaRepository<Bankomat, Long> {}
