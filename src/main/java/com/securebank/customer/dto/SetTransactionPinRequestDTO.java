package com.securebank.customer.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



public class SetTransactionPinRequestDTO {



    // ================= TRANSACTION PIN =================


    @NotBlank(message = "Transaction PIN is required")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "Transaction PIN must be exactly 6 digits"
    )
    private String transactionPin;





    public SetTransactionPinRequestDTO() {

    }






    public String getTransactionPin() {

        return transactionPin;

    }






    public void setTransactionPin(String transactionPin) {

        this.transactionPin =

                transactionPin == null
                ? null
                : transactionPin.trim();

    }


}