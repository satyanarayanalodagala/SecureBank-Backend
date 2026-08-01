package com.securebank.forgotpassword.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securebank.email.dto.EmailRequestDTO;
import com.securebank.email.service.EmailService;
import com.securebank.entity.Customer;
import com.securebank.entity.PasswordResetToken;
import com.securebank.forgotpassword.dto.ForgotPasswordRequestDTO;
import com.securebank.forgotpassword.dto.ResetPasswordRequestDTO;
import com.securebank.repository.CustomerRepository;
import com.securebank.repository.PasswordResetTokenRepository;

@Service
public class ForgotPasswordService {

    private static final Logger logger =
            LoggerFactory.getLogger(ForgotPasswordService.class);

    private final CustomerRepository customerRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public ForgotPasswordService(
            CustomerRepository customerRepository,
            PasswordResetTokenRepository passwordResetTokenRepository,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {

        this.customerRepository = customerRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    // Generate reset token and send email
    @Transactional
    public String forgotPassword(
            ForgotPasswordRequestDTO requestDTO) {

        logger.info(
                "Forgot password request received for email: {}",
                requestDTO.getEmail());

        Customer customer =
                customerRepository
                        .findByEmail(requestDTO.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Email not registered"));

        String token = UUID.randomUUID().toString();

        logger.info(
                "Password reset token generated for customer: {}",
                customer.getEmail());

        PasswordResetToken resetToken =
                new PasswordResetToken();

        resetToken.setToken(token);

        resetToken.setCustomer(customer);

        resetToken.setExpiryDate(
                LocalDateTime.now()
                        .plusMinutes(15));

        passwordResetTokenRepository
                .save(resetToken);

        logger.info(
                "Password reset token saved successfully for email: {}",
                customer.getEmail());

        EmailRequestDTO emailRequest =
                new EmailRequestDTO();

        emailRequest.setTo(
                customer.getEmail());

        emailRequest.setSubject(
                "SecureBank Password Reset Request");

        emailRequest.setMessage(
                "Hello "
                + customer.getFullName()
                + ",\n\n"
                + "You requested a password reset."
                + "\n\nYour password reset token is:"
                + "\n\n"
                + token
                + "\n\n"
                + "This token expires in 15 minutes."
                + "\n\nRegards,\nSecureBank Team");

        emailService.sendEmail(emailRequest);

        logger.info(
                "Password reset email sent successfully to {}",
                customer.getEmail());

        return "Password reset token sent to email";
    }

    // Reset password using token
    @Transactional
    public String resetPassword(
            ResetPasswordRequestDTO requestDTO) {

        logger.info(
                "Password reset request received with token.");

        PasswordResetToken resetToken =
                passwordResetTokenRepository
                        .findByToken(requestDTO.getToken())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invalid reset token"));

        // Check token expiry
        if (resetToken.getExpiryDate()
                .isBefore(LocalDateTime.now())) {

            logger.warn(
                    "Password reset failed. Token expired.");

            passwordResetTokenRepository
                    .deleteByToken(
                            requestDTO.getToken());

            logger.info(
                    "Expired password reset token deleted.");

            throw new RuntimeException(
                    "Reset token expired");
        }

        // Check password confirmation
        if (!requestDTO.getNewPassword()
                .equals(requestDTO.getConfirmPassword())) {

            logger.warn(
                    "Password reset failed. Password confirmation mismatch.");

            throw new RuntimeException(
                    "New password and confirm password do not match");
        }

        Customer customer =
                resetToken.getCustomer();

        // Encrypt new password
        String encryptedPassword =
                passwordEncoder.encode(
                        requestDTO.getNewPassword());

        customer.setPassword(
                encryptedPassword);

        customerRepository.save(customer);

        logger.info(
                "Password updated successfully for customer: {}",
                customer.getEmail());

        // Send password changed confirmation email
        EmailRequestDTO emailRequest =
                new EmailRequestDTO();

        emailRequest.setTo(
                customer.getEmail());

        emailRequest.setSubject(
                "SecureBank Password Changed Successfully");

        emailRequest.setMessage(
                "Hello "
                + customer.getFullName()
                + ",\n\n"
                + "Your password has been changed successfully."
                + "\n\n"
                + "If you did not perform this action,"
                + " please contact SecureBank support immediately."
                + "\n\n"
                + "Regards,\nSecureBank Team");

        emailService.sendEmail(emailRequest);

        logger.info(
                "Password changed confirmation email sent to {}",
                customer.getEmail());

        // Delete token after successful reset
        passwordResetTokenRepository
                .deleteByToken(
                        requestDTO.getToken());

        logger.info(
                "Password reset token deleted successfully.");

        return "Password reset successfully";
    }
}