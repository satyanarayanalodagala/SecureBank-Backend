package com.securebank.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class ForgotPasswordRequestDTO {


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;



    @NotBlank(message = "New password is required")
    @Size(
            min = 8,
            max = 20,
            message = "Password must be between 8 and 20 characters"
    )
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String newPassword;



    public ForgotPasswordRequestDTO() {
    }



    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }



    public String getNewPassword() {
        return newPassword;
    }



    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}