package com.securebank.entity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.securebank.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "customers")
public class Customer {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;





    @NotBlank(message = "Full name is required")
    @Column(name = "full_name",
            nullable = false)
    private String fullName;





    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email",
            unique = true,
            nullable = false)
    private String email;





    @NotBlank(message = "Password is required")
    @Size(min = 6,
          message = "Password must be at least 6 characters")
    @Column(nullable = false)
    private String password;





    // ================= TRANSACTION SECURITY =================


    @Column(name = "transaction_pin")
    private String transactionPin;





    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.CUSTOMER;





    @NotBlank(message = "Mobile number is required")
    @Size(min = 10,
          max = 10,
          message = "Mobile number must be 10 digits")
    @Column(name = "mobile_number",
            nullable = false)
    private String mobileNumber;





    @NotBlank(message = "Address is required")
    @Column(nullable = false)
    private String address;





    // ================= PROFILE DETAILS =================


    @Column(name = "age")
    private Integer age;




    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;




    @Column(name = "gender")
    private String gender;




    @Column(name = "profile_image")
    private String profileImage;






    // ================= CUSTOMER -> ACCOUNTS =================


    @JsonIgnore
    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Account> accounts =
            new ArrayList<>();






    public Customer() {

    }






    public Long getCustomerId() {
        return customerId;
    }



    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }







    public String getFullName() {
        return fullName;
    }



    public void setFullName(String fullName) {
        this.fullName = fullName;
    }







    public String getEmail() {
        return email;
    }



    public void setEmail(String email) {
        this.email = email;
    }







    public String getPassword() {
        return password;
    }



    public void setPassword(String password) {
        this.password = password;
    }







    // ================= TRANSACTION PIN GETTER SETTER =================


    public String getTransactionPin() {
        return transactionPin;
    }



    public void setTransactionPin(String transactionPin) {
        this.transactionPin = transactionPin;
    }







    public Role getRole() {
        return role;
    }



    public void setRole(Role role) {
        this.role = role;
    }







    public String getMobileNumber() {
        return mobileNumber;
    }



    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }







    public String getAddress() {
        return address;
    }



    public void setAddress(String address) {
        this.address = address;
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
        this.gender = gender;
    }







    public String getProfileImage() {
        return profileImage;
    }



    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }







    public List<Account> getAccounts() {
        return accounts;
    }



    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }



}