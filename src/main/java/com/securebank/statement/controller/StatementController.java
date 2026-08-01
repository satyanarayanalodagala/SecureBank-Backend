package com.securebank.statement.controller;

import java.time.LocalDate;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.securebank.statement.service.StatementService;


@RestController
@RequestMapping("/api/statements")
public class StatementController {


    private final StatementService statementService;


    public StatementController(StatementService statementService) {
        this.statementService = statementService;
    }



    @GetMapping("/download")
    public ResponseEntity<Resource> downloadStatement(
            @RequestParam String accountNumber,
            @RequestParam String fromDate,
            @RequestParam String toDate) {


        Resource resource =
                statementService.generateStatement(
                        accountNumber,
                        LocalDate.parse(fromDate),
                        LocalDate.parse(toDate)
                );


        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=account_statement.pdf"
                )
                .body(resource);
    }

}