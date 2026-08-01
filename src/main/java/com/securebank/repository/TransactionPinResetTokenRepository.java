package com.securebank.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securebank.entity.TransactionPinResetToken;



@Repository
public interface TransactionPinResetTokenRepository
        extends JpaRepository<TransactionPinResetToken, Long> {


    Optional<TransactionPinResetToken> findByToken(String token);



    void deleteByCustomerCustomerId(Long customerId);


}