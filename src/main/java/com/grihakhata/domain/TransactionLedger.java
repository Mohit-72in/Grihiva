package com.grihakhata.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(
        name = "transaction_ledgers",
        indexes = {
                @Index(name = "idx_tx_ledger_unit_period", columnList = "unit_id,billing_period"),
                @Index(name = "idx_tx_ledger_renter_period", columnList = "renter_id,billing_period")
        }
)
public class TransactionLedger extends BaseEntity {
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "renter_id", nullable = false)
    private User renter;

    @Column(name = "billing_period", nullable = false, length = 7)
    private YearMonth billingPeriod;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal rentAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal utilityAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal additionalCharges;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal previousBalance;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalDue;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPaid;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal balanceCarryForward;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private LedgerStatus status;

    @OneToMany(mappedBy = "ledgerEntry")
    private List<TransactionPayment> payments = new ArrayList<>();

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }

    public YearMonth getBillingPeriod() {
        return billingPeriod;
    }

    public void setBillingPeriod(YearMonth billingPeriod) {
        this.billingPeriod = billingPeriod;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(BigDecimal rentAmount) {
        this.rentAmount = rentAmount;
    }

    public BigDecimal getUtilityAmount() {
        return utilityAmount;
    }

    public void setUtilityAmount(BigDecimal utilityAmount) {
        this.utilityAmount = utilityAmount;
    }

    public BigDecimal getAdditionalCharges() {
        return additionalCharges;
    }

    public void setAdditionalCharges(BigDecimal additionalCharges) {
        this.additionalCharges = additionalCharges;
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }

    public void setPreviousBalance(BigDecimal previousBalance) {
        this.previousBalance = previousBalance;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public void setTotalDue(BigDecimal totalDue) {
        this.totalDue = totalDue;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public BigDecimal getBalanceCarryForward() {
        return balanceCarryForward;
    }

    public void setBalanceCarryForward(BigDecimal balanceCarryForward) {
        this.balanceCarryForward = balanceCarryForward;
    }

    public LedgerStatus getStatus() {
        return status;
    }

    public void setStatus(LedgerStatus status) {
        this.status = status;
    }

    public List<TransactionPayment> getPayments() {
        return payments;
    }
}
