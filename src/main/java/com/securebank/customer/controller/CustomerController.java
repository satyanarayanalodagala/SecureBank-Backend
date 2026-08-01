package com.securebank.customer.controller;


import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;



import com.securebank.customer.dto.CustomerLoginRequestDTO;

import com.securebank.customer.dto.CustomerLoginResponseDTO;

import com.securebank.customer.dto.CustomerProfileDTO;

import com.securebank.customer.dto.CustomerRequestDTO;

import com.securebank.customer.dto.CustomerResponseDTO;

import com.securebank.customer.dto.CustomerUpdateDTO;

import com.securebank.customer.dto.SetTransactionPinRequestDTO;

import com.securebank.customer.dto.ForgotTransactionPinRequestDTO;

import com.securebank.customer.dto.ResetTransactionPinRequestDTO;



import com.securebank.customer.service.CustomerService;


import com.securebank.forgotpassword.dto.ForgotPasswordRequestDTO;

import com.securebank.forgotpassword.dto.ResetPasswordRequestDTO;



import com.securebank.response.ApiResponse;

import com.securebank.response.ResponseBuilder;



import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;

import com.securebank.customer.dto.VerifyTransactionPinOtpRequestDTO;


@RestController

@RequestMapping("/api/customers")

@CrossOrigin(origins = "*")

@Validated

public class CustomerController {





    private static final Logger logger =

            LoggerFactory.getLogger(CustomerController.class);





    private final CustomerService customerService;





    public CustomerController(

            CustomerService customerService

    ) {

        this.customerService = customerService;

    }
    // ================= REGISTER =================


    @PostMapping("/register")

    public ResponseEntity<ApiResponse<CustomerResponseDTO>> register(

            @Valid @RequestBody CustomerRequestDTO dto

    ) {



        CustomerResponseDTO response =

                customerService.saveCustomerDTO(dto);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer registered successfully",

                        response

                )

        );

    }






 // ================= LOGIN =================


    @PostMapping("/login")

    public ResponseEntity<ApiResponse<CustomerLoginResponseDTO>> login(

            @Valid @RequestBody CustomerLoginRequestDTO dto,

            HttpServletRequest request

    ) {



        CustomerLoginResponseDTO response =

                customerService.loginCustomer(

                        dto,

                        request

                );



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Login successful",

                        response

                )

        );

    }

    // ================= GET PROFILE =================


    @PreAuthorize("hasAnyRole('CUSTOMER','ADMIN')")

    @GetMapping("/profile")

    public ResponseEntity<ApiResponse<CustomerProfileDTO>> getProfile(

            Authentication authentication

    ) {



        String email = authentication.getName();



        CustomerProfileDTO response =

                customerService.getProfile(email);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Customer profile fetched successfully",

                        response

                )

        );

    }



 // ================= UPDATE PROFILE =================

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<CustomerProfileDTO>> updateProfile(

            Authentication authentication,

            @Valid @RequestBody CustomerUpdateDTO dto

    ) {

        CustomerProfileDTO response =

                customerService.updateProfile(

                        authentication.getName(),

                        dto

                );

        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Profile updated successfully",

                        response

                )

        );

    }



    // ================= UPLOAD PROFILE IMAGE =================


    @PostMapping(
            value="/profile/image",
            consumes="multipart/form-data"
    )

    public ResponseEntity<ApiResponse<CustomerProfileDTO>> uploadProfileImage(

            Authentication authentication,

            @RequestPart("file") MultipartFile file

    ){


        CustomerProfileDTO response =

                customerService.uploadProfileImage(

                        authentication.getName(),

                        file

                );



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Profile image uploaded successfully",

                        response

                )

        );

    }







    // ================= FORGOT PASSWORD =================


    @PostMapping("/forgot-password")

    public ResponseEntity<ApiResponse<String>> forgotPassword(

            @Valid @RequestBody ForgotPasswordRequestDTO dto

    ){


        customerService.sendPasswordResetEmail(

                dto.getEmail()

        );


        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Password reset email sent successfully",

                        "Email sent"

                )

        );

    }







    // ================= RESET PASSWORD =================


    @PostMapping("/reset-password")

    public ResponseEntity<ApiResponse<String>> resetPassword(

            @Valid @RequestBody ResetPasswordRequestDTO dto

    ){


        customerService.resetPassword(

                dto.getToken(),

                dto.getNewPassword(),

                dto.getConfirmPassword()

        );


        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Password reset successfully",

                        "Password updated successfully"

                )

        );

    }
    // ================= SET TRANSACTION PIN =================


    @PreAuthorize("hasRole('CUSTOMER')")

    @PostMapping("/set-transaction-pin")

    public ResponseEntity<ApiResponse<String>> setTransactionPin(

            Authentication authentication,

            @Valid @RequestBody SetTransactionPinRequestDTO dto

    ){


        customerService.setTransactionPin(

                authentication.getName(),

                dto

        );



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction PIN created successfully",

                        "Transaction PIN created successfully"

                )

        );

    }







    // ================= FORGOT TRANSACTION PIN =================


    @PostMapping("/forgot-transaction-pin")

    public ResponseEntity<ApiResponse<String>> forgotTransactionPin(

            @Valid @RequestBody ForgotTransactionPinRequestDTO dto

    ){



        customerService.forgotTransactionPin(dto);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction PIN reset email sent successfully",

                        "Email sent"

                )

        );


    }



 // ================= VERIFY TRANSACTION PIN OTP =================


    @PostMapping("/verify-transaction-pin-otp")

    public ResponseEntity<ApiResponse<String>> verifyTransactionPinOtp(

            @Valid @RequestBody VerifyTransactionPinOtpRequestDTO dto

    ){


        customerService.verifyTransactionPinOtp(dto);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "OTP verified successfully",

                        "OTP verified successfully"

                )

        );


    }



    // ================= RESET TRANSACTION PIN =================


    @PostMapping("/reset-transaction-pin")

    public ResponseEntity<ApiResponse<String>> resetTransactionPin(

            @Valid @RequestBody ResetTransactionPinRequestDTO dto

    ){



        customerService.resetTransactionPin(dto);



        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Transaction PIN reset successfully",

                        "Transaction PIN updated successfully"

                )

        );


    }







}