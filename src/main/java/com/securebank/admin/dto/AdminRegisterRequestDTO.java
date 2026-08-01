package com.securebank.admin.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public class AdminRegisterRequestDTO {





    @NotBlank(
        message = "Full name is required"
    )
    private String fullName;







    @NotBlank(
        message = "Email is required"
    )

    @Email(
        message = "Invalid email format"
    )

    @Pattern(
        regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$",
        message = "Only Gmail addresses are allowed"
    )
    private String email;









    @NotBlank(
        message = "Password is required"
    )

    @Size(
        min = 8,
        message = "Password must be at least 8 characters"
    )

    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[A-Z].*$",
        message = "Password must start with capital letter and contain number and special character"
    )
    private String password;









    @NotBlank(
        message = "Admin secret key is required"
    )
    private String adminSecretKey;









    // ================= CONSTRUCTORS =================



    public AdminRegisterRequestDTO() {

    }







    public AdminRegisterRequestDTO(

            String fullName,

            String email,

            String password,

            String adminSecretKey

    ) {


        this.fullName = fullName;

        this.email = email;

        this.password = password;

        this.adminSecretKey = adminSecretKey;


    }









    // ================= GETTERS & SETTERS =================



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








    public String getAdminSecretKey() {

        return adminSecretKey;

    }



    public void setAdminSecretKey(String adminSecretKey) {

        this.adminSecretKey = adminSecretKey;

    }









    @Override
    public String toString() {


        return "AdminRegisterRequestDTO{" +

                "fullName='" + fullName + '\'' +

                ", email='" + email + '\'' +

                '}';


    }


}