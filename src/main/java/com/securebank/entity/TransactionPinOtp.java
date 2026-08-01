package com.securebank.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;



@Entity
@Table(name = "transaction_pin_otps")
public class TransactionPinOtp {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(nullable = false)
    private String otp;





    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;





    @Column(nullable = false)
    private LocalDateTime expiryTime;





    @Column(nullable = false)
    private boolean verified = false;






    public TransactionPinOtp(){

    }






    public TransactionPinOtp(

            String otp,

            Customer customer,

            LocalDateTime expiryTime

    ){

        this.otp = otp;

        this.customer = customer;

        this.expiryTime = expiryTime;

    }







    public Long getId() {

        return id;

    }



    public void setId(Long id) {

        this.id = id;

    }







    public String getOtp() {

        return otp;

    }



    public void setOtp(String otp) {

        this.otp = otp;

    }







    public Customer getCustomer() {

        return customer;

    }



    public void setCustomer(Customer customer) {

        this.customer = customer;

    }







    public LocalDateTime getExpiryTime() {

        return expiryTime;

    }



    public void setExpiryTime(LocalDateTime expiryTime) {

        this.expiryTime = expiryTime;

    }







    public boolean isVerified() {

        return verified;

    }



    public void setVerified(boolean verified) {

        this.verified = verified;

    }


}