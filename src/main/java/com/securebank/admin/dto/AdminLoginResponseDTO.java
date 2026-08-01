package com.securebank.admin.dto;


public class AdminLoginResponseDTO {


    private String token;

    private String role;

    private String message;



    // Default Constructor
    public AdminLoginResponseDTO() {
    }



    // Parameterized Constructor
    public AdminLoginResponseDTO(
            String token,
            String role,
            String message) {

        this.token = token;
        this.role = role;
        this.message = message;
    }



    // Getter and Setter for token

    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }



    // Getter and Setter for role

    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }



    // Getter and Setter for message

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public String toString() {

        return "AdminLoginResponseDTO{" +
                "token='" + token + '\'' +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}