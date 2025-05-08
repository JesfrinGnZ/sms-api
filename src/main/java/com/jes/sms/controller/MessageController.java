package com.jes.sms.controller;

import com.jes.sms.dto.SmsRequestDTO;
import com.jes.sms.dto.SmsResponseDTO;
import com.jes.sms.services.SmsSplitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling SMS messages.
 * This controller receives SMS requests and uses the SmsSplitterService to split the message into parts.
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class MessageController {


    private final SmsSplitterService smsSplitterService;


    public MessageController(SmsSplitterService smsSplitterService) {
        this.smsSplitterService = smsSplitterService;
    }

    @PostMapping
    public ResponseEntity<SmsResponseDTO> sendSms(@RequestBody SmsRequestDTO request) {
        List<String> parts = smsSplitterService.splitMessage(request.message());
        parts.forEach(part -> log.info("SMS part: {}", part));
        return ResponseEntity.ok(new SmsResponseDTO(parts));
    }

}
