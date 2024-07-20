package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @InjectMocks
    private TeacherService teacherService;

    @Mock
    private TeacherRepository teacherRepository;

    private Teacher teacher;

    @BeforeEach
    void setUp() {

        teacher = Teacher.builder()
                .id(1L)
                .firstName("test_first_name")
                .lastName("test_last_name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("JUnit test for findAll operation when teacher repository returns a list of teachers")
    @Test
    void givenTeacherRepository_whenFindAll_thenReturnListOfTeachers() {
        // given
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);

        BDDMockito.given(teacherRepository.findAll()).willReturn(teachers);

        // when
        List<Teacher> teacherList = teacherService.findAll();

        // then
        assertThat(teacherList).hasSize(1);
        assertThat(teacherList).isNotNull();
        assertThat(teacherList.get(0).getLastName()).isEqualTo(teacher.getLastName());
    }

    @DisplayName("JUnit test for findById operation when teacher repository returns a teacher")
    @Test
    void givenTeacherRepository_whenFindById_thenReturnTeacher() {
        // given
        BDDMockito.given(teacherRepository.findById(anyLong())).willReturn(Optional.of(teacher));

        // when
        Teacher result = teacherService.findById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLastName()).isEqualTo(teacher.getLastName());
    }

    @DisplayName("JUnit test for findById operation when teacher repository returns no teacher")
    @Test
    void givenTeacherRepository_whenFindByIdNotFound_thenReturnNull() {
        // given
        BDDMockito.given(teacherRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Teacher result = teacherService.findById(1L);

        // then
        assertThat(result).isNull();
    }
}