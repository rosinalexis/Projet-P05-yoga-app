package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JUnit MessageResponse Tests")
class MessageResponseTest {

    @Test
    @DisplayName("JUnit test MessageResponse getters and setters")
    void testGettersAndSetters() {
        // Given
        String message = "Test message";

        // When
        MessageResponse messageResponse = new MessageResponse(message);

        // Then
        assertEquals(message, messageResponse.getMessage(), "Expected message to match");

        // When
        String newMessage = "New test message";
        messageResponse.setMessage(newMessage);

        // Then
        assertEquals(newMessage, messageResponse.getMessage(), "Expected new message to match");
    }
}
