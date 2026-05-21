package com.grihakhata.service.impl;

import com.grihakhata.domain.OwnerType;
import com.grihakhata.domain.LedgerStatus;
import com.grihakhata.domain.TransactionLedger;
import com.grihakhata.domain.TransactionPayment;
import com.grihakhata.domain.User;
import com.grihakhata.dto.LedgerResponseDTO;
import com.grihakhata.dto.RentPaymentRequestDTO;
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
import java.util.stream.Collectors;

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
    public List<LedgerResponseDTO> getByRenter(Long renterId) {
        return transactionLedgerRepository.findByRenterId(renterId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LedgerResponseDTO logRentPayment(RentPaymentRequestDTO request) {
        if (request.getAmountPaid() == null || request.getAmountPaid().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amountPaid must be greater than zero");
        }

        TransactionLedger ledger = transactionLedgerRepository
                .findTopByRenterIdAndUnitIdOrderByBillingPeriodDesc(request.getRenterId(), request.getUnitId())
                .orElseThrow(() -> new EntityNotFoundException("No ledger found for renter and unit"));

        if (request.getCollectedBy() == OwnerType.NONE) {
            throw new IllegalArgumentException("collectedBy must be FATHER or UNCLE");
        }

        User collector = userRepository.findFirstByOwnerType(request.getCollectedBy())
                .orElseThrow(() -> new EntityNotFoundException("Collector not found for owner type"));

        TransactionPayment payment = new TransactionPayment();
        payment.setLedgerEntry(ledger);
        payment.setCollectedBy(collector);
        payment.setPaymentMode(request.getPaymentMode());
        payment.setAmount(request.getAmountPaid());
        payment.setReceivedAt(Instant.now());
        transactionPaymentRepository.save(payment);

        BigDecimal currentPaid = ledger.getTotalPaid() == null ? BigDecimal.ZERO : ledger.getTotalPaid();
        BigDecimal newTotalPaid = currentPaid.add(request.getAmountPaid());
        BigDecimal totalDue = ledger.getTotalDue();
        if (totalDue == null) {
            BigDecimal rent = ledger.getRentAmount() == null ? BigDecimal.ZERO : ledger.getRentAmount();
            BigDecimal utilities = ledger.getUtilityAmount() == null ? BigDecimal.ZERO : ledger.getUtilityAmount();
            BigDecimal additional = ledger.getAdditionalCharges() == null ? BigDecimal.ZERO : ledger.getAdditionalCharges();
            BigDecimal previous = ledger.getPreviousBalance() == null ? BigDecimal.ZERO : ledger.getPreviousBalance();
            totalDue = rent.add(utilities).add(additional).add(previous);
            ledger.setTotalDue(totalDue);
        }

        BigDecimal balance = totalDue.subtract(newTotalPaid);
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

        TransactionLedger saved = transactionLedgerRepository.save(ledger);
        return toResponse(saved);
    }

    private LedgerResponseDTO toResponse(TransactionLedger ledger) {
        String period = ledger.getBillingPeriod() == null ? null : ledger.getBillingPeriod().toString();
        Long unitId = ledger.getUnit() == null ? null : ledger.getUnit().getId();
        Long renterId = ledger.getRenter() == null ? null : ledger.getRenter().getId();
        return new LedgerResponseDTO(
                ledger.getId(),
                unitId,
                renterId,
                period,
                ledger.getRentAmount(),
                ledger.getUtilityAmount(),
                ledger.getAdditionalCharges(),
                ledger.getPreviousBalance(),
                ledger.getTotalDue(),
                ledger.getTotalPaid(),
                ledger.getBalanceCarryForward(),
                ledger.getStatus()
        );
    }
}
