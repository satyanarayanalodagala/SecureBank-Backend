package com.securebank.admin.dto;

import java.math.BigDecimal;

public class AdminDashboardResponseDTO {

    private Long totalCustomers;
    private Long totalAccounts;
    private Long totalTransactions;
    private BigDecimal totalBalance;

    // Default Constructor
    public AdminDashboardResponseDTO() {
    }

    // Parameterized Constructor
    public AdminDashboardResponseDTO(Long totalCustomers, Long totalAccounts,
                                     Long totalTransactions, BigDecimal totalBalance) {
        this.totalCustomers = totalCustomers;
        this.totalAccounts = totalAccounts;
        this.totalTransactions = totalTransactions;
        this.totalBalance = totalBalance;
    }

    public Long getTotalCustomers() {
        return totalCustomers;
    }

    public void setTotalCustomers(Long totalCustomers) {
        this.totalCustomers = totalCustomers;
    }

    public Long getTotalAccounts() {
        return totalAccounts;
    }

    public void setTotalAccounts(Long totalAccounts) {
        this.totalAccounts = totalAccounts;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Long totalTransactions) {
        this.totalTransactions = totalTransactions;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    @Override
    public String toString() {
        return "AdminDashboardResponseDTO [totalCustomers=" + totalCustomers
                + ", totalAccounts=" + totalAccounts
                + ", totalTransactions=" + totalTransactions
                + ", totalBalance=" + totalBalance + "]";
    }
}