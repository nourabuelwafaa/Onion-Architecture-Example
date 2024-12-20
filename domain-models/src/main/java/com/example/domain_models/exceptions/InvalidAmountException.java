package com.example.domain_models.exceptions;

public class InvalidAmountException extends DomainException {
    
    public InvalidAmountException() {
        super("");
    }

    public InvalidAmountException(String message) {
        super(message);
    }
}