package com.securebank;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestPassword {

    public static void main(String[] args) {

        BCryptPasswordEncoder encoder =
                new BCryptPasswordEncoder();

        String encryptedPassword =
                "$2a$10$5vNgQc2idAm0oU.5ZwVviOEoN1Wwz6NIKCmLaCN6C1S/9qMKneqge";


        boolean result =
                encoder.matches(
                        "Admin@123",
                        encryptedPassword
                );


        System.out.println("Password Match Result : " + result);
    }
}