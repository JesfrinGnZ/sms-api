package com.jes.sms.services;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class SmsSplitterServiceUnitTest {

    private final SmsSplitterServiceImpl smsSplitterService = new SmsSplitterServiceImpl();

    @Test
    void splitMessageSplitsLongMessageIntoMultipleParts() {
        //Arrange
        String message = "Dear user, we inform you that your invoice for the month has been successfully generated and you can view it in the online portal. Remember that your payment deadline is next May 15. If you have any doubts, please contact our customer service center. Thank you for choosing us. This message is important so that you do not lose the early payment discount benefit.";

        //Act
        List<String> parts = smsSplitterService.splitMessage(message);

        //Assert
        assertEquals(3, parts.size());
        assertTrue(parts.get(0).contains("Part 1 of 3"));
        assertTrue(parts.get(1).contains("Part 2 of 3"));
        assertTrue(parts.get(2).contains("Part 3 of 3"));

    }

    @Test
    void splitMessageThrowsExceptionForEmptyMessage() {
        String message = "   ";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> smsSplitterService.splitMessage(message));

        assertEquals("Message cannot be empty", exception.getMessage());
    }

    @Test
    void splitMessageHandlesMessageWithSingleWordExceedingMaxLength() {
        String message = "a".repeat(200);
        List<String> parts = smsSplitterService.splitMessage(message);

        assertEquals(2, parts.size());
        assertTrue(parts.get(0).contains("Part 1 of 2"));
        assertTrue(parts.get(1).contains("Part 2 of 2"));
    }

    @Test
    void splitMessageHandlesMessageWithMultipleSpaces() {
        String message = "This   is   a   test   message.";
        List<String> parts = smsSplitterService.splitMessage(message);

        assertEquals(1, parts.size());
        assertTrue(parts.get(0).contains("Part 1 of 1"));
    }

}
