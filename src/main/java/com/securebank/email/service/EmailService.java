package com.securebank.email.service;


import java.math.BigDecimal;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import com.securebank.email.dto.EmailRequestDTO;



@Service
public class EmailService {



    private static final Logger logger =
            LoggerFactory.getLogger(EmailService.class);



    private final JavaMailSender mailSender;




    public EmailService(
            JavaMailSender mailSender
    ) {

        this.mailSender = mailSender;

    }








    public void sendEmail(
            EmailRequestDTO emailRequestDTO
    ) {



        logger.info(
                "Preparing email for: {}",
                emailRequestDTO.getTo()
        );



        SimpleMailMessage message =
                new SimpleMailMessage();



        message.setTo(
                emailRequestDTO.getTo()
        );



        message.setSubject(
                emailRequestDTO.getSubject()
        );



        message.setText(
                emailRequestDTO.getMessage()
        );





        mailSender.send(message);





        logger.info(
                "Email sent successfully to: {}",
                emailRequestDTO.getTo()
        );


    }









    public void sendPasswordResetEmail(

            String email,

            String fullName,

            String resetLink

    ) {



        logger.info(
                "Sending password reset email to: {}",
                email
        );



        SimpleMailMessage message =
                new SimpleMailMessage();





        message.setTo(email);



        message.setSubject(
                "SecureBank Password Reset"
        );




        message.setText(

                "Hello " + fullName
                + ",\n\n"
                + "We received a request to reset your password.\n\n"
                + "Reset Link:\n"
                + resetLink
                + "\n\nThis link is valid for 15 minutes."
                + "\n\nIf you did not request this change, please ignore this email."
                + "\n\nRegards,\nSecureBank Team"

        );




        mailSender.send(message);



        logger.info(
                "Password reset email sent successfully to: {}",
                email
        );


    }









    // ================= ACCOUNT CREATION EMAIL =================


    public void sendAccountCreationEmail(

            String email,

            String fullName,

            String accountNumber,

            String accountType,

            BigDecimal balance,

            String status

    ) {



        logger.info(

                "Sending account creation email to: {}",

                email

        );





        SimpleMailMessage message =

                new SimpleMailMessage();





        message.setTo(

                email

        );





        message.setSubject(

                "Your SecureBank Account Has Been Created"

        );







        message.setText(



                "Hello " + fullName

                + ",\n\n"

                + "Welcome to SecureBank Digital Banking.\n\n"

                + "Your bank account has been successfully created.\n\n"

                + "Account Details:\n\n"

                + "Account Number: "

                + accountNumber

                + "\n"

                + "Account Type: "

                + accountType

                + "\n"

                + "Account Status: "

                + status

                + "\n"

                + "Opening Balance: ₹"

                + balance

                + "\n\n"

                + "You can now access your account securely through SecureBank.\n\n"

                + "Thank you,\n"

                + "SecureBank Team"

        );







        mailSender.send(message);







        logger.info(

                "Account creation email sent successfully to: {}",

                email

        );



    }



}
