package com.grihakhata.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(
    name = "units",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_building_unit_number", columnNames = {"building_id", "unit_number"})
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Unit extends BaseEntity {
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id", nullable = false)
    private Building building;

    @Column(name = "unit_number", nullable = false, length = 50)
    private String unitNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UnitCategory category;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal baseRent;

    @Column(precision = 12, scale = 2)
    private BigDecimal rentOverride;

    @Column(precision = 12, scale = 4)
    private BigDecimal electricityRateOverride;

    @Column(length = 50)
    private String meterNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private UnitStatus status = UnitStatus.VACANT;

    private LocalDate expectedVacateDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_renter_id")
    private User currentRenter;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @OneToMany(mappedBy = "unit")
    @Builder.Default
    private List<TransactionLedger> ledgerEntries = new ArrayList<>();
}
