package com.securebank.customer.dto;


import java.time.LocalDate;


public class CustomerProfileDTO {


    private String fullName;


    private String email;


    private String mobileNumber;


    private String address;


    // ================= PROFILE DETAILS =================


    private Integer age;


    private LocalDate dateOfBirth;


    private String gender;


    private String profileImage;





    public CustomerProfileDTO() {

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


}