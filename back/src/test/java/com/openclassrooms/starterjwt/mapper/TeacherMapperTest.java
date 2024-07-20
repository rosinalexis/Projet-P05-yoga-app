package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    @BeforeEach
    public void setUp() {
        teacherMapper = Mappers.getMapper(TeacherMapper.class);
    }

    @DisplayName("JUnit test for convert Teacher to TeacherDto should match expected values")
    @Test
    public void givenTeacher_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("Doe");
        teacher.setFirstName("John");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        TeacherDto expectedDto = new TeacherDto();
        expectedDto.setId(teacher.getId());
        expectedDto.setLastName(teacher.getLastName());
        expectedDto.setFirstName(teacher.getFirstName());
        expectedDto.setCreatedAt(teacher.getCreatedAt());
        expectedDto.setUpdatedAt(teacher.getUpdatedAt());

        // When
        TeacherDto resultDto = teacherMapper.toDto(teacher);

        // Then
        assertEquals(expectedDto, resultDto);
    }

    @DisplayName("JUnit test for convert TeacherDto to Teacher should match expected values")
    @Test
    public void givenTeacherDto_whenConvertToEntity_thenEntityShouldMatch() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        TeacherDto teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName("Doe");
        teacherDto.setFirstName("John");
        teacherDto.setCreatedAt(now);
        teacherDto.setUpdatedAt(now);

        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setId(teacherDto.getId());
        expectedTeacher.setLastName(teacherDto.getLastName());
        expectedTeacher.setFirstName(teacherDto.getFirstName());
        expectedTeacher.setCreatedAt(teacherDto.getCreatedAt());
        expectedTeacher.setUpdatedAt(teacherDto.getUpdatedAt());

        // When
        Teacher resultTeacher = teacherMapper.toEntity(teacherDto);

        // Then
        assertEquals(expectedTeacher, resultTeacher);
    }
}
