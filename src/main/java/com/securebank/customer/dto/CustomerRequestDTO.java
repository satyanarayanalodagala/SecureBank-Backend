package com.securebank.customer.dto;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



@JsonPropertyOrder({
    "fullName",
    "email",
    "password",
    "transactionPin",
    "mobileNumber",
    "address",
    "age",
    "dateOfBirth",
    "gender"
})
public class CustomerRequestDTO {



    @NotBlank(message = "Full name is required")
    @Size(
        min = 3,
        max = 50,
        message = "Full name must be between 3 and 50 characters"
    )
    private String fullName;





    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email address")
    private String email;





    @NotBlank(message = "Password is required")
    @Size(
        min = 8,
        max = 20,
        message = "Password must be between 8 and 20 characters"
    )
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String password;





    // ================= TRANSACTION SECURITY =================


    @NotBlank(message = "Transaction PIN is required")
    @Pattern(
        regexp = "^\\d{6}$",
        message = "Transaction PIN must be exactly 6 digits"
    )
    private String transactionPin;





    @NotBlank(message = "Mobile number is required")
    @Pattern(
        regexp = "^[6-9]\\d{9}$",
        message = "Mobile number must be 10 digits and start with 6, 7, 8, or 9"
    )
    private String mobileNumber;





    @NotBlank(message = "Address is required")
    @Size(
        min = 5,
        max = 200,
        message = "Address must be between 5 and 200 characters"
    )
    private String address;





    // ================= PROFILE DETAILS =================


    private Integer age;





    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;





    @NotBlank(message = "Gender is required")
    private String gender;







    public CustomerRequestDTO() {

    }







    public String getFullName() {
        return fullName;
    }



    public void setFullName(String fullName) {

        this.fullName =
                fullName == null ? null : fullName.trim();

    }







    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {

        this.email =
                email == null ? null : email.trim();

    }







    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {

        this.password =
                password == null ? null : password.trim();

    }







    // ================= TRANSACTION PIN =================


    public String getTransactionPin() {
        return transactionPin;
    }



    public void setTransactionPin(String transactionPin) {

        this.transactionPin =
                transactionPin == null ? null : transactionPin.trim();

    }







    public String getMobileNumber() {
        return mobileNumber;
    }



    public void setMobileNumber(String mobileNumber) {

        this.mobileNumber =
                mobileNumber == null ? null : mobileNumber.trim();

    }







    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {

        this.address =
                address == null ? null : address.trim();

    }







    public Integer getAge() {
        return age;
    }



    public void setAge(Integer age) {

        this.age = age;

    }







    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }



    public void setDateOfBirth(LocalDate dateOfBirth) {

        this.dateOfBirth = dateOfBirth;

    }







    public String getGender() {
        return gender;
    }



    public void setGender(String gender) {

        this.gender =
                gender == null ? null : gender.trim();

    }



}