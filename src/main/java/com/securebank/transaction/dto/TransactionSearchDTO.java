package com.securebank.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.securebank.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;

public class TransactionSearchDTO {

    @Pattern(
            regexp = "^ACC\\d{6}$",
            message = "Invalid account number format"
    )
    private String accountNumber;

    private TransactionType transactionType;

    // ================= DATE FILTER =================

    private LocalDate transactionDate;

    // ================= ADMIN MONITORING FILTERS =================

    @DecimalMin(
            value = "0.01",
            message = "Minimum amount must be greater than zero"
    )
    private BigDecimal minAmount;

    @DecimalMin(
            value = "0.01",
            message = "Maximum amount must be greater than zero"
    )
    private BigDecimal maxAmount;

    public TransactionSearchDTO() {

    }

    public String getAccountNumber() {

        return accountNumber;

    }

    public void setAccountNumber(String accountNumber) {

        this.accountNumber =
                accountNumber == null
                        ? null
                        : accountNumber.trim().toUpperCase();

    }

    public TransactionType getTransactionType() {

        return transactionType;

    }

    public void setTransactionType(TransactionType transactionType) {

        this.transactionType = transactionType;

    }

    public LocalDate getTransactionDate() {

        return transactionDate;

    }

    public void setTransactionDate(LocalDate transactionDate) {

        this.transactionDate = transactionDate;

    }

    public BigDecimal getMinAmount() {

        return minAmount;

    }

    public void setMinAmount(BigDecimal minAmount) {

        this.minAmount = minAmount;

    }

    public BigDecimal getMaxAmount() {

        return maxAmount;

    }

    public void setMaxAmount(BigDecimal maxAmount) {

        this.maxAmount = maxAmount;

    }

}