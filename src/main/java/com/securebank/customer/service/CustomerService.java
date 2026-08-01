
package com.securebank.customer.service;

import jakarta.servlet.http.HttpServletRequest;

import com.securebank.security.SecurityLogService;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.Period;

import java.util.List;
import java.util.UUID;
import java.util.Random;
import java.util.stream.Collectors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.securebank.customer.dto.CustomerLoginRequestDTO;
import com.securebank.customer.dto.CustomerLoginResponseDTO;
import com.securebank.customer.dto.CustomerProfileDTO;
import com.securebank.customer.dto.CustomerRequestDTO;
import com.securebank.customer.dto.CustomerResponseDTO;
import com.securebank.customer.dto.CustomerUpdateDTO;

import com.securebank.customer.dto.SetTransactionPinRequestDTO;

import com.securebank.customer.dto.ForgotTransactionPinRequestDTO;
import com.securebank.customer.dto.VerifyTransactionPinOtpRequestDTO;
import com.securebank.customer.dto.ResetTransactionPinRequestDTO;



import com.securebank.email.dto.EmailRequestDTO;
import com.securebank.email.service.EmailService;



import com.securebank.entity.Customer;
import com.securebank.entity.PasswordResetToken;
import com.securebank.entity.TransactionPinResetToken;
import com.securebank.entity.TransactionPinOtp;



import com.securebank.enums.Role;



import com.securebank.exception.CustomerNotFoundException;



import com.securebank.repository.CustomerRepository;
import com.securebank.repository.PasswordResetTokenRepository;
import com.securebank.repository.TransactionPinResetTokenRepository;
import com.securebank.repository.TransactionPinOtpRepository;



import com.securebank.security.JwtService;




@Service
public class CustomerService {



    private static final Logger logger =

            LoggerFactory.getLogger(CustomerService.class);





    private final CustomerRepository customerRepository;



    private final PasswordResetTokenRepository passwordResetTokenRepository;



    // Keep old token repository temporarily

    private final TransactionPinResetTokenRepository transactionPinResetTokenRepository;



    // New OTP repository

    private final TransactionPinOtpRepository transactionPinOtpRepository;



    private final PasswordEncoder passwordEncoder;



    private final JwtService jwtService;



    private final EmailService emailService;

    private final SecurityLogService securityLogService;




    public CustomerService(


            CustomerRepository customerRepository,


            PasswordResetTokenRepository passwordResetTokenRepository,


            TransactionPinResetTokenRepository transactionPinResetTokenRepository,


            TransactionPinOtpRepository transactionPinOtpRepository,


            PasswordEncoder passwordEncoder,


            JwtService jwtService,


            EmailService emailService,


            SecurityLogService securityLogService


    ) {



        this.customerRepository =

                customerRepository;




        this.passwordResetTokenRepository =

                passwordResetTokenRepository;




        this.transactionPinResetTokenRepository =

                transactionPinResetTokenRepository;




        this.transactionPinOtpRepository =

                transactionPinOtpRepository;




        this.passwordEncoder =

                passwordEncoder;




        this.jwtService =

                jwtService;




        this.emailService =

                emailService;




        this.securityLogService =

                securityLogService;


    }
    // ================= REGISTER =================


