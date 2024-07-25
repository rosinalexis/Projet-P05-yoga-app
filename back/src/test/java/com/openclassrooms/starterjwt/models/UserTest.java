package com.openclassrooms.starterjwt.models;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final Validator validator;

    public UserTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    @DisplayName("JUnit test for User creation using Builder pattern")
    void givenUserBuilder_whenBuild_thenCreateUser() {
        // Given
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When

        // Then
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isAdmin());
        Assertions.assertThat(user.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        Assertions.assertThat(user.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("JUnit test for User validation")
    void givenInvalidUser_whenValidate_thenViolations() {
        // Given
        User user = User.builder()
                .email("invalid-email")
                .lastName("")
                .firstName("")
                .password("")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("JUnit test for equals and hashCode methods")
    void givenUsersWithSameId_whenEquals_thenEqual() {
        // Given
        User user1 = User.builder()
                .id(1L)
                .email("test1@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        User user2 = User.builder()
                .id(1L)
                .email("test2@example.com")
                .lastName("Smith")
                .firstName("Jane")
                .password("password456")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        boolean areEqual = user1.equals(user2);
        int hashCode1 = user1.hashCode();
        int hashCode2 = user2.hashCode();

        // Then
        assertTrue(areEqual);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("JUnit test for toString method")
    void givenUser_whenToString_thenCorrectFormat() {
        // Given
        User user = User.builder()
                .id(1L)
                .email("test@example.com")
                .lastName("Doe")
                .firstName("John")
                .password("password123")
                .admin(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        String toString = user.toString();

        // Then
        String expectedToString = "User(id=1, email=test@example.com, lastName=Doe, firstName=John, password=password123, admin=true, createdAt=" + user.getCreatedAt() + ", updatedAt=" + user.getUpdatedAt() + ")";
        assertEquals(expectedToString, toString);
    }
}
