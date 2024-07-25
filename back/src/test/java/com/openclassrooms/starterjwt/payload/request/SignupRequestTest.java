package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("JUnit Test valid SignupRequest")
    void givenValidSignupRequest_whenValidated_thenNoViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    @DisplayName("JUnit Test blank email")
    void givenBlankEmail_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("");
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")), "Expected email constraint violation");
    }

    @Test
    @DisplayName("JUnit Test blank first name")
    void givenBlankFirstName_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName("");
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")), "Expected first name constraint violation");
    }

    @Test
    @DisplayName("JUnit Test blank last name")
    void givenBlankLastName_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName("");
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")), "Expected last name constraint violation");
    }

    @Test
    @DisplayName("JUnit Test blank password")
    void givenBlankPassword_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword("");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")), "Expected password constraint violation");
    }

    @Test
    @DisplayName("JUnit Test null email")
    void givenNullEmail_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(null);
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")), "Expected email constraint violation");
    }

    @Test
    @DisplayName("JUnit Test null first name")
    void givenNullFirstName_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName(null);
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("firstName")), "Expected first name constraint violation");
    }

    @Test
    @DisplayName("JUnit Test null last name")
    void givenNullLastName_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName(null);
        signupRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("lastName")), "Expected last name constraint violation");
    }

    @Test
    @DisplayName("JUnit Test null password")
    void givenNullPassword_whenValidated_thenViolations() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail("valid.email@example.com");
        signupRequest.setFirstName("ValidFirstName");
        signupRequest.setLastName("ValidLastName");
        signupRequest.setPassword(null);

        // When
        Set<ConstraintViolation<SignupRequest>> violations = validator.validate(signupRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")), "Expected password constraint violation");
    }

    @Test
    @DisplayName("JUnit Test equals method")
    void testEquals() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("valid.email@example.com");
        request1.setFirstName("FirstName");
        request1.setLastName("LastName");
        request1.setPassword("password");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("valid.email@example.com");
        request2.setFirstName("FirstName");
        request2.setLastName("LastName");
        request2.setPassword("password");

        assertEquals(request1, request2, "Expected requests to be equal");
    }

    @Test
    @DisplayName("JUnit Test hashCode method")
    void testHashCode() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("valid.email@example.com");
        request1.setFirstName("FirstName");
        request1.setLastName("LastName");
        request1.setPassword("password");

        SignupRequest request2 = new SignupRequest();
        request2.setEmail("valid.email@example.com");
        request2.setFirstName("FirstName");
        request2.setLastName("LastName");
        request2.setPassword("password");

        assertEquals(request1.hashCode(), request2.hashCode(), "Expected hash codes to be equal");
    }

    @Test
    @DisplayName("JUnit Test toString method")
    void testToString() {
        SignupRequest request = new SignupRequest();
        request.setEmail("valid.email@example.com");
        request.setFirstName("FirstName");
        request.setLastName("LastName");
        request.setPassword("password");

        String expectedString = "SignupRequest(email=valid.email@example.com, firstName=FirstName, lastName=LastName, password=password)";
        assertEquals(expectedString, request.toString(), "Expected toString output to match");
    }

    @Test
    @DisplayName("JUnit Test canEqual method")
    void testCanEqual() {
        SignupRequest request1 = new SignupRequest();
        request1.setEmail("valid.email@example.com");
        request1.setFirstName("FirstName");
        request1.setLastName("LastName");
        request1.setPassword("password");

        SignupRequest request2 = new SignupRequest();
        assertTrue(request1.canEqual(request2), "Expected canEqual to return true for SignupRequest instances");
    }
}
