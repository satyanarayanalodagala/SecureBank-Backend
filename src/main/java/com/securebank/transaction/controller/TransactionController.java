package com.securebank.transaction.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.securebank.response.ApiResponse;
import com.securebank.response.ResponseBuilder;

import com.securebank.transaction.dto.DepositRequestDTO;
import com.securebank.transaction.dto.ReceiverResponseDTO;
import com.securebank.transaction.dto.TransactionRequestDTO;
import com.securebank.transaction.dto.TransactionResponseDTO;
import com.securebank.transaction.dto.TransactionSearchDTO;
import com.securebank.transaction.dto.WithdrawRequestDTO;

import com.securebank.transaction.service.TransactionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
@Validated
public class TransactionController {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    TransactionController.class
            );

    private final TransactionService transactionService;

    public TransactionController(

            TransactionService transactionService

    ) {

        this.transactionService = transactionService;

    }

    // ================= TEST API =================

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> test() {

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction API is working",

                        "Transaction API Working"

                )

        );

    }

    // ================= DEPOSIT =================

    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER','ROLE_ADMIN')")
    @PostMapping("/deposit")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> deposit(

            @Valid
            @RequestBody
            DepositRequestDTO dto

    ) {

        logger.info(
                "Deposit request received"
        );

        TransactionResponseDTO response =
                transactionService.deposit(dto);

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Amount deposited successfully",

                        response

                )

        );

    }

    // ================= WITHDRAW =================

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> withdraw(

            @Valid
            @RequestBody
            WithdrawRequestDTO dto

    ) {

        logger.info(
                "Withdraw request received"
        );

        TransactionResponseDTO response =
                transactionService.withdraw(dto);

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Amount withdrawn successfully",

                        response

                )

        );

    }

    // ================= FUND TRANSFER =================

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> transfer(

            @Valid
            @RequestBody
            TransactionRequestDTO dto

    ) {

        logger.info(
                "Fund transfer request received"
        );

        TransactionResponseDTO response =
                transactionService.transfer(dto);

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Amount transferred successfully",

                        response

                )

        );

    }

    // ================= RECEIVER LIST =================

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/receivers")
    public ResponseEntity<ApiResponse<List<ReceiverResponseDTO>>> getReceivers() {

        logger.info("Fetching receiver list");

        List<ReceiverResponseDTO> receivers =
                transactionService.getReceivers();

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Receiver list fetched successfully",

                        receivers

                )

        );

    }

    // ================= GET TRANSACTION DETAILS =================

    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{transactionId}")
    public ResponseEntity<ApiResponse<TransactionResponseDTO>> getTransactionById(

            @PathVariable
            Long transactionId

    ) {

        TransactionResponseDTO response =
                transactionService.getTransactionById(
                        transactionId
                );

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction details fetched successfully",

                        response

                )

        );

    }

    // ================= TRANSACTION HISTORY =================

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-history")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> myHistory() {

        logger.info(
                "Fetching logged-in customer transaction history"
        );

        List<TransactionResponseDTO> transactions =
                transactionService.getMyTransactionHistory();

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction history fetched successfully",

                        transactions

                )

        );

    }

    // ================= CUSTOMER SEARCH TRANSACTIONS =================

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> searchTransactions(

            @Valid
            @RequestBody
            TransactionSearchDTO dto

    ) {

        List<TransactionResponseDTO> transactions =
                transactionService.searchTransactions(dto);

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer transactions fetched successfully",

                        transactions

                )

        );

    }

    // ================= ADMIN SEARCH TRANSACTIONS =================

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/admin/search")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> adminSearchTransactions(

            @Valid
            @RequestBody
            TransactionSearchDTO dto

    ) {

        List<TransactionResponseDTO> transactions =
                transactionService.searchTransactions(dto);

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "All transactions fetched successfully",

                        transactions

                )

        );

    }

    // ================= ADMIN VIEW ALL TRANSACTIONS =================

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getAllTransactions() {

        List<TransactionResponseDTO> transactions =
                transactionService.getAllTransactions();

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "All transactions fetched successfully",

                        transactions

                )

        );

    }

}