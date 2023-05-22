package uz.jaloliddin.bankomat.domain;

import uz.jaloliddin.bankomat.domain.enumeration.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
public class Card extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 16, max = 16)
    @Column(name = "card_number", length = 16, nullable = false, unique = true)
    private String cardNumber;

    @NotNull
    @Min(value = 3)
    @Max(value = 3)
    @Column(name = "cvv", nullable = false)
    private Integer cvv;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @NotNull
    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @NotNull
    @Size(min = 4, max = 4)
    @Column(name = "password", length = 4, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    private Bank bank;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Card id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public Card cardNumber(String cardNumber) {
        this.setCardNumber(cardNumber);
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Integer getCvv() {
        return this.cvv;
    }

    public Card cvv(Integer cvv) {
        this.setCvv(cvv);
        return this;
    }

    public void setCvv(Integer cvv) {
        this.cvv = cvv;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Card lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Card firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Instant getExpiryDate() {
        return this.expiryDate;
    }

    public Card expiryDate(Instant expiryDate) {
        this.setExpiryDate(expiryDate);
        return this;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPassword() {
        return this.password;
    }

    public Card password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Type getType() {
        return this.type;
    }

    public Card type(Type type) {
        this.setType(type);
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Card active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Card bank(Bank bank) {
        this.setBank(bank);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        return id != null && id.equals(((Card) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", cardNumber='" + getCardNumber() + "'" +
            ", cvv=" + getCvv() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", password='" + getPassword() + "'" +
            ", type='" + getType() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
