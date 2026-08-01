package com.securebank.forgotpassword.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.securebank.forgotpassword.dto.ForgotPasswordRequestDTO;
import com.securebank.forgotpassword.dto.ResetPasswordRequestDTO;
import com.securebank.forgotpassword.service.ForgotPasswordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(
            ForgotPasswordService forgotPasswordService) {

        this.forgotPasswordService = forgotPasswordService;
    }

    // Send password reset email
    @PostMapping
    public ResponseEntity<String> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDTO requestDTO) {

        return ResponseEntity.ok(
                forgotPasswordService.forgotPassword(requestDTO));
    }

    // Reset password
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
            @Valid @RequestBody ResetPasswordRequestDTO requestDTO) {

        return ResponseEntity.ok(
                forgotPasswordService.resetPassword(requestDTO));
    }
}