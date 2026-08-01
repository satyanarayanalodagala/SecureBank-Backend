package com.securebank.customer.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



public class ForgotTransactionPinRequestDTO {



    // ================= EMAIL =================


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;





    public ForgotTransactionPinRequestDTO() {

    }






    public String getEmail() {

        return email;

    }






    public void setEmail(String email) {

        this.email =

                email == null
                ? null
                : email.trim();

    }



}