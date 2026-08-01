package com.securebank.entity;


import jakarta.persistence.*;
import java.time.LocalDateTime;



@Entity
@Table(name = "transaction_pin_reset_tokens")
public class TransactionPinResetToken {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(nullable = false, unique = true)
    private String token;




    // Link reset token with customer

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "customer_id",
            nullable = false
    )
    private Customer customer;




    @Column(nullable = false)
    private LocalDateTime expiryDate;






    public TransactionPinResetToken() {

    }







    public TransactionPinResetToken(

            String token,

            Customer customer,

            LocalDateTime expiryDate

    ) {

        this.token = token;

        this.customer = customer;

        this.expiryDate = expiryDate;

    }







    public Long getId() {

        return id;

    }



    public void setId(Long id) {

        this.id = id;

    }







    public String getToken() {

        return token;

    }



    public void setToken(String token) {

        this.token = token;

    }







    public Customer getCustomer() {

        return customer;

    }



    public void setCustomer(Customer customer) {

        this.customer = customer;

    }







    public LocalDateTime getExpiryDate() {

        return expiryDate;

    }



    public void setExpiryDate(LocalDateTime expiryDate) {

        this.expiryDate = expiryDate;

    }



}