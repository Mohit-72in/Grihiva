package com.grihakhata.dto;

import com.grihakhata.domain.OwnerType;
import com.grihakhata.domain.PaymentMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rent payment request")
public class RentPaymentRequestDTO {
    @Schema(example = "1", description = "Unit identifier")
    @NotNull
    private Long unitId;

    @Schema(example = "3", description = "Renter identifier")
    @NotNull
    private Long renterId;

    @Schema(example = "2500.00", description = "Amount paid by the renter")
    @NotNull
    @Positive
    private BigDecimal amountPaid;

    @Schema(example = "UPI", description = "Payment mode (CASH, UPI)")
    @NotNull
    private PaymentMode paymentMode;

    @Schema(example = "FATHER", description = "Collected by owner (FATHER, UNCLE)")
    @NotNull
    private OwnerType collectedBy;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getRenterId() {
        return renterId;
    }

    public void setRenterId(Long renterId) {
        this.renterId = renterId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public PaymentMode getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public OwnerType getCollectedBy() {
        return collectedBy;
    }

    public void setCollectedBy(OwnerType collectedBy) {
        this.collectedBy = collectedBy;
    }
}
