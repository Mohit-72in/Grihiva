package com.grihakhata.service;

import com.grihakhata.domain.OwnerType;
import com.grihakhata.domain.TransactionLedger;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionLedgerService {
    TransactionLedger create(TransactionLedger ledger);
    TransactionLedger getById(Long id);
    List<TransactionLedger> getByRenter(Long renterId);
    TransactionLedger logRentPayment(Long renterId, Long unitId, BigDecimal amountPaid, String paymentMode, OwnerType collectedBy);
}
