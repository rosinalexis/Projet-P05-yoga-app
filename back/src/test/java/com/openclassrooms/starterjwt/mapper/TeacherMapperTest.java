package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {

    private TeacherMapper teacherMapper;

    private Teacher teacher;
    private TeacherDto teacherDto;

    @BeforeEach
    public void setUp() {

        teacherMapper = Mappers.getMapper(TeacherMapper.class);

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setLastName("teacher_lastname_test");
        teacher.setFirstName("teacher_firstname_test");
        teacher.setCreatedAt(LocalDateTime.now());
        teacher.setUpdatedAt(LocalDateTime.now());

        teacherDto = new TeacherDto();
        teacherDto.setId(1L);
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setCreatedAt(teacher.getCreatedAt());
        teacherDto.setUpdatedAt(teacher.getUpdatedAt());
    }

    @DisplayName("JUnit test for convert Teacher to TeacherDto should match expected values")
    @Test
    public void givenTeacher_whenConvertToDto_thenDtoShouldMatch() {
        // Given
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

    @DisplayName("JUnit test for convert TeacherList to TeacherDtoList should match expected values")
    @Test
    public void givenTeacherList_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);


        TeacherDto expectedDto = new TeacherDto();
        expectedDto.setId(teacher.getId());
        expectedDto.setLastName(teacher.getLastName());
        expectedDto.setFirstName(teacher.getFirstName());
        expectedDto.setCreatedAt(teacher.getCreatedAt());
        expectedDto.setUpdatedAt(teacher.getUpdatedAt());

        List<TeacherDto> expectedDtoList = new ArrayList<>();
        expectedDtoList.add(expectedDto);

        // When
        List<TeacherDto> resultDto = teacherMapper.toDto(teacherList);

        // Then
        assertEquals(expectedDtoList.get(0), resultDto.get(0));
    }

    @DisplayName("JUnit test for convert TeacherDto to Teacher should match expected values")
    @Test
    public void givenTeacherDto_whenConvertToEntity_thenEntityShouldMatch() {
        // Given
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


    @DisplayName("JUnit test for convert TeacherDtoList to TeacherList should match expected values")
    @Test
    public void givenTeacherDtoList_whenConvertToEntity_thenEntityShouldMatch() {
        // Given
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        teacherDtoList.add(teacherDto);

        Teacher expectedTeacher = new Teacher();
        expectedTeacher.setId(teacherDto.getId());
        expectedTeacher.setLastName(teacherDto.getLastName());
        expectedTeacher.setFirstName(teacherDto.getFirstName());
        expectedTeacher.setCreatedAt(teacherDto.getCreatedAt());
        expectedTeacher.setUpdatedAt(teacherDto.getUpdatedAt());

        List<Teacher> expectedTeacherList = new ArrayList<>();
        expectedTeacherList.add(expectedTeacher);

        // When
        List<Teacher> resultTeachers = teacherMapper.toEntity(teacherDtoList);

        // Then
        assertEquals(expectedTeacherList, resultTeachers);
    }
}