    @Transactional
    public CustomerResponseDTO saveCustomerDTO(

            CustomerRequestDTO dto

    ) {


        logger.info(
                "Customer registration request received for email: {}",
                dto.getEmail()
        );



        if(customerRepository.existsByEmail(dto.getEmail())) {


            throw new RuntimeException(
                    "Email already registered"
            );

        }




        Customer customer = new Customer();





        customer.setFullName(
                dto.getFullName()
        );



        customer.setEmail(
                dto.getEmail()
        );



        customer.setMobileNumber(
                dto.getMobileNumber()
        );



        customer.setAddress(
                dto.getAddress()
        );



     // ================= PROFILE DETAILS =================

        customer.setDateOfBirth(
                dto.getDateOfBirth()
        );

        if (dto.getDateOfBirth() != null) {

            customer.setAge(

                    Math.max(

                            Period.between(

                                    dto.getDateOfBirth(),

                                    LocalDate.now()

                            ).getYears(),

                            0

                    )

            );

        }

        customer.setGender(
                dto.getGender()
        );
     // ================= LOGIN PASSWORD =================

        customer.setPassword(

                passwordEncoder.encode(

                        dto.getPassword()

                )

        );




        // ================= TRANSACTION PIN =================

        // Encrypt 6 digit transaction PIN before saving

        customer.setTransactionPin(

                passwordEncoder.encode(

                        dto.getTransactionPin()

                )

        );


        customer.setRole(
                Role.CUSTOMER
        );



        Customer savedCustomer =

                customerRepository.save(customer);




        EmailRequestDTO emailRequestDTO =

                new EmailRequestDTO();



        emailRequestDTO.setTo(

                savedCustomer.getEmail()

        );



        emailRequestDTO.setSubject(

                "Welcome to SecureBank"

        );



        emailRequestDTO.setMessage(

                "Hello "
                + savedCustomer.getFullName()
                + ",\n\n"
                + "Welcome to SecureBank."
                + "\nYour account has been created successfully."
                + "\n\nRegards,\nSecureBank Team"

        );



        emailService.sendEmail(

                emailRequestDTO

        );



        return convertToResponseDTO(

                savedCustomer

        );

    }






 // ================= LOGIN =================


    public CustomerLoginResponseDTO loginCustomer(

            CustomerLoginRequestDTO dto,

            HttpServletRequest request

    ) {String clientIp = request.getRemoteAddr();


if(clientIp.equals("0:0:0:0:0:0:0:1")) {

    clientIp = "127.0.0.1";

}


final String ipAddress = clientIp;


        Customer customer =

                customerRepository.findByEmail(

                        dto.getEmail()

                )

                .orElseThrow(() -> {


                    securityLogService.saveLog(

                            dto.getEmail(),

                            "CUSTOMER",

                            "LOGIN_FAILED",

                            "FAILED",

                            ipAddress

                    );



                    return new CustomerNotFoundException(

                            "Invalid email or password"

                    );


                });







        if(!passwordEncoder.matches(

                dto.getPassword(),

                customer.getPassword()

        )) {



            securityLogService.saveLog(

                    customer.getEmail(),

                    "CUSTOMER",

                    "LOGIN_FAILED",

                    "FAILED",

                    ipAddress

            );



            throw new RuntimeException(

                    "Invalid email or password"

            );

        }







        // ==============================
        // LOGIN SUCCESS LOG
        // ==============================


        securityLogService.saveLog(

                customer.getEmail(),

                "CUSTOMER",

                "LOGIN_SUCCESS",

                "SUCCESS",

                ipAddress

        );








        String token =

                jwtService.generateToken(

                        customer.getEmail(),

                        customer.getRole().name()

                );







        CustomerLoginResponseDTO response =

                new CustomerLoginResponseDTO();





        response.setToken(

                token

        );




        response.setType(

                "Bearer"

        );




        response.setMessage(

                "Login successful"

        );




        return response;


    }
 
 // ================= GET PROFILE =================

    public CustomerProfileDTO getProfile(

            String email

    ) {

        Customer customer =

                customerRepository.findByEmail(email)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        CustomerProfileDTO profile =

                new CustomerProfileDTO();



        profile.setFullName(

                customer.getFullName()

        );



        profile.setEmail(

                customer.getEmail()

        );



        profile.setMobileNumber(

                customer.getMobileNumber()

        );



        profile.setAddress(

                customer.getAddress()

        );



        // ================= PROFILE DETAILS =================

        profile.setAge(

                customer.getAge()

        );



        profile.setDateOfBirth(

                customer.getDateOfBirth()

        );



        profile.setGender(

                customer.getGender()

        );



        profile.setProfileImage(

                customer.getProfileImage()

        );



        // Calculate age if database age is null

        if (customer.getAge() == null
                && customer.getDateOfBirth() != null) {

            profile.setAge(

                    Math.max(

                            Period.between(

                                    customer.getDateOfBirth(),

                                    LocalDate.now()

                            ).getYears(),

                            0

                    )

            );

        }



        return profile;

    }



