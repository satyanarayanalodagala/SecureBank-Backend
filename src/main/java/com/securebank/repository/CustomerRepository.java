package com.securebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.securebank.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customer using email for login/JWT authentication
    Optional<Customer> findByEmail(String email);

    // Check duplicate email during registration
    boolean existsByEmail(String email);

}