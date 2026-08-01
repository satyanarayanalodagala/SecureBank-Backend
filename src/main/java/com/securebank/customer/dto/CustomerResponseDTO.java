package com.securebank.customer.dto;


import java.time.LocalDate;


public class CustomerResponseDTO {


    private Long customerId;


    private String fullName;


    private String email;


    private String mobileNumber;


    private String address;


    private String role;


    // ================= PROFILE DETAILS =================


    private Integer age;


    private LocalDate dateOfBirth;


    private String gender;


    private String profileImage;





    public CustomerResponseDTO() {

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







    public String getRole() {
        return role;
    }



    public void setRole(String role) {
        this.role = role;
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







    @Override
    public String toString() {

        return "CustomerResponseDTO{" +

                "customerId=" + customerId +

                ", fullName='" + fullName + '\'' +

                ", email='" + email + '\'' +

                ", mobileNumber='" + mobileNumber + '\'' +

                ", address='" + address + '\'' +

                ", role='" + role + '\'' +

                ", age=" + age +

                ", dateOfBirth=" + dateOfBirth +

                ", gender='" + gender + '\'' +

                ", profileImage='" + profileImage + '\'' +

                '}';

    }


}