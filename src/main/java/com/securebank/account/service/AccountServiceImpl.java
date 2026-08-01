package com.securebank.account.service;


import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import com.securebank.account.dto.AccountRequestDTO;
import com.securebank.account.dto.AccountResponseDTO;

import com.securebank.entity.Account;
import com.securebank.entity.Customer;

import com.securebank.enums.AccountStatus;

import com.securebank.repository.AccountRepository;
import com.securebank.repository.CustomerRepository;

import com.securebank.email.service.EmailService;




@Service
public class AccountServiceImpl implements AccountService {




    private static final Logger logger =
            LoggerFactory.getLogger(AccountServiceImpl.class);





    private final AccountRepository accountRepository;



    private final CustomerRepository customerRepository;



    private final EmailService emailService;








    public AccountServiceImpl(

            AccountRepository accountRepository,

            CustomerRepository customerRepository,

            EmailService emailService

    ) {


        this.accountRepository = accountRepository;


        this.customerRepository = customerRepository;


        this.emailService = emailService;


    }









    // ================= CREATE ACCOUNT =================



    @Override
    public AccountResponseDTO createAccount(

            AccountRequestDTO dto

    ) {



        String email =

                SecurityContextHolder

                .getContext()

                .getAuthentication()

                .getName();





        logger.info(

                "Account creation request received"

        );








        Customer customer =

                customerRepository

                .findByEmail(email)

                .orElseThrow(() -> {



                    logger.error(

                            "Account creation failed. Customer not found"

                    );



                    return new RuntimeException(

                            "Customer not found"

                    );



                });









        if(accountRepository.existsByCustomer_CustomerId(

                customer.getCustomerId()

        )) {



            logger.warn(

                    "Account creation failed. Customer already has an account. Customer ID: {}",

                    customer.getCustomerId()

            );





            throw new RuntimeException(

                    "Customer already has an account."

            );


        }









        Account account = new Account();







        account.setAccountNumber(

                "ACC" +

                String.format(

                        "%06d",

                        (int)(Math.random() * 1000000)

                )

        );







        account.setAccountType(

                dto.getAccountType()

        );







        account.setBalance(

                dto.getInitialBalance()

        );







        account.setStatus(

                AccountStatus.ACTIVE

        );







        account.setCustomer(

                customer

        );










        Account savedAccount =

                accountRepository.save(account);









        // ================= ACCOUNT CREATION EMAIL =================



        emailService.sendAccountCreationEmail(

                customer.getEmail(),

                customer.getFullName(),

                savedAccount.getAccountNumber(),

                savedAccount.getAccountType(),

                savedAccount.getBalance(),

                savedAccount.getStatus().name()

        );










        logger.info(

                "Account created successfully. Account Number: {}",

                savedAccount.getAccountNumber()

        );








        return convertToDTO(savedAccount);



    }
 // ================= GET ACCOUNT =================


    @Override
    public AccountResponseDTO getAccountByNumber(

            String accountNumber

    ) {



        logger.info(

                "Fetching account details. Account Number: {}",

                accountNumber

        );





        Account account =

                accountRepository

                .findByAccountNumber(accountNumber)

                .orElseThrow(() -> {



                    logger.error(

                            "Account not found. Account Number: {}",

                            accountNumber

                    );



                    return new RuntimeException(

                            "Account not found"

                    );


                });







        return convertToDTO(account);



    }











    // ================= GET ALL ACCOUNTS =================



    @Override
    public List<AccountResponseDTO> getAllAccounts() {



        logger.info(

                "Fetching all accounts"

        );







        List<AccountResponseDTO> accounts =



                accountRepository

                .findAll()

                .stream()

                .map(this::convertToDTO)

                .collect(Collectors.toList());







        logger.info(

                "Total accounts fetched: {}",

                accounts.size()

        );







        return accounts;



    }












    // ================= GET MY ACCOUNT =================



