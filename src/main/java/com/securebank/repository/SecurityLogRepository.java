package com.securebank.repository;


import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.securebank.entity.SecurityLog;



@Repository
public interface SecurityLogRepository

        extends JpaRepository<SecurityLog, Long> {





    // ==========================================
    // LATEST 20 SECURITY ACTIVITIES
    // ==========================================


    List<SecurityLog> findTop20ByOrderByLoginTimeDesc();








    // ==========================================
    // TOTAL LOGIN / SECURITY ATTEMPTS
    // ==========================================


    long count();








    // ==========================================
    // COUNT BY STATUS
    // SUCCESS / FAILED
    // ==========================================


    long countByStatus(

            String status

    );








    // ==========================================
    // COUNT BY ACTION
    // LOGIN_SUCCESS / LOGIN_FAILED
    // ==========================================


    long countByAction(

            String action

    );








    // ==========================================
    // USER SECURITY HISTORY
    // ==========================================


    List<SecurityLog> findByEmailOrderByLoginTimeDesc(

            String email

    );



}