package com.grihakhata.service;

import com.grihakhata.domain.TransactionLedger;
import com.grihakhata.dto.LedgerResponseDTO;
import com.grihakhata.dto.RentPaymentRequestDTO;

import java.util.List;

public interface TransactionLedgerService {
    TransactionLedger create(TransactionLedger ledger);
    TransactionLedger getById(Long id);
    List<LedgerResponseDTO> getByRenter(Long renterId);
    LedgerResponseDTO logRentPayment(RentPaymentRequestDTO request);
}
