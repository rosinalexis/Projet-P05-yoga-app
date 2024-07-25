package com.openclassrooms.starterjwt.models;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    private Validator validator;
    private Teacher teacher;
    private User user;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        teacher = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .build();

        user = User.builder()
                .id(1L)
                .email("user@example.com")
                .lastName("Smith")
                .firstName("Jane")
                .password("password")
                .admin(false)
                .build();
    }

    @Test
    @DisplayName("JUnit test for Session creation using Builder pattern")
    void givenSessionBuilder_whenBuild_thenCreateSession() {
        // Given
        Session session = Session.builder()
                .id(1L)
                .name("Session Name")
                .date(new Date())
                .description("This is a description of the session.")
                .teacher(teacher)
                .users(Collections.singletonList(user))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When

        // Then
        assertNotNull(session);
        assertEquals(1L, session.getId());
        assertEquals("Session Name", session.getName());
        assertNotNull(session.getDate());
        assertEquals("This is a description of the session.", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        assertEquals(1, session.getUsers().size());
        assertTrue(session.getUsers().contains(user));
        Assertions.assertThat(session.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        Assertions.assertThat(session.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("JUnit test for Session validation")
    void givenInvalidSession_whenValidate_thenViolations() {
        // Given
        Session session = Session.builder()
                .name("")
                .date(null)
                .description(null)
                .teacher(null)
                .users(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        Set<ConstraintViolation<Session>> violations = validator.validate(session);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size());
    }

    @Test
    @DisplayName("JUnit test for equals and hashCode methods")
    void givenSessionsWithSameId_whenEquals_thenEqual() {
        // Given
        Session session1 = Session.builder()
                .id(1L)
                .name("Session One")
                .date(new Date())
                .description("Description One")
                .teacher(teacher)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Session session2 = Session.builder()
                .id(1L)
                .name("Session Two")
                .date(new Date())
                .description("Description Two")
                .teacher(teacher)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        boolean areEqual = session1.equals(session2);
        int hashCode1 = session1.hashCode();
        int hashCode2 = session2.hashCode();

        // Then
        assertTrue(areEqual);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("JUnit test for toString method")
    void givenSession_whenToString_thenCorrectFormat() {
        // Given
        Session session = Session.builder()
                .id(1L)
                .name("Session Name")
                .date(new Date())
                .description("This is a description of the session.")
                .teacher(teacher)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        String toString = session.toString();

        // Then
        String expectedToString = "Session(id=1, name=Session Name, date=" + session.getDate() + ", description=This is a description of the session., teacher=" + teacher + ", users=null, createdAt=" + session.getCreatedAt() + ", updatedAt=" + session.getUpdatedAt() + ")";
        assertEquals(expectedToString, toString);
    }
}
