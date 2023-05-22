package uz.jaloliddin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import uz.jaloliddin.domain.enumeration.Currency;
import uz.jaloliddin.domain.enumeration.TypeOfPractice;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeOfPractice type;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Long amount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "card_number")
    private String cardNumber;

    @OneToMany(mappedBy = "transaction")
    @JsonIgnoreProperties(value = { "bankomat", "transaction" }, allowSetters = true)
    private Set<BanknoteBox> banknoteBoxes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeOfPractice getType() {
        return this.type;
    }

    public Transaction type(TypeOfPractice type) {
        this.setType(type);
        return this;
    }

    public void setType(TypeOfPractice type) {
        this.type = type;
    }

    public Long getAmount() {
        return this.amount;
    }

    public Transaction amount(Long amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public Transaction currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public Transaction cardNumber(String cardNumber) {
        this.setCardNumber(cardNumber);
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Set<BanknoteBox> getBanknoteBoxes() {
        return this.banknoteBoxes;
    }

    public void setBanknoteBoxes(Set<BanknoteBox> banknoteBoxes) {
        if (this.banknoteBoxes != null) {
            this.banknoteBoxes.forEach(i -> i.setTransaction(null));
        }
        if (banknoteBoxes != null) {
            banknoteBoxes.forEach(i -> i.setTransaction(this));
        }
        this.banknoteBoxes = banknoteBoxes;
    }

    public Transaction banknoteBoxes(Set<BanknoteBox> banknoteBoxes) {
        this.setBanknoteBoxes(banknoteBoxes);
        return this;
    }

    public Transaction addBanknoteBoxes(BanknoteBox banknoteBox) {
        this.banknoteBoxes.add(banknoteBox);
        banknoteBox.setTransaction(this);
        return this;
    }

    public Transaction removeBanknoteBoxes(BanknoteBox banknoteBox) {
        this.banknoteBoxes.remove(banknoteBox);
        banknoteBox.setTransaction(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", amount=" + getAmount() +
            ", currency='" + getCurrency() + "'" +
            ", cardNumber='" + getCardNumber() + "'" +
            "}";
    }
}
