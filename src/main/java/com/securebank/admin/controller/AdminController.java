package com.securebank.admin.controller;


import java.util.List;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


import com.securebank.admin.dto.AdminDashboardResponseDTO;
import com.securebank.admin.dto.AdminLoginRequestDTO;
import com.securebank.admin.dto.AdminLoginResponseDTO;
import com.securebank.admin.dto.AdminRegisterRequestDTO;
import com.securebank.admin.dto.AdminRegisterResponseDTO;

import com.securebank.admin.service.AdminDashboardService;
import com.securebank.admin.service.AdminService;


import com.securebank.account.dto.AccountResponseDTO;
import com.securebank.account.service.AccountService;


import com.securebank.customer.dto.CustomerResponseDTO;
import com.securebank.customer.dto.CustomerUpdateDTO;
import com.securebank.customer.service.CustomerService;


import com.securebank.response.ApiResponse;
import com.securebank.response.ResponseBuilder;


import com.securebank.transaction.dto.TransactionResponseDTO;
import com.securebank.transaction.dto.TransactionSearchDTO;
import com.securebank.transaction.service.TransactionService;



@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {



    private static final Logger logger =
            LoggerFactory.getLogger(AdminController.class);





    private final AdminService adminService;

    private final AdminDashboardService adminDashboardService;

    private final CustomerService customerService;

    private final AccountService accountService;

    private final TransactionService transactionService;







    public AdminController(

            AdminService adminService,

            AdminDashboardService adminDashboardService,

            CustomerService customerService,

            AccountService accountService,

            TransactionService transactionService

    ) {


        this.adminService = adminService;

        this.adminDashboardService = adminDashboardService;

        this.customerService = customerService;

        this.accountService = accountService;

        this.transactionService = transactionService;


    }









    // ================= ADMIN REGISTER =================


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AdminRegisterResponseDTO>> register(

            @Valid @RequestBody AdminRegisterRequestDTO request

    ) {


        AdminRegisterResponseDTO response =
                adminService.register(request);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Admin registered successfully",

                        response

                )

        );


    }









    // ================= ADMIN LOGIN =================


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminLoginResponseDTO>> login(

            @RequestBody AdminLoginRequestDTO request,

            HttpServletRequest httpRequest

    ) {


        AdminLoginResponseDTO response =

                adminService.login(

                        request,

                        httpRequest

                );



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Admin login successful",

                        response

                )

        );


    }

    // ================= ADMIN DASHBOARD =================


    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<AdminDashboardResponseDTO>> getDashboard() {


        AdminDashboardResponseDTO response =
                adminDashboardService.getDashboardDetails();



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Dashboard fetched successfully",

                        response

                )

        );


    }









    // ================= VIEW ALL CUSTOMERS =================


    @GetMapping("/customers")
    public ResponseEntity<ApiResponse<List<CustomerResponseDTO>>> getAllCustomers() {


        List<CustomerResponseDTO> response =
                customerService.getAllCustomers();



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customers fetched successfully",

                        response

                )

        );


    }









    // ================= VIEW CUSTOMER BY ID =================


    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> getCustomerById(

            @PathVariable Long id

    ) {


        CustomerResponseDTO response =
                customerService.getCustomerById(id);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer fetched successfully",

                        response

                )

        );


    }









    // ================= UPDATE CUSTOMER =================


    @PutMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<CustomerResponseDTO>> updateCustomer(

            @PathVariable Long id,

            @RequestBody CustomerUpdateDTO dto

    ) {


        CustomerResponseDTO response =
                customerService.updateCustomer(id,dto);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer updated successfully",

                        response

                )

        );


    }









    // ================= DELETE CUSTOMER =================


    @DeleteMapping("/customers/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(

            @PathVariable Long id

    ) {


        customerService.deleteCustomer(id);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer deleted successfully",

                        "Customer deleted successfully"

                )

        );


    }









    // ======================================================
    // NEW API
    // VIEW ALL ACCOUNTS
    // ======================================================


    @GetMapping("/accounts")
    public ResponseEntity<ApiResponse<List<AccountResponseDTO>>> getAllAccounts() {



        List<AccountResponseDTO> response =

                accountService.getAllAccounts();




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Accounts fetched successfully",

                        response

                )

        );


    }









    // ================= VIEW ACCOUNT =================


    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> getAccountByNumber(

            @PathVariable String accountNumber

    ) {



        AccountResponseDTO response =

                accountService.getAccountByNumber(accountNumber);




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Account details fetched successfully",

                        response

                )

        );


    }









    // ================= BLOCK ACCOUNT =================


    @PutMapping("/accounts/{accountNumber}/block")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> blockAccount(

            @PathVariable String accountNumber

    ) {



        AccountResponseDTO response =

                accountService.blockAccount(accountNumber);




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Account blocked successfully",

                        response

                )

        );


    }









    // ================= UNBLOCK ACCOUNT =================


    @PutMapping("/accounts/{accountNumber}/unblock")
    public ResponseEntity<ApiResponse<AccountResponseDTO>> unblockAccount(

            @PathVariable String accountNumber

    ) {



        AccountResponseDTO response =

                accountService.unblockAccount(accountNumber);




        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Account unblocked successfully",

                        response

                )

        );


    }









    // ================= VIEW ALL TRANSACTIONS =================


    @GetMapping("/transactions")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> getAllTransactions() {


        List<TransactionResponseDTO> response =
                transactionService.getAllTransactions();



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transactions fetched successfully",

                        response

                )

        );


    }









    // ================= SEARCH TRANSACTIONS =================


    @PostMapping("/transactions/search")
    public ResponseEntity<ApiResponse<List<TransactionResponseDTO>>> searchTransactions(

            @RequestBody TransactionSearchDTO dto

    ) {


        List<TransactionResponseDTO> response =
                transactionService.searchTransactions(dto);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction search completed",

                        response

                )

        );


    }



}