package com.grihakhata.dto;

import com.grihakhata.domain.UnitCategory;
import com.grihakhata.domain.UnitStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unit creation request")
public class UnitCreateRequest {
    @Schema(example = "1", description = "Building identifier")
    @NotNull
    private Long buildingId;

    @Schema(example = "A-101", description = "Unit number")
    @NotBlank
    private String unitNumber;

    @Schema(example = "FLAT", description = "Unit category")
    @NotNull
    private UnitCategory category;

    @Schema(example = "12000.00", description = "Base rent amount")
    @NotNull
    private BigDecimal baseRent;

    @Schema(example = "12500.00", description = "Optional rent override")
    private BigDecimal rentOverride;

    @Schema(example = "8.50", description = "Electricity rate override")
    private BigDecimal electricityRateOverride;

    @Schema(example = "MTR-001", description = "Meter number")
    private String meterNumber;

    @Schema(example = "VACANT", description = "Unit status")
    private UnitStatus status;

    @Schema(example = "2026-06-30", description = "Expected vacate date (YYYY-MM-DD)")
    private LocalDate expectedVacateDate;

    @Schema(example = "true", description = "Whether the unit is active")
    private Boolean active;

    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(String unitNumber) {
        this.unitNumber = unitNumber;
    }

    public UnitCategory getCategory() {
        return category;
    }

    public void setCategory(UnitCategory category) {
        this.category = category;
    }

    public BigDecimal getBaseRent() {
        return baseRent;
    }

    public void setBaseRent(BigDecimal baseRent) {
        this.baseRent = baseRent;
    }

    public BigDecimal getRentOverride() {
        return rentOverride;
    }

    public void setRentOverride(BigDecimal rentOverride) {
        this.rentOverride = rentOverride;
    }

    public BigDecimal getElectricityRateOverride() {
        return electricityRateOverride;
    }

    public void setElectricityRateOverride(BigDecimal electricityRateOverride) {
        this.electricityRateOverride = electricityRateOverride;
    }

    public String getMeterNumber() {
        return meterNumber;
    }

    public void setMeterNumber(String meterNumber) {
        this.meterNumber = meterNumber;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public void setStatus(UnitStatus status) {
        this.status = status;
    }

    public LocalDate getExpectedVacateDate() {
        return expectedVacateDate;
    }

    public void setExpectedVacateDate(LocalDate expectedVacateDate) {
        this.expectedVacateDate = expectedVacateDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
