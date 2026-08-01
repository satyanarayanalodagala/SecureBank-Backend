package com.securebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securebank.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    /**
     * Find an admin by email.
     * Used during admin login authentication.
     */
    Optional<Admin> findByEmail(String email);

    /**
     * Check whether an admin email already exists.
     */
    boolean existsByEmail(String email);
}