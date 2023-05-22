package uz.jaloliddin.bankomat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import uz.jaloliddin.bankomat.domain.enumeration.Currency;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A BanknoteBox.
 */
@Entity
@Table(name = "banknote_box")
public class BanknoteBox extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private Currency currency;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne
    @JsonIgnoreProperties(value = { "banknoteBoxes", "bank" }, allowSetters = true)
    private Bankomat bankomat;

    @ManyToOne
    @JsonIgnoreProperties(value = { "banknoteBoxes" }, allowSetters = true)
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BanknoteBox id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public BanknoteBox currency(Currency currency) {
        this.setCurrency(currency);
        return this;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Integer getAmount() {
        return this.amount;
    }

    public BanknoteBox amount(Integer amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getValue() {
        return this.value;
    }

    public BanknoteBox value(Integer value) {
        this.setValue(value);
        return this;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Bankomat getBankomat() {
        return this.bankomat;
    }

    public void setBankomat(Bankomat bankomat) {
        this.bankomat = bankomat;
    }

    public BanknoteBox bankomat(Bankomat bankomat) {
        this.setBankomat(bankomat);
        return this;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public BanknoteBox transaction(Transaction transaction) {
        this.setTransaction(transaction);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BanknoteBox)) {
            return false;
        }
        return id != null && id.equals(((BanknoteBox) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BanknoteBox{" +
            "id=" + getId() +
            ", currency='" + getCurrency() + "'" +
            ", amount=" + getAmount() +
            ", value=" + getValue() +
            "}";
    }
}
