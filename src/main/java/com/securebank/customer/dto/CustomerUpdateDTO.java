package com.securebank.customer.dto;


import java.time.LocalDate;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class CustomerUpdateDTO {


    @Size(
            min = 3,
            max = 50,
            message = "Full name must be between 3 and 50 characters"
    )
    private String fullName;





    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Mobile number must be 10 digits and start with 6, 7, 8, or 9"
    )
    private String mobileNumber;





    @Size(
            min = 5,
            max = 200,
            message = "Address must be between 5 and 200 characters"
    )
    private String address;





    // ================= PROFILE DETAILS =================


    private Integer age;


    private LocalDate dateOfBirth;


    private String gender;





    public CustomerUpdateDTO() {

    }







    public String getFullName() {
        return fullName;
    }



    public void setFullName(String fullName) {

        this.fullName =
                fullName == null ? null : fullName.trim();

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