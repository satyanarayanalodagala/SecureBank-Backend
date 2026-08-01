package com.securebank.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.securebank.enums.TransactionType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionResponseDTO {

    private Long transactionId;

    private TransactionType transactionType;

    private BigDecimal amount;

    private LocalDate transactionDate;

    private LocalDateTime transactionTime;

    // Used only for TRANSFER
    private String fromAccount;

    // Used only for TRANSFER
    private String toAccount;

    // Used only for DEPOSIT
    private String creditedAccount;

    // Used only for WITHDRAW
    private String debitedAccount;

    private String description;

    private String status;

    // New field - Available balance after transaction
    private BigDecimal availableBalance;

    // New field - Receiver name for transfer success page
    private String receiverName;

    public TransactionResponseDTO() {

    }

    public Long getTransactionId() {

        return transactionId;

    }

    public void setTransactionId(Long transactionId) {

        this.transactionId = transactionId;

    }

    public TransactionType getTransactionType() {

        return transactionType;

    }

    public void setTransactionType(TransactionType transactionType) {

        this.transactionType = transactionType;

    }

    public BigDecimal getAmount() {

        return amount;

    }

    public void setAmount(BigDecimal amount) {

        this.amount = amount;

    }

    public LocalDate getTransactionDate() {

        return transactionDate;

    }

    public void setTransactionDate(LocalDate transactionDate) {

        this.transactionDate = transactionDate;

    }

    public LocalDateTime getTransactionTime() {

        return transactionTime;

    }

    public void setTransactionTime(LocalDateTime transactionTime) {

        this.transactionTime = transactionTime;

    }

    public String getFromAccount() {

        return fromAccount;

    }

    public void setFromAccount(String fromAccount) {

        this.fromAccount = fromAccount;

    }

    public String getToAccount() {

        return toAccount;

    }

    public void setToAccount(String toAccount) {

        this.toAccount = toAccount;

    }

    public String getCreditedAccount() {

        return creditedAccount;

    }

    public void setCreditedAccount(String creditedAccount) {

        this.creditedAccount = creditedAccount;

    }

    public String getDebitedAccount() {

        return debitedAccount;

    }

    public void setDebitedAccount(String debitedAccount) {

        this.debitedAccount = debitedAccount;

    }

    public String getDescription() {

        return description;

    }

    public void setDescription(String description) {

        this.description = description;

    }

    public String getStatus() {

        return status;

    }

    public void setStatus(String status) {

        this.status = status;

    }

    public BigDecimal getAvailableBalance() {

        return availableBalance;

    }

    public void setAvailableBalance(BigDecimal availableBalance) {

        this.availableBalance = availableBalance;

    }

    public String getReceiverName() {

        return receiverName;

    }

    public void setReceiverName(String receiverName) {

        this.receiverName = receiverName;

    }

}