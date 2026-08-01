package com.securebank.statement.dto;

import java.time.LocalDate;

public class StatementRequestDTO {

    private String accountNumber;
    private LocalDate fromDate;
    private LocalDate toDate;

    public StatementRequestDTO() {
    }

    public StatementRequestDTO(String accountNumber, LocalDate fromDate, LocalDate toDate) {
        this.accountNumber = accountNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}