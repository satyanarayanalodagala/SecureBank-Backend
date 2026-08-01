package com.securebank.admin.dto;


public class AdminRegisterResponseDTO {


    private Long adminId;

    private String fullName;

    private String email;

    private String role;

    private String message;



    // Default Constructor
    public AdminRegisterResponseDTO() {
    }



    // Parameterized Constructor
    public AdminRegisterResponseDTO(
            Long adminId,
            String fullName,
            String email,
            String role,
            String message) {

        this.adminId = adminId;
        this.fullName = fullName;
        this.email = email;
        this.role = role;
        this.message = message;
    }



    public Long getAdminId() {
        return adminId;
    }


    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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



    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }



    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }



    @Override
    public String toString() {

        return "AdminRegisterResponseDTO{" +
                "adminId=" + adminId +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}