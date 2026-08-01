package com.securebank.repository;


import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.securebank.entity.Account;



@Repository
public interface AccountRepository 
        extends JpaRepository<Account, Long> {



    // ================= FIND ACCOUNT BY ACCOUNT NUMBER =================


    Optional<Account> findByAccountNumber(

            String accountNumber

    );







    // ================= FIND ACCOUNT USING CUSTOMER EMAIL =================


    Optional<Account> findByCustomer_Email(

            String email

    );







    // ================= FIND ALL ACCOUNTS OF CUSTOMER =================


    List<Account> findAllByCustomer_CustomerId(

            Long customerId

    );







    // ================= CHECK CUSTOMER ACCOUNT EXISTS =================


    boolean existsByCustomer_CustomerId(

            Long customerId

    );







    // ================= TOTAL BANK BALANCE =================


    @Query(
        "SELECT COALESCE(SUM(a.balance),0) FROM Account a"
    )
    BigDecimal getTotalBankBalance();



}