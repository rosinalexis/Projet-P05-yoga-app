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

class TeacherTest {

    private final Validator validator;

    public TeacherTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    @DisplayName("JUnit test for Teacher creation using Builder pattern")
    void givenTeacherBuilder_whenBuild_thenCreateTeacher() {
        // Given
        Teacher teacher = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When

        // Then
        assertNotNull(teacher);
        assertEquals(1L, teacher.getId());
        assertEquals("Doe", teacher.getLastName());
        assertEquals("John", teacher.getFirstName());
        Assertions.assertThat(teacher.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
        Assertions.assertThat(teacher.getUpdatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("JUnit test for Teacher validation")
    void givenInvalidTeacher_whenValidate_thenViolations() {
        // Given
        Teacher teacher = Teacher.builder()
                .lastName("")
                .firstName("")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        Set<ConstraintViolation<Teacher>> violations = validator.validate(teacher);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    @DisplayName("JUnit test for equals and hashCode methods")
    void givenTeachersWithSameId_whenEquals_thenEqual() {
        // Given
        Teacher teacher1 = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Teacher teacher2 = Teacher.builder()
                .id(1L)
                .lastName("Smith")
                .firstName("Jane")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        boolean areEqual = teacher1.equals(teacher2);
        int hashCode1 = teacher1.hashCode();
        int hashCode2 = teacher2.hashCode();

        // Then
        assertTrue(areEqual);
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    @DisplayName("JUnit test for toString method")
    void givenTeacher_whenToString_thenCorrectFormat() {
        // Given
        Teacher teacher = Teacher.builder()
                .id(1L)
                .lastName("Doe")
                .firstName("John")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        String toString = teacher.toString();

        // Then
        String expectedToString = "Teacher(id=1, lastName=Doe, firstName=John, createdAt=" + teacher.getCreatedAt() + ", updatedAt=" + teacher.getUpdatedAt() + ")";
        assertEquals(expectedToString, toString);
    }
}
