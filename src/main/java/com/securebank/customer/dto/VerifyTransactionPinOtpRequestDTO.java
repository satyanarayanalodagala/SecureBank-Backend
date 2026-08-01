package com.securebank.customer.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;



public class VerifyTransactionPinOtpRequestDTO {


    // ================= EMAIL =================


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;



    // ================= OTP =================


    @NotBlank(message = "OTP is required")
    private String otp;




    public VerifyTransactionPinOtpRequestDTO(){

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





    public String getOtp() {

        return otp;

    }





    public void setOtp(String otp) {

        this.otp =
                otp == null
                ? null
                : otp.trim();

    }

}