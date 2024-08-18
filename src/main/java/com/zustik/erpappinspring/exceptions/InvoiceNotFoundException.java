package com.zustik.erpappinspring.exceptions;

import org.springframework.web.bind.annotation.RestControllerAdvice;

public class InvoiceNotFoundException extends  RuntimeException{

    // Default constructor
    public InvoiceNotFoundException() {
        super();
    }

    // Constructor that accepts a custom error message
    public InvoiceNotFoundException(String message) {
        super(message);
    }

    // Constructor that accepts a custom error message and a cause
    public InvoiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor that accepts a cause
    public InvoiceNotFoundException(Throwable cause) {
        super(cause);
    }


}
