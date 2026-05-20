package com.grihakhata.service.impl;

import com.grihakhata.domain.OwnerType;
import com.grihakhata.domain.PaymentMode;
import com.grihakhata.domain.LedgerStatus;
import com.grihakhata.domain.TransactionLedger;
import com.grihakhata.domain.TransactionPayment;
import com.grihakhata.domain.User;
import com.grihakhata.repository.TransactionLedgerRepository;
import com.grihakhata.repository.TransactionPaymentRepository;
import com.grihakhata.repository.UserRepository;
import com.grihakhata.service.TransactionLedgerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public class TransactionLedgerServiceImpl implements TransactionLedgerService {
    private final TransactionLedgerRepository transactionLedgerRepository;
    private final TransactionPaymentRepository transactionPaymentRepository;
    private final UserRepository userRepository;

    public TransactionLedgerServiceImpl(
            TransactionLedgerRepository transactionLedgerRepository,
            TransactionPaymentRepository transactionPaymentRepository,
            UserRepository userRepository
    ) {
        this.transactionLedgerRepository = transactionLedgerRepository;
        this.transactionPaymentRepository = transactionPaymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TransactionLedger create(TransactionLedger ledger) {
        return transactionLedgerRepository.save(ledger);
    }

    @Override
    public TransactionLedger getById(Long id) {
        return transactionLedgerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Transaction ledger not found"));
    }

    @Override
    public List<TransactionLedger> getByRenter(Long renterId) {
        return transactionLedgerRepository.findByRenterId(renterId);
    }

    @Override
    @Transactional
    public TransactionLedger logRentPayment(Long renterId, Long unitId, BigDecimal amountPaid, String paymentMode, OwnerType collectedBy) {
        if (amountPaid == null || amountPaid.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amountPaid must be greater than zero");
        }

        TransactionLedger ledger = transactionLedgerRepository
                .findTopByRenterIdAndUnitIdOrderByBillingPeriodDesc(renterId, unitId)
                .orElseThrow(() -> new EntityNotFoundException("No ledger found for renter and unit"));

        PaymentMode resolvedMode;
        try {
            resolvedMode = PaymentMode.valueOf(paymentMode.trim().toUpperCase());
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Invalid paymentMode: " + paymentMode);
        }

        User collector = null;
        if (collectedBy != null && collectedBy != OwnerType.NONE) {
            collector = userRepository.findFirstByOwnerType(collectedBy)
                    .orElseThrow(() -> new EntityNotFoundException("Collector not found for owner type"));
        }

        TransactionPayment payment = new TransactionPayment();
        payment.setLedgerEntry(ledger);
        payment.setCollectedBy(collector);
        payment.setPaymentMode(resolvedMode);
        payment.setAmount(amountPaid);
        payment.setReceivedAt(Instant.now());
        transactionPaymentRepository.save(payment);

        BigDecimal currentPaid = ledger.getTotalPaid() == null ? BigDecimal.ZERO : ledger.getTotalPaid();
        BigDecimal newTotalPaid = currentPaid.add(amountPaid);
        BigDecimal balance = ledger.getTotalDue().subtract(newTotalPaid);
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            balance = BigDecimal.ZERO;
        }

        ledger.setTotalPaid(newTotalPaid);
        ledger.setBalanceCarryForward(balance);
        if (balance.compareTo(BigDecimal.ZERO) == 0) {
            ledger.setStatus(LedgerStatus.PAID);
        } else if (newTotalPaid.compareTo(BigDecimal.ZERO) > 0) {
            ledger.setStatus(LedgerStatus.PARTIAL);
        } else {
            ledger.setStatus(LedgerStatus.UNPAID);
        }

        return transactionLedgerRepository.save(ledger);
    }
}
