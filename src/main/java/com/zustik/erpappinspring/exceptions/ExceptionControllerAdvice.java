package com.zustik.erpappinspring.exceptions;

import org.apache.coyote.Response;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {
//    Spring plugne do parametru e instanci te vytvorene except.. mohu pak pristoupit k jejim detailum
    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity<ErrorDetails> invoiceNotFoundExceptionHanler(InvoiceNotFoundException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMsg("Inovice not found.");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> generalExceptionHanler(Exception e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMsg("Internal server error: " + e.getCause());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorDetails);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ErrorDetails> emptyResultDataAccessExceptionHandler(EmptyResultDataAccessException e) {
        ErrorDetails errorDetails = new ErrorDetails();
        errorDetails.setMsg("No entity with this ID exists in DB");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDetails);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> emptyResultDataAccessExceptionHandler(DataIntegrityViolationException e) {
        ErrorDetails errorDetails = new ErrorDetails("Related entities do not have to cascade delete.",
                e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetails);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDetails> emptyResultDataAccessExceptionHandler(ConstraintViolationException e) {
        ErrorDetails errorDetails = new ErrorDetails("required field is null when it should not be (based on database schema constraints)",
                e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetails);
    }

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ErrorDetails> emptyResultDataAccessExceptionHandler(OptimisticLockingFailureException e) {
        ErrorDetails errorDetails = new ErrorDetails("there's a conflict during the update. This can happen when another" +
                " transaction has updated the same entity after it was loaded but before it was saved",
                e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDetails);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> invalidClientInputExceptionHandler (MethodArgumentNotValidException e) {
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(),
                error.getDefaultMessage())
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMap);
    }
}
