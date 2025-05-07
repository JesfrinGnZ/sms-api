package com.jes.sms.dto;

import java.util.List;

/**
 * Data Transfer Object (DTO) for SMS response.
 * This DTO is used to encapsulate the response data for SMS messages.
 *
 * @param parts A list of message parts that fit within the SMS character limit.
 */
public record SmsResponseDTO(List<String> parts) {}
