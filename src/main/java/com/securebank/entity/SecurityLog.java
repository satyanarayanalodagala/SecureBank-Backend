package com.securebank.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "security_logs")
public class SecurityLog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    // User email who performed action
    private String email;



    // ADMIN / CUSTOMER
    private String role;



    // LOGIN_SUCCESS / LOGIN_FAILED / LOGOUT
    private String action;



    // SUCCESS / FAILED
    private String status;



    // User IP address
    private String ipAddress;



    // Login time
    private LocalDateTime loginTime;





    public SecurityLog() {

    }





   


    public SecurityLog(
            String email,
            String role,
            String action,
            String status,
            String ipAddress
    ) {

        this.email = email;
        this.role = role;
        this.action = action;
        this.status = status;
        this.ipAddress = ipAddress;
        this.loginTime = LocalDateTime.now();

    }



    public Long getId() {

        return id;

    }


    public void setId(Long id) {

        this.id = id;

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



    public String getAction() {

        return action;

    }


    public void setAction(String action) {

        this.action = action;

    }



    public String getStatus() {

        return status;

    }


    public void setStatus(String status) {

        this.status = status;

    }



    public String getIpAddress() {

        return ipAddress;

    }


    public void setIpAddress(String ipAddress) {

        this.ipAddress = ipAddress;

    }



    public LocalDateTime getLoginTime() {

        return loginTime;

    }


    public void setLoginTime(LocalDateTime loginTime) {

        this.loginTime = loginTime;

    }

}