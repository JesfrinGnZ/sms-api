package com.jes.sms.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

/**
 * Custom exception class for domain-related errors.
 * This class extends RuntimeException to provide unchecked exceptions
 * that can be thrown during the execution of the application.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DomainException extends RuntimeException {

  protected final HttpStatus httpStatus;

  public DomainException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
}
