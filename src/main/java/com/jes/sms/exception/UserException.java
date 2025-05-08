package com.jes.sms.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class for user-related errors.
 * This class extends DomainException to provide a specific exception
 * for user-related issues in the application.
 */
public class UserException extends DomainException {
    public UserException(String message, HttpStatus status) {
        super(message, status);
    }
}
