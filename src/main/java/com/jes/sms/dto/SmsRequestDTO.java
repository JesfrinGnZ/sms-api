package com.jes.sms.dto;

/**
 * Data Transfer Object (DTO) for SMS request.
 * This DTO is used to encapsulate the request data for sending an SMS message.
 *
 * @param message The message to be sent via SMS.
 */
public record SmsRequestDTO(String message) {}
