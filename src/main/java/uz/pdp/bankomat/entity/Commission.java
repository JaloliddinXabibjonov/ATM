package uz.pdp.bankomat.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@RequiredArgsConstructor
@Entity
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Karta to'ldirishdagi komissiya miqdori % da
     */
    @Column(nullable = false)
    private Short debit;

    /**
     * Karta to'ldirishdagi komissiya miqdori % da
     */
    @Column(nullable = false)
    private Short credit;

    private boolean success;
}
