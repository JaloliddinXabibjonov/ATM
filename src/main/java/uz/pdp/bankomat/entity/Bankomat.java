package uz.pdp.bankomat.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uz.pdp.bankomat.entity.enums.CardType;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@Entity
public class Bankomat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    /**
     * Bankomatdan yechiladigan maksimal pul miqdori
     */
    @Column(nullable = false)
    private Long maxValueTakeOff;

    @ManyToOne
    private Bank bank;

    @ManyToMany
    private List<Commission> commissions;

    /**
     * Bank mas'ul xodimiga xabar yuborish uchun bankomatda qolgan minimum pul miqdori
     */
    @Column(nullable = false)
    private Long amountForNotification;

    @Column(nullable = false)
    private String address;

    @OneToMany
    private Map<String, Currency> banknotes;


}
