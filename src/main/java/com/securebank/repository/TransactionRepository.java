package com.securebank.repository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.securebank.entity.Transaction;
import com.securebank.enums.TransactionType;



@Repository
public interface TransactionRepository 
        extends JpaRepository<Transaction, Long> {



    // =====================================================
    // CUSTOMER TRANSACTION HISTORY
    // Sender OR Receiver transactions
    // =====================================================


    List<Transaction> 
    findByFromAccountOrToAccountOrderByTransactionTimeDesc(

            String fromAccount,

            String toAccount

    );






    // =====================================================
    // SEARCH BY TRANSACTION TYPE
    // =====================================================


    List<Transaction> findByTransactionType(

            TransactionType transactionType

    );







    // =====================================================
    // SEARCH BY DATE RANGE
    // =====================================================


    List<Transaction> findByTransactionTimeBetween(

            LocalDateTime startDate,

            LocalDateTime endDate

    );







    // =====================================================
    // ACCOUNT TRANSACTIONS
    // =====================================================


    List<Transaction> 
    findByAccountAccountNumberOrderByTransactionTimeDesc(

            String accountNumber

    );







    // =====================================================
    // ACCOUNT STATEMENT
    // =====================================================


    List<Transaction>
    findByAccountAccountNumberAndTransactionDateBetweenOrderByTransactionTimeDesc(

            String accountNumber,

            LocalDate fromDate,

            LocalDate toDate

    );








    // =====================================================
    // ADMIN VIEW ALL TRANSACTIONS
    // =====================================================


    List<Transaction> findAllByOrderByTransactionTimeDesc();








    // =====================================================
    // CUSTOMER SEARCH TRANSACTIONS
    //
    // Used when account number is provided
    // =====================================================


    @Query("""
           
           SELECT t FROM Transaction t

           WHERE

           (

                :accountNumber IS NULL

                OR t.fromAccount = :accountNumber

                OR t.toAccount = :accountNumber

           )


           AND

           (

                :type IS NULL

                OR t.transactionType = :type

           )


           AND

           (

                :startDate IS NULL

                OR t.transactionTime >= :startDate

           )


           AND

           (

                :endDate IS NULL

                OR t.transactionTime <= :endDate

           )


           AND

           (

                :minAmount IS NULL

                OR t.amount >= :minAmount

           )


           AND

           (

                :maxAmount IS NULL

                OR t.amount <= :maxAmount

           )


           ORDER BY t.transactionTime DESC

           """)


    List<Transaction> searchTransactions(


            @Param("accountNumber")

            String accountNumber,


            @Param("type")

            TransactionType type,


            @Param("startDate")

            LocalDateTime startDate,


            @Param("endDate")

            LocalDateTime endDate,


            @Param("minAmount")

            java.math.BigDecimal minAmount,


            @Param("maxAmount")

            java.math.BigDecimal maxAmount


    );









    // =====================================================
    // ADMIN GLOBAL SEARCH TRANSACTIONS
    //
    // Admin can search all transactions
    // Account number is optional
    // =====================================================


    @Query("""
           
           SELECT t FROM Transaction t


           WHERE


           (

                :type IS NULL

                OR t.transactionType = :type

           )


           AND


           (

                :startDate IS NULL

                OR t.transactionTime >= :startDate

           )


           AND


           (

                :endDate IS NULL

                OR t.transactionTime <= :endDate

           )


           AND


           (

                :minAmount IS NULL

                OR t.amount >= :minAmount

           )


           AND


           (

                :maxAmount IS NULL

                OR t.amount <= :maxAmount

           )


           ORDER BY t.transactionTime DESC


           """)


    List<Transaction> searchAllTransactions(



            @Param("type")

            TransactionType type,


            @Param("startDate")

            LocalDateTime startDate,


            @Param("endDate")

            LocalDateTime endDate,


            @Param("minAmount")

            java.math.BigDecimal minAmount,


            @Param("maxAmount")

            java.math.BigDecimal maxAmount


    );



}