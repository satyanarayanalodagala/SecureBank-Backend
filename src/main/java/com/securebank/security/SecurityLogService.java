package com.securebank.security;


import java.util.List;


import org.springframework.stereotype.Service;


import com.securebank.entity.SecurityLog;
import com.securebank.repository.SecurityLogRepository;



@Service
public class SecurityLogService {




    private final SecurityLogRepository securityLogRepository;




    public SecurityLogService(

            SecurityLogRepository securityLogRepository

    ) {

        this.securityLogRepository = securityLogRepository;

    }









    // =====================================
    // SAVE SECURITY LOG
    // =====================================


    public void saveLog(

            String email,

            String role,

            String action,

            String status,

            String ipAddress

    ) {



        SecurityLog log = new SecurityLog(

                email,

                role,

                action,

                status,

                ipAddress

        );



        securityLogRepository.save(log);


    }









    // =====================================
    // TOTAL LOGIN ATTEMPTS
    // =====================================


    public long getTotalLoginAttempts(){


        return securityLogRepository.count();


    }









    // =====================================
    // GET RECENT SECURITY ACTIVITIES
    // =====================================


    public List<SecurityLog> getRecentActivities(){



        return securityLogRepository

                .findTop20ByOrderByLoginTimeDesc();


    }









    // =====================================
    // COUNT SUCCESSFUL LOGINS
    // =====================================


    public long getSuccessfulLogins(){



        return securityLogRepository

                .countByStatus("SUCCESS");


    }









    // =====================================
    // COUNT FAILED LOGINS
    // =====================================


    public long getFailedLogins(){



        return securityLogRepository

                .countByStatus("FAILED");


    }









    // =====================================
    // COUNT BY ACTION
    // =====================================


    public long getLoginSuccessCount(){



        return securityLogRepository

                .countByAction("LOGIN_SUCCESS");


    }









    public long getLoginFailedCount(){



        return securityLogRepository

                .countByAction("LOGIN_FAILED");


    }









    // =====================================
    // USER SECURITY HISTORY
    // =====================================


    public List<SecurityLog> getUserHistory(

            String email

    ){



        return securityLogRepository

                .findByEmailOrderByLoginTimeDesc(email);


    }



}