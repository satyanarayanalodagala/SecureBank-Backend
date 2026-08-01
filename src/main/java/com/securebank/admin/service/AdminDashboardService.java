package com.securebank.admin.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securebank.admin.dto.AdminDashboardResponseDTO;
import com.securebank.repository.AccountRepository;
import com.securebank.repository.CustomerRepository;
import com.securebank.repository.TransactionRepository;

@Service
public class AdminDashboardService {

    private static final Logger logger =
            LoggerFactory.getLogger(AdminDashboardService.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public AdminDashboardResponseDTO getDashboardDetails() {

        logger.info("Admin dashboard access request received.");

        Long totalCustomers = customerRepository.count();
        Long totalAccounts = accountRepository.count();
        Long totalTransactions = transactionRepository.count();

        BigDecimal totalBalance = accountRepository.getTotalBankBalance();

        if (totalBalance == null) {
            totalBalance = BigDecimal.ZERO;
        }

        logger.info(
                "Dashboard statistics fetched successfully. Customers: {}, Accounts: {}, Transactions: {}, Total Balance: {}",
                totalCustomers,
                totalAccounts,
                totalTransactions,
                totalBalance);

        AdminDashboardResponseDTO dashboard = new AdminDashboardResponseDTO();

        dashboard.setTotalCustomers(totalCustomers);
        dashboard.setTotalAccounts(totalAccounts);
        dashboard.setTotalTransactions(totalTransactions);
        dashboard.setTotalBalance(totalBalance);

        return dashboard;
    }
}