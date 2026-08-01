package com.securebank.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DepositRequestDTO {

    @NotBlank(message = "Account number is required")
    @Pattern(
            regexp = "^\\d{10,20}$",
            message = "Account number must contain 10 to 20 digits"
    )
    private String accountNumber;

    @NotNull(message = "Deposit amount is required")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Deposit amount must be greater than zero"
    )
    private BigDecimal amount;

    public DepositRequestDTO() {
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null
                ? null
                : accountNumber.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}