    @Override
    public AccountResponseDTO getMyAccount(

            String email

    ) {



        logger.info(

                "Fetching customer account. Email: {}",

                email

        );







        Customer customer =



                customerRepository

                .findByEmail(email)

                .orElseThrow(() -> {



                    return new RuntimeException(

                            "Customer not found"

                    );



                });









        Account account =



                accountRepository

                .findAllByCustomer_CustomerId(

                        customer.getCustomerId()

                )

                .stream()

                .findFirst()

                .orElseThrow(() -> {



                    return new RuntimeException(

                            "Account not found"

                    );



                });









        return convertToDTO(account);



    }
 // ================= DEPOSIT =================



    @Override
    public AccountResponseDTO depositMoney(

            String accountNumber,

            BigDecimal amount

    ) {



        logger.info(

                "Deposit request received for account: {}",

                accountNumber

        );








        Account account =



                accountRepository

                .findByAccountNumber(accountNumber)

                .orElseThrow(() -> {



                    return new RuntimeException(

                            "Account not found"

                    );



                });









        if(account.getStatus()

                == AccountStatus.BLOCKED) {



            throw new RuntimeException(

                    "Account is blocked"

            );


        }









        account.setBalance(

                account.getBalance()

                .add(amount)

        );









        Account saved =



                accountRepository.save(account);









        logger.info(

                "Deposit completed successfully for account: {}",

                accountNumber

        );









        return convertToDTO(saved);



    }













    // ================= WITHDRAW =================




    @Override
    public AccountResponseDTO withdrawMoney(

            String accountNumber,

            BigDecimal amount

    ) {



        logger.info(

                "Withdraw request received for account: {}",

                accountNumber

        );









        Account account =



                accountRepository

                .findByAccountNumber(accountNumber)

                .orElseThrow(() -> {



                    return new RuntimeException(

                            "Account not found"

                    );



                });









        if(account.getStatus()

                == AccountStatus.BLOCKED) {



            throw new RuntimeException(

                    "Account is blocked"

            );


        }









        if(account.getBalance()

                .compareTo(amount) < 0) {



            throw new RuntimeException(

                    "Insufficient balance"

            );


        }









        account.setBalance(

                account.getBalance()

                .subtract(amount)

        );









        Account saved =



                accountRepository.save(account);









        logger.info(

                "Withdrawal completed successfully for account: {}",

                accountNumber

        );









        return convertToDTO(saved);



    }
 // ================= BLOCK ACCOUNT =================



    @Override
    public AccountResponseDTO blockAccount(

            String accountNumber

    ) {



        Account account =



                accountRepository

                .findByAccountNumber(accountNumber)

                .orElseThrow(() ->

                        new RuntimeException(

                                "Account not found"

                        )

                );







        account.setStatus(

                AccountStatus.BLOCKED

        );







        Account saved =



                accountRepository.save(account);







        return convertToDTO(saved);



    }











    // ================= UNBLOCK ACCOUNT =================



    @Override
    public AccountResponseDTO unblockAccount(

            String accountNumber

    ) {



        Account account =



                accountRepository

                .findByAccountNumber(accountNumber)

                .orElseThrow(() ->

                        new RuntimeException(

                                "Account not found"

                        )

                );







        account.setStatus(

                AccountStatus.ACTIVE

        );







        Account saved =



                accountRepository.save(account);







        return convertToDTO(saved);



    }












    // ================= GET BALANCE =================



    @Override
    public BigDecimal getBalance(

            String accountNumber

    ) {



        Account account =



                accountRepository

                .findByAccountNumber(accountNumber)

                .orElseThrow(() ->

                        new RuntimeException(

                                "Account not found"

                        )

                );







        return account.getBalance();



    }












    // ================= DTO CONVERTER =================



    private AccountResponseDTO convertToDTO(

            Account account

    ) {



        AccountResponseDTO dto =

                new AccountResponseDTO();







        dto.setAccountId(

                account.getAccountId()

        );







        dto.setAccountNumber(

                account.getAccountNumber()

        );







        dto.setAccountType(

                account.getAccountType()

        );







        dto.setBalance(

                account.getBalance()

        );







        if(account.getCustomer() != null) {



            dto.setCustomerId(

                    account.getCustomer()

                    .getCustomerId()

            );







            dto.setCustomerName(

                    account.getCustomer()

                    .getFullName()

            );



        }







        dto.setStatus(

                account.getStatus()

                .name()

        );







        return dto;



    }


    }