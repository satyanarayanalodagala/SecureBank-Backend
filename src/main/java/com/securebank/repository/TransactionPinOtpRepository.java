package com.securebank.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securebank.entity.TransactionPinOtp;



@Repository
public interface TransactionPinOtpRepository
        extends JpaRepository<TransactionPinOtp, Long> {


    Optional<TransactionPinOtp> findByOtp(String otp);



    Optional<TransactionPinOtp> findByOtpAndCustomerCustomerId(
            String otp,
            Long customerId
    );



    Optional<TransactionPinOtp> findTopByCustomerCustomerIdOrderByIdDesc(
            Long customerId
    );



    void deleteByCustomerCustomerId(Long customerId);


}