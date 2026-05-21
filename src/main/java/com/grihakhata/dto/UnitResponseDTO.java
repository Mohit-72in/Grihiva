package com.grihakhata.dto;

import com.grihakhata.domain.UnitCategory;
import com.grihakhata.domain.UnitStatus;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Unit response payload")
public class UnitResponseDTO {
    @Schema(example = "10", description = "Unit identifier")
    private final Long id;

    @Schema(example = "A-101", description = "Unit number")
    private final String unitNumber;

    @Schema(example = "FLAT", description = "Unit category")
    private final UnitCategory category;

    @Schema(example = "12000.00", description = "Base rent amount")
    private final BigDecimal baseRent;

    @Schema(example = "VACANT", description = "Unit status")
    private final UnitStatus status;

    @Schema(example = "1", description = "Building identifier")
    private final Long buildingId;

    public UnitResponseDTO(Long id, String unitNumber, UnitCategory category, BigDecimal baseRent, UnitStatus status, Long buildingId) {
        this.id = id;
        this.unitNumber = unitNumber;
        this.category = category;
        this.baseRent = baseRent;
        this.status = status;
        this.buildingId = buildingId;
    }

    public Long getId() {
        return id;
    }

    public String getUnitNumber() {
        return unitNumber;
    }

    public UnitCategory getCategory() {
        return category;
    }

    public BigDecimal getBaseRent() {
        return baseRent;
    }

    public UnitStatus getStatus() {
        return status;
    }

    public Long getBuildingId() {
        return buildingId;
    }
}
