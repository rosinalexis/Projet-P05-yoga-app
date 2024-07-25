package com.openclassrooms.starterjwt.payload.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("JUnit JwtResponse Tests")
class JwtResponseTest {

    @Test
    @DisplayName("JUnit test JwtResponse getters and setters")
    void testGettersAndSetters() {
        // Given
        String token = "testToken";
        Long id = 1L;
        String username = "testUsername";
        String firstName = "testFirstName";
        String lastName = "testLastName";
        Boolean admin = true;

        // When
        JwtResponse jwtResponse = new JwtResponse(token, id, username, firstName, lastName, admin);

        // Then
        assertEquals(token, jwtResponse.getToken(), "Expected token to match");
        assertEquals("Bearer", jwtResponse.getType(), "Expected type to be Bearer");
        assertEquals(id, jwtResponse.getId(), "Expected id to match");
        assertEquals(username, jwtResponse.getUsername(), "Expected username to match");
        assertEquals(firstName, jwtResponse.getFirstName(), "Expected firstName to match");
        assertEquals(lastName, jwtResponse.getLastName(), "Expected lastName to match");
        assertEquals(admin, jwtResponse.getAdmin(), "Expected admin to match");
    }
    
}
