package com.securebank.admin.dto;


public class SecurityDashboardResponseDTO {


    private long totalLoginAttempts;


    private long successfulLogins;


    private long failedLogins;




    public SecurityDashboardResponseDTO() {

    }





    public SecurityDashboardResponseDTO(

            long totalLoginAttempts,

            long successfulLogins,

            long failedLogins

    ) {

        this.totalLoginAttempts = totalLoginAttempts;

        this.successfulLogins = successfulLogins;

        this.failedLogins = failedLogins;

    }





    public long getTotalLoginAttempts() {

        return totalLoginAttempts;

    }


    public void setTotalLoginAttempts(long totalLoginAttempts) {

        this.totalLoginAttempts = totalLoginAttempts;

    }





    public long getSuccessfulLogins() {

        return successfulLogins;

    }


    public void setSuccessfulLogins(long successfulLogins) {

        this.successfulLogins = successfulLogins;

    }





    public long getFailedLogins() {

        return failedLogins;

    }


    public void setFailedLogins(long failedLogins) {

        this.failedLogins = failedLogins;

    }

}