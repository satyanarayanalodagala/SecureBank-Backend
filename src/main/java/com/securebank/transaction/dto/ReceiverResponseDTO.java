package com.securebank.transaction.dto;

public class ReceiverResponseDTO {

    private String accountNumber;

    private String accountHolderName;

    public ReceiverResponseDTO() {

    }

    public String getAccountNumber() {

        return accountNumber;

    }

    public void setAccountNumber(String accountNumber) {

        this.accountNumber = accountNumber;

    }

    public String getAccountHolderName() {

        return accountHolderName;

    }

    public void setAccountHolderName(String accountHolderName) {

        this.accountHolderName = accountHolderName;

    }

}