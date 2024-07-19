package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.mapper.TeacherMapper;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @MockBean
    private TeacherMapper teacherMapper;


    @Autowired
    private ObjectMapper objectMapper;

    private Teacher teacher;
    private TeacherDto teacherDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        teacher = Teacher.builder()
                .id(1L)
                .firstName("teacher_firstname")
                .lastName("teacher_lastname")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setFirstName("teacher_firstname");
        teacherDto.setLastName("teacher_lastname");
        teacherDto.setCreatedAt(LocalDateTime.now());
        teacherDto.setUpdatedAt(LocalDateTime.now());
    }

    @DisplayName("JUnit test for findById operation when teacher is found")
    @Test
    void givenTeacherId_whenFindById_thenReturnTeacherDto() throws Exception {
        // Given
        given(teacherService.findById(anyLong())).willReturn(teacher);
        given(teacherMapper.toDto(any(Teacher.class))).willReturn(teacherDto);

        // When & Then
        mockMvc.perform(get("/api/teacher/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(teacherDto.getId()))
                .andExpect(jsonPath("$.firstName").value(teacherDto.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(teacherDto.getLastName()));
    }

    @DisplayName("JUnit test for findById operation when teacher is not found")
    @Test
    void givenInvalidTeacherId_whenFindById_thenReturnNotFound() throws Exception {
        // Given
        given(teacherService.findById(anyLong())).willReturn(null);

        // When & Then
        mockMvc.perform(get("/api/teacher/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for findById operation when id is invalid")
    @Test
    void givenInvalidId_whenFindById_thenReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/teacher/{id}", "invalid_id") // Provide an invalid ID format
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for findAll operation")
    @Test
    void whenFindAll_thenReturnTeacherDtoList() throws Exception {
        // Given
        Teacher anotherTeacher = Teacher.builder()
                .id(2L)
                .firstName("another_firstname")
                .lastName("another_lastname")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        TeacherDto anotherTeacherDto = new TeacherDto();
        anotherTeacherDto.setId(2L);
        anotherTeacherDto.setFirstName("another_firstname");
        anotherTeacherDto.setLastName("another_lastname");
        anotherTeacherDto.setCreatedAt(LocalDateTime.now());
        anotherTeacherDto.setUpdatedAt(LocalDateTime.now());

        List<Teacher> teachers = Arrays.asList(teacher, anotherTeacher);
        List<TeacherDto> teacherDtos = Arrays.asList(teacherDto, anotherTeacherDto);

        given(teacherService.findAll()).willReturn(teachers);
        given(teacherMapper.toDto(teachers)).willReturn(teacherDtos);

        // When & Then
        mockMvc.perform(get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(teacherDto.getId()))
                .andExpect(jsonPath("$[0].firstName").value(teacherDto.getFirstName()))
                .andExpect(jsonPath("$[0].lastName").value(teacherDto.getLastName()))
                .andExpect(jsonPath("$[1].id").value(anotherTeacherDto.getId()))
                .andExpect(jsonPath("$[1].firstName").value(anotherTeacherDto.getFirstName()))
                .andExpect(jsonPath("$[1].lastName").value(anotherTeacherDto.getLastName()));
    }
}
