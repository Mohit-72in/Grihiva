package com.grihakhata.dto;

import com.grihakhata.domain.LedgerStatus;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ledger response for a renter")
public class LedgerResponseDTO {
    @Schema(example = "10", description = "Ledger entry identifier")
    private final Long ledgerId;

    @Schema(example = "1", description = "Unit identifier")
    private final Long unitId;

    @Schema(example = "3", description = "Renter identifier")
    private final Long renterId;

    @Schema(example = "2026-05", description = "Billing period (YYYY-MM)")
    private final String billingPeriod;

    @Schema(example = "2500.00", description = "Base rent amount")
    private final BigDecimal rentAmount;

    @Schema(example = "200.00", description = "Utility charges")
    private final BigDecimal utilityAmount;

    @Schema(example = "0.00", description = "Additional charges")
    private final BigDecimal additionalCharges;

    @Schema(example = "0.00", description = "Previous balance")
    private final BigDecimal previousBalance;

    @Schema(example = "2700.00", description = "Total amount due")
    private final BigDecimal totalDue;

    @Schema(example = "2000.00", description = "Total amount paid")
    private final BigDecimal totalPaid;

    @Schema(example = "700.00", description = "Balance to carry forward")
    private final BigDecimal balanceCarryForward;

    @Schema(example = "PARTIAL", description = "Ledger status")
    private final LedgerStatus status;

    public LedgerResponseDTO(
            Long ledgerId,
            Long unitId,
            Long renterId,
            String billingPeriod,
            BigDecimal rentAmount,
            BigDecimal utilityAmount,
            BigDecimal additionalCharges,
            BigDecimal previousBalance,
            BigDecimal totalDue,
            BigDecimal totalPaid,
            BigDecimal balanceCarryForward,
            LedgerStatus status
    ) {
        this.ledgerId = ledgerId;
        this.unitId = unitId;
        this.renterId = renterId;
        this.billingPeriod = billingPeriod;
        this.rentAmount = rentAmount;
        this.utilityAmount = utilityAmount;
        this.additionalCharges = additionalCharges;
        this.previousBalance = previousBalance;
        this.totalDue = totalDue;
        this.totalPaid = totalPaid;
        this.balanceCarryForward = balanceCarryForward;
        this.status = status;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public Long getRenterId() {
        return renterId;
    }

    public String getBillingPeriod() {
        return billingPeriod;
    }

    public BigDecimal getRentAmount() {
        return rentAmount;
    }

    public BigDecimal getUtilityAmount() {
        return utilityAmount;
    }

    public BigDecimal getAdditionalCharges() {
        return additionalCharges;
    }

    public BigDecimal getPreviousBalance() {
        return previousBalance;
    }

    public BigDecimal getTotalDue() {
        return totalDue;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public BigDecimal getBalanceCarryForward() {
        return balanceCarryForward;
    }

    public LedgerStatus getStatus() {
        return status;
    }
}
