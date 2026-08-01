package com.securebank.transaction.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securebank.customer.service.CustomerService;
import com.securebank.email.dto.EmailRequestDTO;
import com.securebank.email.service.EmailService;

import com.securebank.entity.Account;
import com.securebank.entity.Transaction;

import com.securebank.enums.TransactionType;
import com.securebank.enums.AccountStatus;
import com.securebank.entity.Account;
import com.securebank.exception.ResourceNotFoundException;

import com.securebank.repository.AccountRepository;
import com.securebank.repository.TransactionRepository;

import com.securebank.transaction.dto.DepositRequestDTO;
import com.securebank.transaction.dto.ReceiverResponseDTO;
import com.securebank.transaction.dto.TransactionRequestDTO;
import com.securebank.transaction.dto.TransactionResponseDTO;
import com.securebank.transaction.dto.TransactionSearchDTO;
import com.securebank.transaction.dto.WithdrawRequestDTO;
import com.securebank.transaction.service.TransactionService;

@Service
@Transactional
public class TransactionServiceImpl
        implements TransactionService {



    private static final Logger logger =
            LoggerFactory.getLogger(
                    TransactionServiceImpl.class
            );



    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    private final EmailService emailService;

    private final CustomerService customerService;

    
    public TransactionServiceImpl(
            TransactionRepository transactionRepository,
            AccountRepository accountRepository,
            EmailService emailService,
            CustomerService customerService
    )
    
    
    {

        this.transactionRepository =
                transactionRepository;

        this.accountRepository =
                accountRepository;

        this.emailService =
                emailService;
        this.customerService = customerService;

    }





 // ================= LOGGED USER ACCOUNT =================


    private Account getLoggedInAccount() {


        String email =

                SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();



        return accountRepository

                .findByCustomer_Email(email)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Customer account not found"
                        )
                );

    }


    // ================= ACCOUNT STATUS VALIDATION =================


    private void validateAccountStatus(Account account) {


        if (account.getStatus() == AccountStatus.BLOCKED) {


            throw new RuntimeException(
                    "Account is blocked. Transactions are not allowed."
            );


        }

    }
 // ================= DEPOSIT =================

    @Override
    public TransactionResponseDTO deposit(

            DepositRequestDTO dto

    ) {

        Account account =
                getLoggedInAccount();
        validateAccountStatus(account);
       
        
        
        if (!customerService.verifyTransactionPin(
                account.getCustomer().getEmail(),
                dto.getTransactionPin())) {

            throw new RuntimeException("Invalid Transaction PIN");
        }

        account.setBalance(

                account.getBalance()
                        .add(dto.getAmount())

        );

        accountRepository.save(account);

        Transaction transaction =
                new Transaction();

        transaction.setAccount(account);

        transaction.setTransactionType(

                TransactionType.DEPOSIT

        );

        transaction.setAmount(

                dto.getAmount()

        );

        transaction.setTransactionDate(

                LocalDate.now()

        );

        transaction.setTransactionTime(

                LocalDateTime.now()

        );

        transaction.setToAccount(

                account.getAccountNumber()

        );

        transaction.setDescription(

                dto.getDescription() == null
                        ? "Cash Deposit"
                        : dto.getDescription()

        );

        transactionRepository.save(transaction);

        sendDepositEmail(

                account,

                transaction

        );

        return mapToResponse(transaction);

    }
 // ================= WITHDRAW =================

    @Override
    public TransactionResponseDTO withdraw(

            WithdrawRequestDTO dto

    ) {

        Account account =
                getLoggedInAccount();
        validateAccountStatus(account);

        if (!customerService.verifyTransactionPin(
                account.getCustomer().getEmail(),
                dto.getTransactionPin())) {

            throw new RuntimeException("Invalid Transaction PIN");
        }

        if (account.getBalance()
                .compareTo(dto.getAmount()) < 0) {

            throw new RuntimeException(
                    "Insufficient balance"
            );

        }

        account.setBalance(

                account.getBalance()
                        .subtract(dto.getAmount())

        );

        accountRepository.save(account);

        Transaction transaction =
                new Transaction();

        transaction.setAccount(account);

        transaction.setTransactionType(

                TransactionType.WITHDRAW

        );

        transaction.setAmount(

                dto.getAmount()

        );

        transaction.setTransactionDate(

                LocalDate.now()

        );

        transaction.setTransactionTime(

                LocalDateTime.now()

        );

        transaction.setFromAccount(

                account.getAccountNumber()

        );

        transaction.setDescription(

                dto.getDescription() == null

                        ? "ATM Withdrawal"

                        : dto.getDescription()

        );

        transactionRepository.save(transaction);

        sendWithdrawEmail(

                account,

                transaction

        );

        return mapToResponse(transaction);

    }





    // ================= FUND TRANSFER =================

    @Override
    public TransactionResponseDTO transfer(

            TransactionRequestDTO dto

    ) {

        Account sender =
                getLoggedInAccount();
        validateAccountStatus(sender);


        if (!customerService.verifyTransactionPin(
                sender.getCustomer().getEmail(),
                dto.getTransactionPin())) {

            throw new RuntimeException("Invalid Transaction PIN");
        }

        Account receiver =

                accountRepository

                .findByAccountNumber(
                        dto.getToAccount()
                )

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Receiver account not found"
                        )

                );

        if (sender.getAccountNumber()
                .equals(receiver.getAccountNumber())) {

            throw new RuntimeException(
                    "Cannot transfer to same account"
            );

        }

        if (sender.getBalance()
                .compareTo(dto.getAmount()) < 0) {

            throw new RuntimeException(
                    "Insufficient balance"
            );

        }

        sender.setBalance(

                sender.getBalance()
                        .subtract(dto.getAmount())

        );

        receiver.setBalance(

                receiver.getBalance()
                        .add(dto.getAmount())

        );

        accountRepository.save(sender);

        accountRepository.save(receiver);

        Transaction transaction =

                new Transaction();

        transaction.setAccount(sender);

        transaction.setTransactionType(

                TransactionType.TRANSFER

        );

        transaction.setAmount(

                dto.getAmount()

        );

        transaction.setTransactionDate(

                LocalDate.now()

        );

        transaction.setTransactionTime(

                LocalDateTime.now()

        );

        transaction.setFromAccount(

                sender.getAccountNumber()

        );

        transaction.setToAccount(

                receiver.getAccountNumber()

        );

        transaction.setDescription(

                dto.getDescription() == null

                        ? "Fund Transfer"

                        : dto.getDescription()

        );

        transactionRepository.save(transaction);

        sendTransferEmails(

                sender,

                receiver,

                transaction

        );

        return mapToResponse(transaction);

    }
 // ================= RECEIVER LIST =================

    @Override
    public List<ReceiverResponseDTO> getReceivers() {

        Account loggedInAccount = getLoggedInAccount();

        return accountRepository.findAll()

                .stream()

                .filter(account ->

                        !account.getAccountNumber()
                                .equals(loggedInAccount.getAccountNumber())

                )

                .map(account -> {

                    ReceiverResponseDTO dto =

                            new ReceiverResponseDTO();

                    dto.setAccountNumber(

                            account.getAccountNumber()

                    );

                    dto.setAccountHolderName(

                            account.getCustomer()
                                    .getFullName()

                    );

                    return dto;

                })

                .collect(Collectors.toList());

    }
 // ================= DEPOSIT EMAIL =================


    private void sendDepositEmail(

            Account account,

            Transaction transaction

    ) {


        try {


            EmailRequestDTO email =

                    new EmailRequestDTO();



            email.setTo(

                    account.getCustomer()
                    .getEmail()

            );



            email.setSubject(

                    "Deposit Successful - SecureBank"

            );



            email.setMessage(


                    "Hello "

                    + account.getCustomer()
                    .getFullName()


                    + ",\n\n"

                    + "Your deposit transaction was successful.\n\n"

                    + "Transaction Details:\n\n"

                    + "Transaction ID: "

                    + transaction.getTransactionId()


                    + "\nTransaction Type: DEPOSIT"


                    + "\nAmount: ₹"

                    + transaction.getAmount()


                    + "\nDate & Time: "

                    + transaction.getTransactionTime()


                    + "\nAvailable Balance: ₹"

                    + account.getBalance()


                    + "\nStatus: SUCCESS"


                    + "\n\nRegards,\nSecureBank Team"

            );



            emailService.sendEmail(email);



            logger.info(
                    "Deposit email sent successfully"
            );


        } catch(Exception e) {


            logger.error(
                    "Deposit email failed",
                    e
            );

        }


    }








    // ================= WITHDRAW EMAIL =================


    private void sendWithdrawEmail(

            Account account,

            Transaction transaction

    ) {


        try {


            EmailRequestDTO email =

                    new EmailRequestDTO();



            email.setTo(

                    account.getCustomer()
                    .getEmail()

            );



            email.setSubject(

                    "Withdrawal Successful - SecureBank"

            );



            email.setMessage(


                    "Hello "

                    + account.getCustomer()
                    .getFullName()


                    + ",\n\n"

                    + "Your withdrawal transaction was successful.\n\n"

                    + "Transaction Details:\n\n"

                    + "Transaction ID: "

                    + transaction.getTransactionId()


                    + "\nTransaction Type: WITHDRAW"


                    + "\nAmount: ₹"

                    + transaction.getAmount()


                    + "\nDate & Time: "

                    + transaction.getTransactionTime()


                    + "\nRemaining Balance: ₹"

                    + account.getBalance()


                    + "\nStatus: SUCCESS"


                    + "\n\nRegards,\nSecureBank Team"

            );



            emailService.sendEmail(email);



            logger.info(
                    "Withdraw email sent successfully"
            );


        } catch(Exception e) {


            logger.error(
                    "Withdraw email failed",
                    e
            );

        }


    }








    // ================= TRANSFER EMAILS =================


    private void sendTransferEmails(

            Account sender,

            Account receiver,

            Transaction transaction

    ) {


        try {


            // SENDER EMAIL


            EmailRequestDTO senderEmail =

                    new EmailRequestDTO();



            senderEmail.setTo(

                    sender.getCustomer()
                    .getEmail()

            );



            senderEmail.setSubject(

                    "Fund Transfer Successful - SecureBank"

            );



            senderEmail.setMessage(


                    "Hello "

                    + sender.getCustomer()
                    .getFullName()


                    + ",\n\n"

                    + "Your fund transfer was successful.\n\n"

                    + "Transaction ID: "

                    + transaction.getTransactionId()


                    + "\nAmount: ₹"

                    + transaction.getAmount()


                    + "\nReceiver Account: "

                    + receiver.getAccountNumber()


                    + "\nRemaining Balance: ₹"

                    + sender.getBalance()


                    + "\nStatus: SUCCESS"


                    + "\n\nRegards,\nSecureBank Team"

            );



            emailService.sendEmail(senderEmail);






            // RECEIVER EMAIL


            EmailRequestDTO receiverEmail =

                    new EmailRequestDTO();



            receiverEmail.setTo(

                    receiver.getCustomer()
                    .getEmail()

            );



            receiverEmail.setSubject(

                    "Money Received - SecureBank"

            );



            receiverEmail.setMessage(


                    "Hello "

                    + receiver.getCustomer()
                    .getFullName()


                    + ",\n\n"

                    + "Money has been received in your account.\n\n"

                    + "Transaction Details:\n\n"

                    + "Transaction ID: "

                    + transaction.getTransactionId()


                    + "\nReceived Amount: ₹"

                    + transaction.getAmount()


                    + "\nSender Account: "

                    + sender.getAccountNumber()


                    + "\nUpdated Balance: ₹"

                    + receiver.getBalance()


                    + "\nStatus: SUCCESS"


                    + "\n\nRegards,\nSecureBank Team"

            );



            emailService.sendEmail(receiverEmail);



            logger.info(
                    "Transfer emails sent successfully"
            );


        } catch(Exception e) {


            logger.error(
                    "Transfer email failed",
                    e
            );

        }


    }
 // ================= GET TRANSACTION BY ID =================


    @Override
    public TransactionResponseDTO getTransactionById(

            Long transactionId

    ) {


        Transaction transaction =

                transactionRepository
                .findById(transactionId)

                .orElseThrow(() ->

                        new ResourceNotFoundException(
                                "Transaction not found"
                        )

                );



        return mapToResponse(transaction);


    }








    // ================= CUSTOMER HISTORY =================


    @Override
    public List<TransactionResponseDTO> getMyTransactionHistory() {



        Account account =

                getLoggedInAccount();




        List<Transaction> transactions =

                transactionRepository

                .findByFromAccountOrToAccountOrderByTransactionTimeDesc(

                        account.getAccountNumber(),

                        account.getAccountNumber()

                );




        return transactions

                .stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());



    }









 // ================= SEARCH TRANSACTIONS =================

    @Override
    public List<TransactionResponseDTO> searchTransactions(

            TransactionSearchDTO dto

    ) {


        LocalDateTime startDate = null;

        LocalDateTime endDate = null;




        if(dto.getTransactionDate() != null) {


            startDate =

                    dto.getTransactionDate()
                    .atStartOfDay();



            endDate =

                    dto.getTransactionDate()
                    .atTime(23,59,59);


        }






        List<Transaction> transactions;






        // ==========================================
        // SEARCH BY ACCOUNT NUMBER
        // ==========================================


        if(dto.getAccountNumber() != null
                && !dto.getAccountNumber().isEmpty()) {



            Account account =

                    accountRepository
                    .findByAccountNumber(
                            dto.getAccountNumber()
                    )

                    .orElseThrow(() ->

                            new ResourceNotFoundException(
                                    "Account not found"
                            )

                    );





            transactions =

                    transactionRepository.searchTransactions(


                            account.getAccountNumber(),


                            dto.getTransactionType(),


                            startDate,


                            endDate,


                            dto.getMinAmount(),


                            dto.getMaxAmount()


                    );



        }






        // ==========================================
        // ADMIN GLOBAL SEARCH
        // ==========================================


        else {



            transactions =

                    transactionRepository.searchAllTransactions(


                            dto.getTransactionType(),


                            startDate,


                            endDate,


                            dto.getMinAmount(),


                            dto.getMaxAmount()


                    );



        }








        return transactions

                .stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());


    }









    // ================= ADMIN VIEW =================


    @Override
    public List<TransactionResponseDTO> getAllTransactions() {



        return transactionRepository

                .findAllByOrderByTransactionTimeDesc()

                .stream()

                .map(this::mapToResponse)

                .collect(Collectors.toList());


    }
 // ================= ENTITY TO DTO =================

    private TransactionResponseDTO mapToResponse(

            Transaction transaction

    ) {

        TransactionResponseDTO response =
                new TransactionResponseDTO();

        response.setTransactionId(
                transaction.getTransactionId()
        );

        response.setTransactionType(
                transaction.getTransactionType()
        );

        response.setAmount(
                transaction.getAmount()
        );

        response.setTransactionDate(
                transaction.getTransactionDate()
        );

        response.setTransactionTime(
                transaction.getTransactionTime()
        );

        response.setFromAccount(
                transaction.getFromAccount()
        );

        response.setToAccount(
                transaction.getToAccount()
        );

        response.setDescription(
                transaction.getDescription()
        );

        response.setStatus(
                "SUCCESS"
        );

        if (transaction.getAccount() != null) {

            response.setAvailableBalance(
                    transaction.getAccount().getBalance()
            );

        }

        if (transaction.getTransactionType() == TransactionType.TRANSFER
                && transaction.getToAccount() != null) {

            accountRepository.findByAccountNumber(transaction.getToAccount())
                    .ifPresent(account -> response.setReceiverName(
                            account.getCustomer().getFullName()
                    ));

        }

        return response;

    }
}