    // ================= UPDATE PROFILE =================

    @Transactional
    public CustomerProfileDTO updateProfile(

            String email,

            CustomerUpdateDTO dto

    ) {

        Customer customer =

                customerRepository.findByEmail(email)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        // ================= EDITABLE FIELDS =================

        customer.setMobileNumber(

                dto.getMobileNumber()

        );



        customer.setAddress(

                dto.getAddress()

        );



        Customer updatedCustomer =

                customerRepository.save(customer);



        return getProfile(

                updatedCustomer.getEmail()

        );

    }
 // ================= UPLOAD PROFILE IMAGE =================


    @Transactional
    public CustomerProfileDTO uploadProfileImage(

            String email,

            MultipartFile file

    ) {


        Customer customer =

                customerRepository.findByEmail(email)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );


        try {


            // Validate file

            if(file == null || file.isEmpty()) {

                throw new RuntimeException(
                        "Uploaded image is empty"
                );

            }


            if(file.getContentType() == null ||
                    !file.getContentType().startsWith("image/")) {

                throw new RuntimeException(
                        "Only image files are allowed"
                );

            }



            String uploadFolder =

                    System.getProperty("user.dir")
                    + "/uploads/profile/";



            Path folderPath =

                    Paths.get(uploadFolder);



            if(!Files.exists(folderPath)) {


                Files.createDirectories(folderPath);


            }



            String fileName =

                    UUID.randomUUID()

                    + "_profile-image."

                    + getExtension(
                            file.getOriginalFilename()
                    );


            Path filePath =
                    folderPath.resolve(fileName);

            byte[] bytes = file.getBytes();

            System.out.println("ORIGINAL NAME : " + file.getOriginalFilename());
            System.out.println("CONTENT TYPE  : " + file.getContentType());
            System.out.println("SIZE          : " + file.getSize());
            System.out.println("BYTE LENGTH   : " + bytes.length);

            Files.write(filePath, bytes);

            System.out.println("FILE EXISTS   : " + Files.exists(filePath));
            System.out.println("FILE SIZE     : " + Files.size(filePath));

            




            customer.setProfileImage(

                    "/uploads/profile/" + fileName

            );




            Customer updatedCustomer =

                    customerRepository.save(customer);




            return getProfile(

                    updatedCustomer.getEmail()

            );



        }

