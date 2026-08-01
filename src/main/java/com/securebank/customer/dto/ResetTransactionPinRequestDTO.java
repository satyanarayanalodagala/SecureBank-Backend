package com.securebank.customer.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;



public class ResetTransactionPinRequestDTO {



    // ================= CUSTOMER EMAIL =================


    @NotBlank(message = "Email is required")
    private String email;





    // ================= NEW TRANSACTION PIN =================


    @NotBlank(message = "New transaction PIN is required")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "Transaction PIN must be exactly 6 digits"
    )
    private String newPin;





    // ================= CONFIRM PIN =================


    @NotBlank(message = "Confirm transaction PIN is required")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "Transaction PIN must be exactly 6 digits"
    )
    private String confirmPin;







    public ResetTransactionPinRequestDTO(){

    }







    public String getEmail() {

        return email;

    }



    public void setEmail(String email) {

        this.email =
                email == null ? null : email.trim();

    }







    public String getNewPin() {

        return newPin;

    }



    public void setNewPin(String newPin) {

        this.newPin =
                newPin == null ? null : newPin.trim();

    }







    public String getConfirmPin() {

        return confirmPin;

    }



    public void setConfirmPin(String confirmPin) {

        this.confirmPin =
                confirmPin == null ? null : confirmPin.trim();

    }



}