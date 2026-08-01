package com.securebank.admin.controller;


import java.util.List;


import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.securebank.admin.dto.SecurityDashboardResponseDTO;

import com.securebank.entity.SecurityLog;

import com.securebank.response.ApiResponse;
import com.securebank.response.ResponseBuilder;

import com.securebank.security.SecurityLogService;



@RestController
@RequestMapping("/api/admin/security")
public class SecurityController {




    private final SecurityLogService securityLogService;






    public SecurityController(

            SecurityLogService securityLogService

    ) {

        this.securityLogService = securityLogService;

    }









    // ==========================================
    // SECURITY DASHBOARD CARDS
    // ==========================================


    @GetMapping("/dashboard")
    public ResponseEntity<ApiResponse<SecurityDashboardResponseDTO>> getSecurityDashboard(){



        long totalLoginAttempts =

                securityLogService

                .getTotalLoginAttempts();







        long successfulLogins =

                securityLogService

                .getSuccessfulLogins();







        long failedLogins =

                securityLogService

                .getFailedLogins();









        SecurityDashboardResponseDTO response =

                new SecurityDashboardResponseDTO(

                        totalLoginAttempts,

                        successfulLogins,

                        failedLogins

                );









        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Security dashboard fetched successfully",

                        response

                )

        );


    }












    // ==========================================
    // RECENT LOGIN ACTIVITIES
    // ==========================================


    @GetMapping("/logs")
    public ResponseEntity<ApiResponse<List<SecurityLog>>> getSecurityLogs(){



        List<SecurityLog> logs =

                securityLogService

                .getRecentActivities();






        return ResponseEntity.ok(

                ResponseBuilder.success(

                        "Security logs fetched successfully",

                        logs

                )

        );


    }





}