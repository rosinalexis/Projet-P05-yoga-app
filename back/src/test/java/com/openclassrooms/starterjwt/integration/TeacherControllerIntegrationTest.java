package com.openclassrooms.starterjwt.integration;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
public class TeacherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacherRepository.deleteAll();

        teacher = Teacher.builder()
                .firstName("teacher_firstname")
                .lastName("teacher_lastname")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("JUnit test for findById operation when teacher is found")
    @Test
    void givenTeacherId_whenFindById_thenReturnTeacherDto() throws Exception {
        // Given
        teacherRepository.save(teacher);

        // When & Then
        mockMvc.perform(get("/api/teacher/{id}", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacher.getId()))
                .andExpect(jsonPath("$.firstName").value(teacher.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(teacher.getLastName()));
    }

    @DisplayName("JUnit test for findById operation when teacher is not found")
    @Test
    void givenInvalidTeacherId_whenFindById_thenReturnNotFound() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/teacher/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for findById operation when id is invalid")
    @Test
    void givenInvalidId_whenFindById_thenReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/teacher/{id}", "invalid_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for findAll operation")
    @Test
    void whenFindAll_thenReturnTeacherDtoList() throws Exception {
        // Given
        Teacher anotherTeacher = Teacher.builder()
                .firstName("another_firstname")
                .lastName("another_lastname")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<Teacher> teachers = Arrays.asList(teacher, anotherTeacher);
        teacherRepository.saveAll(teachers);

        // When & Then
        mockMvc.perform(get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(teacher.getId()))
                .andExpect(jsonPath("$[0].firstName").value(teacher.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(teacher.getLastName()))
                .andExpect(jsonPath("$[1].id").value(anotherTeacher.getId()))
                .andExpect(jsonPath("$[1].firstName").value(anotherTeacher.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(anotherTeacher.getLastName()));
    }
}
