package com.securebank.admin.service;


import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;


import com.securebank.admin.dto.AdminLoginRequestDTO;
import com.securebank.admin.dto.AdminLoginResponseDTO;
import com.securebank.admin.dto.AdminRegisterRequestDTO;
import com.securebank.admin.dto.AdminRegisterResponseDTO;

import com.securebank.entity.Admin;

import com.securebank.repository.AdminRepository;

import com.securebank.security.JwtService;
import com.securebank.security.SecurityLogService;


import jakarta.servlet.http.HttpServletRequest;




@Service
public class AdminService {



    private static final Logger logger =
            LoggerFactory.getLogger(AdminService.class);






    @Value("${admin.secret.key}")
    private String adminSecretKey;







    @Autowired
    private AdminRepository adminRepository;



    @Autowired
    private PasswordEncoder passwordEncoder;



    @Autowired
    private JwtService jwtService;



    @Autowired
    private SecurityLogService securityLogService;









    // =====================================================
    // ADMIN REGISTER
    // =====================================================


    public AdminRegisterResponseDTO register(
            AdminRegisterRequestDTO request) {



        logger.info(
                "Admin registration request received"
        );





        if(!adminSecretKey.equals(
                request.getAdminSecretKey())) {



            throw new RuntimeException(
                    "Invalid Admin Secret Key."
            );

        }






        Optional<Admin> existingAdmin =

                adminRepository.findByEmail(
                        request.getEmail()
                );






        if(existingAdmin.isPresent()) {



            throw new RuntimeException(
                    "Admin already exists with this email."
            );

        }







        Admin admin = new Admin();



        admin.setFullName(
                request.getFullName()
        );



        admin.setEmail(
                request.getEmail()
        );



        admin.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );



        admin.setRole(
                "ADMIN"
        );







        Admin savedAdmin =

                adminRepository.save(admin);







        return new AdminRegisterResponseDTO(

                savedAdmin.getAdminId(),

                savedAdmin.getFullName(),

                savedAdmin.getEmail(),

                savedAdmin.getRole(),

                "Admin registered successfully."

        );


    }














    // =====================================================
    // ADMIN LOGIN
    // =====================================================


    public AdminLoginResponseDTO login(

            AdminLoginRequestDTO request,

            HttpServletRequest httpRequest

    ) {



        logger.info(
                "Admin login attempt received"
        );






        String ipAddress =

                httpRequest.getRemoteAddr();


        // Convert IPv6 localhost to IPv4 localhost

        if(ipAddress.equals("0:0:0:0:0:0:0:1")) {

            ipAddress = "127.0.0.1";

        }







        Optional<Admin> optionalAdmin =


                adminRepository.findByEmail(

                        request.getEmail()

                );









        // ==============================
        // EMAIL NOT FOUND
        // ==============================


        if(optionalAdmin.isEmpty()) {



            securityLogService.saveLog(

                    request.getEmail(),

                    "ADMIN",

                    "LOGIN_FAILED",

                    "FAILED",

                    ipAddress

            );



            throw new RuntimeException(
                    "Invalid email or password."
            );


        }









        Admin admin =

                optionalAdmin.get();









        boolean passwordMatch =


                passwordEncoder.matches(


                        request.getPassword(),


                        admin.getPassword()


                );









        // ==============================
        // WRONG PASSWORD
        // ==============================


        if(!passwordMatch) {



            securityLogService.saveLog(

                    admin.getEmail(),

                    "ADMIN",

                    "LOGIN_FAILED",

                    "FAILED",

                    ipAddress

            );




            throw new RuntimeException(
                    "Invalid email or password."
            );


        }









        // ==============================
        // LOGIN SUCCESS
        // ==============================


        securityLogService.saveLog(

                admin.getEmail(),

                "ADMIN",

                "LOGIN_SUCCESS",

                "SUCCESS",

                ipAddress

        );









        String token =


                jwtService.generateToken(

                        admin.getEmail(),

                        admin.getRole()

                );









        logger.info(

                "Admin login successful. Admin ID: {}",

                admin.getAdminId()

        );








        return new AdminLoginResponseDTO(

                token,

                admin.getRole(),

                "Admin login successful."

        );


    }





}