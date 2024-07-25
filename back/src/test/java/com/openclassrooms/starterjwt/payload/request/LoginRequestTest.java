package com.openclassrooms.starterjwt.payload.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("JUnit Test valid LoginRequest")
    void givenValidLoginRequest_whenValidated_thenNoViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("valid.email@example.com");
        loginRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertTrue(violations.isEmpty(), "Expected no constraint violations");
    }

    @Test
    @DisplayName("JUnit Test blank email")
    void givenBlankEmail_whenValidated_thenViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("");
        loginRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")), "Expected email constraint violation");
    }

    @Test
    @DisplayName("JUnit Test null email")
    void givenNullEmail_whenValidated_thenViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(null);
        loginRequest.setPassword("ValidPassword123");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")), "Expected email constraint violation");
    }

    @Test
    @DisplayName("JUnit Test blank password")
    void givenBlankPassword_whenValidated_thenViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("valid.email@example.com");
        loginRequest.setPassword("");

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")), "Expected password constraint violation");
    }

    @Test
    @DisplayName("JUnit Test null password")
    void givenNullPassword_whenValidated_thenViolations() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("valid.email@example.com");
        loginRequest.setPassword(null);

        // When
        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);

        // Then
        assertFalse(violations.isEmpty(), "Expected constraint violations");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("password")), "Expected password constraint violation");
    }
}
