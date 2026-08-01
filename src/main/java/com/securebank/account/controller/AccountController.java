package com.securebank.account.controller;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;


import com.securebank.account.dto.AccountRequestDTO;
import com.securebank.account.dto.AccountResponseDTO;
import com.securebank.account.service.AccountService;
import com.securebank.response.ApiResponse;
import com.securebank.response.ResponseBuilder;


import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



@RestController
@RequestMapping("/api/accounts")
@CrossOrigin(origins = "*")
@Validated
public class AccountController {



    private static final Logger logger =
            LoggerFactory.getLogger(AccountController.class);



    private final AccountService accountService;




    public AccountController(

            AccountService accountService

    ) {

        this.accountService = accountService;

    }







    // ================= CREATE ACCOUNT =================


    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<AccountResponseDTO>> createAccount(

            @Valid @RequestBody AccountRequestDTO dto

    ) {



        logger.info(
                "Account creation request received"
        );



        AccountResponseDTO response =

                accountService.createAccount(dto);




        return new ResponseEntity<>(

                ResponseBuilder.success(

                        "Account created successfully",

                        response

                ),

                HttpStatus.CREATED

        );

    }









    // ================= DEPOSIT =================


    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PutMapping("/deposit")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> depositMoney(

            @RequestParam

            @NotBlank(message = "Account number is required")

            @Pattern(
                    regexp = "^ACC\\d{6}$",
                    message = "Invalid account number format"
            )

            String accountNumber,



            @RequestParam

            @DecimalMin(
                    value = "0.01",
                    message = "Amount must be greater than zero"
            )

            BigDecimal amount

    ) {



        AccountResponseDTO response =

                accountService.depositMoney(

                        accountNumber.trim(),

                        amount

                );




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Amount deposited successfully",

                        response

                )

        );

    }









    // ================= WITHDRAW =================


    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @PutMapping("/withdraw")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> withdrawMoney(

            @RequestParam String accountNumber,

            @RequestParam BigDecimal amount

    ) {



        AccountResponseDTO response =

                accountService.withdrawMoney(

                        accountNumber.trim(),

                        amount

                );




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Amount withdrawn successfully",

                        response

                )

        );

    }









    // ================= GET MY ACCOUNT =================


    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/my-account")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getMyAccount(){



        String email =

                SecurityContextHolder

                .getContext()

                .getAuthentication()

                .getName();




        AccountResponseDTO response =

                accountService.getMyAccount(email);




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer account fetched successfully",

                        response

                )

        );

    }









    // ================= GET ACCOUNT =================


    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccount(

            @PathVariable

            @NotBlank(message = "Account number is required")

            @Pattern(
                    regexp = "^ACC\\d{6}$",
                    message = "Invalid account number format"
            )

            String accountNumber

    ) {



        AccountResponseDTO response =

                accountService.getAccountByNumber(

                        accountNumber.trim()

                );




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Account details fetched successfully",

                        response

                )

        );

    }
    // ================= GET ALL ACCOUNTS (ADMIN) =================


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getAllAccounts(){



        List<AccountResponseDTO> response =

                accountService.getAllAccounts();




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "All accounts fetched successfully",

                        response

                )

        );

    }









    // ================= GET BALANCE =================


    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")
    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<ApiResponse<BigDecimal>> getBalance(

            @PathVariable String accountNumber

    ) {



        BigDecimal balance =

                accountService.getBalance(

                        accountNumber.trim()

                );




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Balance fetched successfully",

                        balance

                )

        );

    }









    // ================= BLOCK ACCOUNT =================


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{accountNumber}/block")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> blockAccount(

            @PathVariable String accountNumber

    ) {



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Account blocked successfully",

                        accountService.blockAccount(accountNumber)

                )

        );

    }









    // ================= UNBLOCK ACCOUNT =================


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{accountNumber}/unblock")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> unblockAccount(

            @PathVariable String accountNumber

    ) {



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Account unblocked successfully",

                        accountService.unblockAccount(accountNumber)

                )

        );

    }



}