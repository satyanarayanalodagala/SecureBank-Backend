package com.securebank.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;


@RestControllerAdvice
public class GlobalExceptionHandler {


    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);





    // =====================================================
    // CUSTOMER NOT FOUND
    // =====================================================


    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleCustomerNotFoundException(
            CustomerNotFoundException ex) {


        logger.warn(
                "Customer resource not found. Message: {}",
                ex.getMessage());



        ErrorResponse error =
                new ErrorResponse(

                        HttpStatus.NOT_FOUND.value(),

                        HttpStatus.NOT_FOUND.getReasonPhrase(),

                        ex.getMessage()
                );


        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND);

    }






    // =====================================================
    // RESOURCE NOT FOUND
    // =====================================================


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleResourceNotFoundException(
            ResourceNotFoundException ex) {



        logger.warn(
                "Requested resource not found. Message: {}",
                ex.getMessage());



        ErrorResponse error =
                new ErrorResponse(

                        HttpStatus.NOT_FOUND.value(),

                        HttpStatus.NOT_FOUND.getReasonPhrase(),

                        ex.getMessage()
                );


        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND);

    }








    // =====================================================
    // VALIDATION ERROR
    // =====================================================


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>>
    handleValidationException(
            MethodArgumentNotValidException ex) {



        logger.warn(
                "Request validation failed");



        Map<String,String> validationErrors =
                new LinkedHashMap<>();



        for(FieldError error :
                ex.getBindingResult()
                .getFieldErrors()) {



            validationErrors.put(

                    error.getField(),

                    error.getDefaultMessage()
            );



            logger.warn(
                    "Validation error. Field: {}, Reason: {}",
                    error.getField(),
                    error.getDefaultMessage());

        }




        Map<String,Object> response =
                new LinkedHashMap<>();


        response.put(
                "status",
                HttpStatus.BAD_REQUEST.value());


        response.put(
                "error",
                HttpStatus.BAD_REQUEST.getReasonPhrase());


        response.put(
                "message",
                "Validation Failed");


        response.put(
                "errors",
                validationErrors);




        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST);

    }








    // =====================================================
    // CONSTRAINT VALIDATION
    // =====================================================


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse>
    handleConstraintViolationException(
            ConstraintViolationException ex) {



        logger.warn(
                "Constraint validation failed: {}",
                ex.getMessage());



        ErrorResponse error =
                new ErrorResponse(

                        HttpStatus.BAD_REQUEST.value(),

                        HttpStatus.BAD_REQUEST.getReasonPhrase(),

                        ex.getMessage()
                );



        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST);

    }








    // =====================================================
    // METHOD NOT SUPPORTED
    // =====================================================


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse>
    handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {



        logger.warn(
                "HTTP method not supported. Method: {}",
                ex.getMethod());



        ErrorResponse error =
                new ErrorResponse(

                        HttpStatus.METHOD_NOT_ALLOWED.value(),

                        HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),

                        ex.getMessage()
                );



        return new ResponseEntity<>(
                error,
                HttpStatus.METHOD_NOT_ALLOWED);

    }








    // =====================================================
    // RUNTIME EXCEPTION
    // =====================================================


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse>
    handleRuntimeException(
            RuntimeException ex) {



        logger.error(
                "Runtime exception occurred. Type: {}, Message: {}",
                ex.getClass().getSimpleName(),
                ex.getMessage(),
                ex);



        ErrorResponse error =
                new ErrorResponse(

                        HttpStatus.BAD_REQUEST.value(),

                        HttpStatus.BAD_REQUEST.getReasonPhrase(),

                        ex.getMessage()
                );



        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST);

    }








    // =====================================================
    // UNKNOWN EXCEPTION
    // =====================================================


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(
            Exception ex) {



        logger.error(
                "Unexpected system error occurred. Type: {}",
                ex.getClass().getSimpleName(),
                ex);



        ErrorResponse error =
                new ErrorResponse(

                        HttpStatus.INTERNAL_SERVER_ERROR.value(),

                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),

                        "Internal Server Error"
                );



        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR);

    }


}