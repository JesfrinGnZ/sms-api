package com.jes.sms.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException and returns a 400 Bad Request response.
     *
     * @param ex the exception
     * @return a ResponseEntity with a 400 status and error message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    /**
     * Handles NoHandlerFoundException and returns a 404 Not Found response.
     *
     * @param ex the exception
     * @return a ResponseEntity with a 404 status and error message
     */
    @ExceptionHandler(NoResourceFoundException.class)
    ResponseEntity<String> noOtherEndpointFound(NoResourceFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("""
                        {
                            "message": "'%s' is not a valid path"
                        }
                        """.formatted(request.getRequestURI()));
    }

    /**
     * Handles NoResourceFoundException and returns a 404 Not Found response.
     *
     * @param ex the exception
     * @return a ResponseEntity with a 404 status and error message
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAll(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Unexpected error"));
    }


    /**
     * Handles BadCredentialsException and returns a 404 Not Found response.
     *
     * @param ex the exception
     * @return a ResponseEntity with a 404 status and error message
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "Bad credentials"));
    }


    /**
     * Handles DomainException.
     *
     * @param ex the exception
     * @return a ResponseEntity with status and error message
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, String>> handleDomainException(DomainException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.status(ex.getHttpStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", ex.getMessage()));
    }

}
