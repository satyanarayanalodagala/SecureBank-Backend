package com.securebank.account.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {

    private Long accountId;

    private String accountNumber;

    private String accountType;

    private BigDecimal balance;

    // Customer Details
    private Long customerId;

    private String customerName;

    // Account Status (ACTIVE / BLOCKED)
    private String status;

    public AccountResponseDTO() {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AccountResponseDTO{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}