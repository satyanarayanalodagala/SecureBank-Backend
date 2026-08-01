package com.securebank.transaction.service;

import java.util.List;

import com.securebank.transaction.dto.DepositRequestDTO;
import com.securebank.transaction.dto.ReceiverResponseDTO;
import com.securebank.transaction.dto.TransactionRequestDTO;
import com.securebank.transaction.dto.TransactionResponseDTO;
import com.securebank.transaction.dto.TransactionSearchDTO;
import com.securebank.transaction.dto.WithdrawRequestDTO;

public interface TransactionService {

    // ================= DEPOSIT =================

    TransactionResponseDTO deposit(

            DepositRequestDTO dto

    );

    // ================= WITHDRAW =================

    TransactionResponseDTO withdraw(

            WithdrawRequestDTO dto

    );

    // ================= FUND TRANSFER =================

    TransactionResponseDTO transfer(

            TransactionRequestDTO dto

    );

    // ================= RECEIVER LIST =================

    List<ReceiverResponseDTO> getReceivers();

    // ================= TRANSACTION DETAILS =================

    TransactionResponseDTO getTransactionById(

            Long transactionId

    );

    // ================= CUSTOMER HISTORY =================

    List<TransactionResponseDTO> getMyTransactionHistory();

    // ================= SEARCH =================

    List<TransactionResponseDTO> searchTransactions(

            TransactionSearchDTO dto

    );

    // ================= ADMIN VIEW =================

    List<TransactionResponseDTO> getAllTransactions();

}