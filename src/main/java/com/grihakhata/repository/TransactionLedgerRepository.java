package com.grihakhata.repository;

import com.grihakhata.domain.TransactionLedger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TransactionLedgerRepository extends JpaRepository<TransactionLedger, Long> {
    List<TransactionLedger> findByRenterId(Long renterId);
    List<TransactionLedger> findByUnitId(Long unitId);
    Optional<TransactionLedger> findTopByRenterIdAndUnitIdOrderByBillingPeriodDesc(Long renterId, Long unitId);
}
