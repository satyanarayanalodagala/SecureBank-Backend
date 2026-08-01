package com.securebank.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class TransferRequestDTO {

    @NotBlank(message = "From account number is required")
    @Pattern(
            regexp = "^\\d{10,20}$",
            message = "From account number must contain 10 to 20 digits"
    )
    private String fromAccount;

    @NotBlank(message = "To account number is required")
    @Pattern(
            regexp = "^\\d{10,20}$",
            message = "To account number must contain 10 to 20 digits"
    )
    private String toAccount;

    @NotNull(message = "Amount is required")
    @DecimalMin(
            value = "0.01",
            inclusive = true,
            message = "Amount must be greater than zero"
    )
    private BigDecimal amount;

    @Size(
            max = 200,
            message = "Description cannot exceed 200 characters"
    )
    private String description;

    public TransferRequestDTO() {
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null
                ? null
                : fromAccount.trim();
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount == null
                ? null
                : toAccount.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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