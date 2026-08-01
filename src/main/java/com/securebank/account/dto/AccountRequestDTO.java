package com.securebank.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AccountRequestDTO {

    @NotBlank(message = "Account type is required")
    @Size(
            min = 3,
            max = 20,
            message = "Account type must be between 3 and 20 characters"
    )
    @Pattern(
            regexp = "^(SAVINGS|CURRENT)$",
            message = "Account type must be either SAVINGS or CURRENT"
    )
    private String accountType;

    @NotNull(message = "Initial balance is required")
    @DecimalMin(
            value = "0.00",
            inclusive = true,
            message = "Initial balance cannot be negative"
    )
    private BigDecimal initialBalance;

    public AccountRequestDTO() {
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null
                ? null
                : accountType.trim().toUpperCase();
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}