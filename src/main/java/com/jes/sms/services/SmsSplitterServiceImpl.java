package com.jes.sms.services;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for handling SMS message splitting.
 * This service is responsible for splitting a long message into smaller parts
 * that fit within the SMS character limit.
 */
@Service
public class SmsSplitterServiceImpl implements SmsSplitterService {

    private static final int MAX_LENGTH = 160;


    /**
     * Splits a message into parts that fit within the SMS character limit.
     * Each part will have a suffix indicating its part number and total parts.
     *
     * @param message The message to be split.
     * @return A list of message parts.
     */
    @Override
    public List<String> splitMessage(String message) {

        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be empty");
        }

        List<String> words = List.of(message.split(" "));
        List<String> parts = new ArrayList<>();

        StringBuilder part = new StringBuilder();

        String suffixTemplate = " ... - Part %d of %d";

        int totalEstimatedParts = (int) Math.ceil((double) message.length() / (MAX_LENGTH - suffixTemplate.length() - 6));
        int maxContentLength = MAX_LENGTH - String.format(suffixTemplate, totalEstimatedParts, totalEstimatedParts).length();

        for (String word : words) {
            if (part.length() + word.length() + 1 > maxContentLength) {
                parts.add(part.toString());
                part = new StringBuilder();
            }
            if (!part.isEmpty()) part.append(" ");
            part.append(word);
        }
        if (!part.isEmpty()) {
            parts.add(part.toString());
        }

        // Add suffix
        int totalParts = parts.size();
        for (int i = 0; i < totalParts; i++) {
            String suffix = String.format(" ... - Part %d of %d", i + 1, totalParts);
            parts.set(i, parts.get(i) + suffix);
        }

        return parts;
    }
}
