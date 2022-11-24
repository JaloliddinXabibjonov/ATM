package uz.pdp.bankomat.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uz.pdp.bankomat.entity.enums.CardType;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 16, min = 16)
    @Column(nullable = false, unique = true, length = 16)
    private Long cardNumber;

    @ManyToOne
    private Bank bank;

    @Size(min = 3,max = 3)
    @Column(nullable = false, length = 3)
    private Short cvv;

    @Column(nullable = false)
    private String customerFullName;

    @Size(min = 4, max = 4)
    @Column(nullable = false, length = 4)
    private Short expiryDate;

    @Size(min = 4, max = 4)
    @Column(nullable = false, length = 4)
    private Short password;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

}
