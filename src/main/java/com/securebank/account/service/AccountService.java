package com.securebank.account.service;


import java.math.BigDecimal;
import java.util.List;

import com.securebank.account.dto.AccountRequestDTO;
import com.securebank.account.dto.AccountResponseDTO;



public interface AccountService {



    AccountResponseDTO createAccount(
            AccountRequestDTO dto
    );



    AccountResponseDTO depositMoney(
            String accountNumber,
            BigDecimal amount
    );



    AccountResponseDTO withdrawMoney(
            String accountNumber,
            BigDecimal amount
    );



    AccountResponseDTO blockAccount(
            String accountNumber
    );



    AccountResponseDTO unblockAccount(
            String accountNumber
    );



    AccountResponseDTO getAccountByNumber(
            String accountNumber
    );



    List<AccountResponseDTO> getAllAccounts();



    BigDecimal getBalance(
            String accountNumber
    );



    // ================= GET MY ACCOUNT =================

    AccountResponseDTO getMyAccount(
            String email
    );


}