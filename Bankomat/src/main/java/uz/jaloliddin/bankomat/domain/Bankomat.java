package uz.jaloliddin.bankomat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import uz.jaloliddin.bankomat.domain.enumeration.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Bankomat.
 */
@Entity
@Table(name = "bankomat")
public class Bankomat extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Type type;

    @NotNull
    @Column(name = "maximum_withdrawal_amount", nullable = false)
    private Double maximumWithdrawalAmount;

    @NotNull
    @Column(name = "commission_of_other_bank", nullable = false)
    private Double commissionOfOtherBank;

    @NotNull
    @Column(name = "commission_of_this_bank", nullable = false)
    private Double commissionOfThisBank;

    @Column(name = "amount_for_notification")
    private Long amountForNotification;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "bankomat")
    @JsonIgnoreProperties(value = { "bankomat", "transaction" }, allowSetters = true)
    private Set<BanknoteBox> banknoteBoxes = new HashSet<>();

    @ManyToOne
    private Bank bank;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Bankomat id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return this.type;
    }

    public Bankomat type(Type type) {
        this.setType(type);
        return this;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Double getMaximumWithdrawalAmount() {
        return this.maximumWithdrawalAmount;
    }

    public Bankomat maximumWithdrawalAmount(Double maximumWithdrawalAmount) {
        this.setMaximumWithdrawalAmount(maximumWithdrawalAmount);
        return this;
    }

    public void setMaximumWithdrawalAmount(Double maximumWithdrawalAmount) {
        this.maximumWithdrawalAmount = maximumWithdrawalAmount;
    }

    public Double getCommissionOfOtherBank() {
        return this.commissionOfOtherBank;
    }

    public Bankomat commissionOfOtherBank(Double commissionOfOtherBank) {
        this.setCommissionOfOtherBank(commissionOfOtherBank);
        return this;
    }

    public void setCommissionOfOtherBank(Double commissionOfOtherBank) {
        this.commissionOfOtherBank = commissionOfOtherBank;
    }

    public Double getCommissionOfThisBank() {
        return this.commissionOfThisBank;
    }

    public Bankomat commissionOfThisBank(Double commissionOfThisBank) {
        this.setCommissionOfThisBank(commissionOfThisBank);
        return this;
    }

    public void setCommissionOfThisBank(Double commissionOfThisBank) {
        this.commissionOfThisBank = commissionOfThisBank;
    }

    public Long getAmountForNotification() {
        return this.amountForNotification;
    }

    public Bankomat amountForNotification(Long amountForNotification) {
        this.setAmountForNotification(amountForNotification);
        return this;
    }

    public void setAmountForNotification(Long amountForNotification) {
        this.amountForNotification = amountForNotification;
    }

    public String getAddress() {
        return this.address;
    }

    public Bankomat address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<BanknoteBox> getBanknoteBoxes() {
        return this.banknoteBoxes;
    }

    public void setBanknoteBoxes(Set<BanknoteBox> banknoteBoxes) {
        if (this.banknoteBoxes != null) {
            this.banknoteBoxes.forEach(i -> i.setBankomat(null));
        }
        if (banknoteBoxes != null) {
            banknoteBoxes.forEach(i -> i.setBankomat(this));
        }
        this.banknoteBoxes = banknoteBoxes;
    }

    public Bankomat banknoteBoxes(Set<BanknoteBox> banknoteBoxes) {
        this.setBanknoteBoxes(banknoteBoxes);
        return this;
    }

    public Bankomat addBanknoteBoxes(BanknoteBox banknoteBox) {
        this.banknoteBoxes.add(banknoteBox);
        banknoteBox.setBankomat(this);
        return this;
    }

    public Bankomat removeBanknoteBoxes(BanknoteBox banknoteBox) {
        this.banknoteBoxes.remove(banknoteBox);
        banknoteBox.setBankomat(null);
        return this;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bankomat bank(Bank bank) {
        this.setBank(bank);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bankomat)) {
            return false;
        }
        return id != null && id.equals(((Bankomat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bankomat{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", maximumWithdrawalAmount=" + getMaximumWithdrawalAmount() +
            ", commissionOfOtherBank=" + getCommissionOfOtherBank() +
            ", commissionOfThisBank=" + getCommissionOfThisBank() +
            ", amountForNotification=" + getAmountForNotification() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
