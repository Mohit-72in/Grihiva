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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    @Builder.Default
    private List<TransactionPayment> payments = new ArrayList<>();
}
