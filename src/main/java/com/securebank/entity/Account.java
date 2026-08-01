package com.securebank.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.securebank.enums.AccountStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "accounts")
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;



    @Column(name = "account_number",
            unique = true,
            nullable = false)
    private String accountNumber;



    @Column(name = "account_type",
            nullable = false)
    private String accountType;



    @Column(nullable = false,
            precision = 19,
            scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;



    // ================= ACCOUNT STATUS =================

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status = AccountStatus.ACTIVE;



    // ================= CUSTOMER RELATION =================

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",
                nullable = false)
    @JsonIgnore
    private Customer customer;



    // ================= TRANSACTIONS =================

    @JsonIgnore
    @OneToMany(mappedBy = "account",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    private List<Transaction> transactions =
            new ArrayList<>();



    public Account() {
    }



    public Long getAccountId() {
        return accountId;
    }


    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }



    public String getAccountNumber() {
        return accountNumber;
    }


    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }



    public String getAccountType() {
        return accountType;
    }


    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }



    public BigDecimal getBalance() {
        return balance;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }



    public AccountStatus getStatus() {
        return status;
    }


    public void setStatus(AccountStatus status) {
        this.status = status;
    }



    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    public List<Transaction> getTransactions() {
        return transactions;
    }


    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}