        catch(IOException e){



            throw new RuntimeException(

                    "Failed to upload profile image"

            );


        }


    }
    private String getExtension(String fileName){


        if(fileName == null || !fileName.contains(".")){

            return "png";

        }


        return fileName.substring(

                fileName.lastIndexOf(".") + 1

        );

    }


 
    // ================= FORGOT PASSWORD =================


    @Transactional

    public void sendPasswordResetEmail(

            String email

    ) {



        Customer customer =

                customerRepository.findByEmail(email)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );





        String token =

                UUID.randomUUID()

                        .toString();





        PasswordResetToken resetToken =

                new PasswordResetToken(

                        token,

                        customer,

                        LocalDateTime.now()

                                .plusMinutes(15)

                );





        passwordResetTokenRepository.save(

                resetToken

        );





        String resetLink =

                "http://localhost:4200/reset-password?token="

                        + token;





        emailService.sendPasswordResetEmail(

                customer.getEmail(),

                customer.getFullName(),

                resetLink

        );



    }







    // ================= RESET PASSWORD =================


    @Transactional

    public void resetPassword(

            String token,

            String newPassword,

            String confirmPassword

    ) {



        PasswordResetToken resetToken =


                passwordResetTokenRepository

                .findByToken(token)

                .orElseThrow(() ->

                        new RuntimeException(

                                "Invalid reset token"

                        )

                );






        if(resetToken.getExpiryDate()

                .isBefore(LocalDateTime.now())) {



            passwordResetTokenRepository

                    .delete(resetToken);



            throw new RuntimeException(

                    "Reset token expired"

            );

        }






        if(!newPassword.equals(confirmPassword)) {



            throw new RuntimeException(

                    "Passwords do not match"

            );

        }






        Customer customer =

                resetToken.getCustomer();





        customer.setPassword(

                passwordEncoder.encode(

                        newPassword

                )

        );




        customerRepository.save(

                customer

        );





        passwordResetTokenRepository

                .delete(resetToken);



    }
 // ================= GET ALL CUSTOMERS =================


    public List<CustomerResponseDTO> getAllCustomers(){


        logger.info(
                "Fetching all customers"
        );


        List<Customer> customers =

                customerRepository.findAll();



        return customers.stream()

                .map(this::convertToResponseDTO)

                .collect(Collectors.toList());

    }








    // ================= GET CUSTOMER BY ID =================


    public CustomerResponseDTO getCustomerById(

            Long id

    ){


        logger.info(

                "Fetching customer by id: {}",

                id

        );



        Customer customer =

                customerRepository.findById(id)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        return convertToResponseDTO(customer);


    }







    // ================= UPDATE CUSTOMER =================


    @Transactional
    public CustomerResponseDTO updateCustomer(

            Long id,

            CustomerUpdateDTO dto

    ){


        logger.info(

                "Updating customer id: {}",

                id

        );



        Customer customer =

                customerRepository.findById(id)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        customer.setFullName(

                dto.getFullName()

        );



        customer.setMobileNumber(

                dto.getMobileNumber()

        );



        customer.setAddress(

                dto.getAddress()

        );





        if(dto.getDateOfBirth() != null){


            customer.setDateOfBirth(

                    dto.getDateOfBirth()

            );


            customer.setAge(

                    Period.between(

                            dto.getDateOfBirth(),

                            LocalDate.now()

                    ).getYears()

            );

        }



        if(dto.getGender() != null){

            customer.setGender(

                    dto.getGender()

            );

        }





        Customer savedCustomer =

                customerRepository.save(customer);




        return convertToResponseDTO(

                savedCustomer

        );


    }








    // ================= DELETE CUSTOMER =================


    @Transactional
    public void deleteCustomer(

            Long id

    ){


        Customer customer =

                customerRepository.findById(id)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        customerRepository.delete(customer);


    }









    // ================= SET TRANSACTION PIN =================


    @Transactional
    public void setTransactionPin(

            String email,

            SetTransactionPinRequestDTO dto

    ){


        logger.info(

                "Setting transaction PIN for customer: {}",

                email

        );



        Customer customer =

                customerRepository.findByEmail(email)

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        customer.setTransactionPin(

                passwordEncoder.encode(

                        dto.getTransactionPin()

                )

        );



        customerRepository.save(customer);



    }






    public boolean verifyTransactionPin(
            String email,
            String transactionPin
    ) {

        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer not found"));

        return passwordEncoder.matches(
                transactionPin,
                customer.getTransactionPin()
        );
    }

 // ================= FORGOT TRANSACTION PIN =================


    @Transactional
    public void forgotTransactionPin(

            ForgotTransactionPinRequestDTO dto

    ) {



        Customer customer =


                customerRepository.findByEmail(

                        dto.getEmail()

                )


                .orElseThrow(() ->


                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );





        // Delete previous OTP

        transactionPinOtpRepository

                .deleteByCustomerCustomerId(

                        customer.getCustomerId()

                );






        // Generate 6 digit OTP

        String otp =


                String.valueOf(

                        100000 +

                        new Random().nextInt(900000)

                );






        TransactionPinOtp transactionPinOtp =


                new TransactionPinOtp(

                        otp,

                        customer,

                        LocalDateTime.now()

                                .plusMinutes(15)

                );







        transactionPinOtpRepository.save(

                transactionPinOtp

        );







        EmailRequestDTO emailRequestDTO =


                new EmailRequestDTO();







        emailRequestDTO.setTo(

                customer.getEmail()

        );







        emailRequestDTO.setSubject(

                "SecureBank Transaction PIN Reset OTP"

        );







        emailRequestDTO.setMessage(



                "Hello "

                + customer.getFullName()

                + ",\n\n"

                + "Your Transaction PIN reset OTP is: "

                + otp

                + "\n\nThis OTP is valid for 15 minutes."

                + "\n\nDo not share this OTP with anyone."

                + "\n\nRegards,\nSecureBank Team"



        );







        emailService.sendEmail(

                emailRequestDTO

        );


    }
 // ================= VERIFY TRANSACTION PIN OTP =================


    @Transactional
    public void verifyTransactionPinOtp(

            VerifyTransactionPinOtpRequestDTO dto

    ) {


        Customer customer =


                customerRepository.findByEmail(

                        dto.getEmail()

                )


                .orElseThrow(() ->


                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );





        TransactionPinOtp otpRecord =


                transactionPinOtpRepository

                .findByOtpAndCustomerCustomerId(

                        dto.getOtp(),

                        customer.getCustomerId()

                )


                .orElseThrow(() ->


                        new RuntimeException(

                                "Invalid OTP"

                        )

                );







        if(otpRecord.getExpiryTime()

                .isBefore(LocalDateTime.now())) {



            transactionPinOtpRepository.delete(

                    otpRecord

            );



            throw new RuntimeException(

                    "OTP expired"

            );

        }






        otpRecord.setVerified(true);



        transactionPinOtpRepository.save(

                otpRecord

        );
        


    }
 // ================= RESET TRANSACTION PIN =================

    @Transactional
    public void resetTransactionPin(

            ResetTransactionPinRequestDTO dto

    ) {

        Customer customer =

                customerRepository.findByEmail(

                        dto.getEmail()

                )

                .orElseThrow(() ->

                        new CustomerNotFoundException(

                                "Customer not found"

                        )

                );



        TransactionPinOtp otpRecord =

                transactionPinOtpRepository

                        .findTopByCustomerCustomerIdOrderByIdDesc(

                                customer.getCustomerId()

                        )

                        .orElseThrow(() ->

                                new RuntimeException(

                                        "OTP verification required"

                                )

                        );



        if (!otpRecord.isVerified()) {

            throw new RuntimeException(

                    "Please verify OTP first"

            );

        }



        if (!dto.getNewPin().equals(dto.getConfirmPin())) {

            throw new RuntimeException(

                    "Transaction PINs do not match"

            );

        }



        customer.setTransactionPin(

                passwordEncoder.encode(

                        dto.getNewPin()

                )

        );



        customerRepository.save(

                customer

        );



        transactionPinOtpRepository.delete(

                otpRecord

        );



        // ================= SEND CONFIRMATION EMAIL =================

        EmailRequestDTO emailRequestDTO = new EmailRequestDTO();

        emailRequestDTO.setTo(

                customer.getEmail()

        );

        emailRequestDTO.setSubject(

                "SecureBank Transaction PIN Reset Successful"

        );

        emailRequestDTO.setMessage(

                "Hello "

                + customer.getFullName()

                + ",\n\n"

                + "Your SecureBank Transaction PIN has been reset successfully."

                + "\n\nIf you performed this action, no further action is required."

                + "\n\nIf you did NOT reset your Transaction PIN, please contact SecureBank Customer Support immediately."

                + "\n\nRegards,\nSecureBank Team"

        );

        emailService.sendEmail(

                emailRequestDTO

        );

    }
 // ================= DTO CONVERTER =================


    private CustomerResponseDTO convertToResponseDTO(

            Customer customer

    ){


        CustomerResponseDTO dto =

                new CustomerResponseDTO();




        dto.setCustomerId(

                customer.getCustomerId()

        );




        dto.setFullName(

                customer.getFullName()

        );




        dto.setEmail(

                customer.getEmail()

        );




        dto.setMobileNumber(

                customer.getMobileNumber()

        );




        dto.setAddress(

                customer.getAddress()

        );




        dto.setRole(

                customer.getRole().name()

        );





        // ================= PROFILE DETAILS =================


        dto.setAge(

                customer.getAge()

        );




        dto.setDateOfBirth(

                customer.getDateOfBirth()

        );




        dto.setGender(

                customer.getGender()

        );




        dto.setProfileImage(

                customer.getProfileImage()

        );





        return dto;


    }
}