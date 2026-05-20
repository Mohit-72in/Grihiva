package com.grihakhata.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(nullable = false, length = 120)
    private String fullName;

    @Column(nullable = false, length = 20, unique = true)
    private String phoneNumber;

    @Column(length = 120, unique = true)
    private String email;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private OwnerType ownerType = OwnerType.NONE;

    @Column(length = 255)
    private String kycDocumentUrl;

    @Column(length = 20)
    private String kycLastFour;

    @Column(nullable = false)
    @Builder.Default
    private boolean kycApproved = false;

    @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY)
    @Builder.Default
    private List<TransactionLedger> ledgerEntries = new ArrayList<>();

    @OneToMany(mappedBy = "collectedBy", fetch = FetchType.LAZY)
    @Builder.Default
    private List<TransactionPayment> collectedPayments = new ArrayList<>();
}
