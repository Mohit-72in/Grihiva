package com.grihakhata.controller;

import com.grihakhata.dto.LedgerResponseDTO;
import com.grihakhata.dto.RentPaymentRequestDTO;
import com.grihakhata.service.TransactionLedgerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Rent & Ledger", description = "Rent collection and ledger history")
@SecurityRequirement(name = "bearerAuth")
public class TransactionController {
    private final TransactionLedgerService transactionLedgerService;

    public TransactionController(TransactionLedgerService transactionLedgerService) {
        this.transactionLedgerService = transactionLedgerService;
    }

    @Operation(summary = "Collect rent payment", description = "Record a rent payment and update ledger balances.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/rent/collect")
    public ResponseEntity<LedgerResponseDTO> collectRent(@Valid @RequestBody RentPaymentRequestDTO request) {
        return ResponseEntity.ok(transactionLedgerService.logRentPayment(request));
    }

    @Operation(summary = "Get renter ledger", description = "Fetch ledger history for a renter.")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/ledger/{renterId}")
    public ResponseEntity<List<LedgerResponseDTO>> getLedger(@PathVariable Long renterId) {
        return ResponseEntity.ok(transactionLedgerService.getByRenter(renterId));
    }
}
