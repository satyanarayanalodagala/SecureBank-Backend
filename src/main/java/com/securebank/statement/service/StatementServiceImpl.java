package com.securebank.statement.service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.securebank.entity.Transaction;
import com.securebank.repository.TransactionRepository;

@Service
public class StatementServiceImpl
        implements StatementService {

    private static final Logger logger =
            LoggerFactory.getLogger(StatementServiceImpl.class);

    private final TransactionRepository transactionRepository;

    public StatementServiceImpl(
            TransactionRepository transactionRepository) {

        this.transactionRepository = transactionRepository;
    }

    @Override
    public Resource generateStatement(
            String accountNumber,
            LocalDate fromDate,
            LocalDate toDate) {

        logger.info(
                "PDF statement generation requested. Account: {}, From: {}, To: {}",
                accountNumber,
                fromDate,
                toDate);

        List<Transaction> transactions =
                transactionRepository
                .findByAccountAccountNumberAndTransactionDateBetweenOrderByTransactionTimeDesc(
                        accountNumber,
                        fromDate,
                        toDate);

        logger.info(
                "Found {} transactions for statement generation.",
                transactions.size());

        try {

            ByteArrayOutputStream output =
                    new ByteArrayOutputStream();

            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    output);

            document.open();

            Font headingFont =
                    new Font(
                            Font.HELVETICA,
                            18,
                            Font.BOLD);

            Paragraph heading =
                    new Paragraph(
                            "SecureBank\nAccount Statement",
                            headingFont);

            heading.setAlignment(
                    Element.ALIGN_CENTER);

            document.add(heading);

            document.add(
                    new Paragraph(
                            "\nAccount Number : "
                            + accountNumber));

            document.add(
                    new Paragraph(
                            "Period : "
                            + fromDate
                            + " to "
                            + toDate));

            document.add(
                    new Paragraph("\n"));

            PdfPTable table =
                    new PdfPTable(4);

            table.setWidthPercentage(100);

            table.addCell(
                    new PdfPCell(
                            new Phrase("Date")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Type")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Amount")));

            table.addCell(
                    new PdfPCell(
                            new Phrase("Description")));

            for(Transaction transaction : transactions) {

                table.addCell(
                        transaction.getTransactionDate()
                        .format(
                        DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                table.addCell(
                        transaction.getTransactionType()
                        .name());

                table.addCell(
                        "₹ "
                        + transaction.getAmount());

                table.addCell(
                        transaction.getDescription() == null
                        ? "-"
                        : transaction.getDescription());

            }

            document.add(table);

            document.add(
                    new Paragraph(
                    "\nThank You For Banking With SecureBank"));

            document.close();

            logger.info(
                    "PDF statement generated successfully for Account: {}",
                    accountNumber);

            logger.info(
                    "Statement download completed for Account: {}",
                    accountNumber);

            return new ByteArrayResource(
                    output.toByteArray());

        }
        catch(Exception e) {

            logger.error(
                    "PDF statement generation failed for Account: {}",
                    accountNumber,
                    e);

            throw new RuntimeException(
                    "PDF generation failed",
                    e);

        }

    }

}