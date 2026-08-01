package com.securebank.transaction.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class DepositRequestDTO {

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "0.01",
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;

    @NotBlank(message = "Transaction PIN is required")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "Transaction PIN must be exactly 6 digits"
    )
    private String transactionPin;

    @Size(
            max = 200,
            message = "Description cannot exceed 200 characters"
    )
    private String description;

    public DepositRequestDTO() {
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionPin() {
        return transactionPin;
    }

    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin == null
                ? null
                : transactionPin.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null
                ? null
                : description.trim();
    }
}