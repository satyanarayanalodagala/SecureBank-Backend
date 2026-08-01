package com.securebank.statement.service;


import java.time.LocalDate;

import org.springframework.core.io.Resource;


public interface StatementService {


    Resource generateStatement(
            String accountNumber,
            LocalDate fromDate,
            LocalDate toDate);

}