package com.grihakhata.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Table(name = "users")
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
    private OwnerType ownerType = OwnerType.NONE;

    @Column(length = 255)
    private String kycDocumentUrl;

    @Column(length = 20)
    private String kycLastFour;

    @Column(nullable = false)
    private boolean kycApproved = false;

    @OneToMany(mappedBy = "renter", fetch = FetchType.LAZY)
    private List<TransactionLedger> ledgerEntries = new ArrayList<>();

    @OneToMany(mappedBy = "collectedBy", fetch = FetchType.LAZY)
    private List<TransactionPayment> collectedPayments = new ArrayList<>();

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public OwnerType getOwnerType() {
        return ownerType;
    }

    public void setOwnerType(OwnerType ownerType) {
        this.ownerType = ownerType;
    }

    public String getKycDocumentUrl() {
        return kycDocumentUrl;
    }

    public void setKycDocumentUrl(String kycDocumentUrl) {
        this.kycDocumentUrl = kycDocumentUrl;
    }

    public String getKycLastFour() {
        return kycLastFour;
    }

    public void setKycLastFour(String kycLastFour) {
        this.kycLastFour = kycLastFour;
    }

    public boolean isKycApproved() {
        return kycApproved;
    }

    public void setKycApproved(boolean kycApproved) {
        this.kycApproved = kycApproved;
    }

    public List<TransactionLedger> getLedgerEntries() {
        return ledgerEntries;
    }

    public List<TransactionPayment> getCollectedPayments() {
        return collectedPayments;
    }
}
