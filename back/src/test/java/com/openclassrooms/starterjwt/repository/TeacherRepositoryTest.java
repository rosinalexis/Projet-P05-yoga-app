package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:cleanup.sql")
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher teacher;

    @BeforeEach
    public void setUp() {
        teacher = Teacher.builder()
                .firstName("test_first_name")
                .lastName("test_last_name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("JUnit test for save teacher operation")
    @Test
    public void givenTeacherObject_whenSave_thenReturnSavedTeacher() {
        // given

        // when
        Teacher savedTeacher = teacherRepository.save(teacher);

        // then
        Assertions.assertThat(savedTeacher).isNotNull();
        Assertions.assertThat(savedTeacher.getFirstName()).isEqualTo(teacher.getFirstName());
        Assertions.assertThat(savedTeacher.getLastName()).isEqualTo(teacher.getLastName());
        Assertions.assertThat(savedTeacher.getCreatedAt()).isEqualTo(teacher.getCreatedAt());
        Assertions.assertThat(savedTeacher.getUpdatedAt()).isEqualTo(teacher.getUpdatedAt());
    }

    @DisplayName("JUnit test for get all teachers operation")
    @Test
    public void givenTeacherList_whenFindAll_thenListIsReturned() {
        // given
        Teacher teacher1 = Teacher.builder()
                .firstName("test1_first_name")
                .lastName("test1_last_name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        teacherRepository.save(teacher);
        teacherRepository.save(teacher1);

        // when
        List<Teacher> teacherList = teacherRepository.findAll();

        // then
        Assertions.assertThat(teacherList).isNotNull();
        Assertions.assertThat(teacherList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for find Teacher by id operation")
    @Test
    public void givenTeacherObject_whenFindById_thenReturnTeacher() {
        // given
        teacherRepository.save(teacher);

        // when
        Teacher teacherDb = teacherRepository.findById(teacher.getId()).get();

        // then
        Assertions.assertThat(teacherDb).isNotNull();
        Assertions.assertThat(teacherDb.getFirstName()).isEqualTo(teacher.getFirstName());
        Assertions.assertThat(teacherDb.getLastName()).isEqualTo(teacher.getLastName());
        Assertions.assertThat(teacherDb.getCreatedAt()).isEqualTo(teacher.getCreatedAt());
        Assertions.assertThat(teacherDb.getUpdatedAt()).isEqualTo(teacher.getUpdatedAt());
    }

    @DisplayName("JUnit test for update teacher operation")
    @Test
    public void givenTeacherObject_whenUpdateTeacher_thenReturnUpdatedTeacher() {
        // given
        teacherRepository.save(teacher);

        // when
        Teacher teacherDb = teacherRepository.findById(teacher.getId()).get();
        teacherDb.setFirstName("test2_first_name");
        teacherDb.setLastName("test2_last_name");

        Teacher updatedTeacher = teacherRepository.save(teacherDb);

        // then
        Assertions.assertThat(updatedTeacher).isNotNull();
        Assertions.assertThat(updatedTeacher.getFirstName()).isEqualTo("test2_first_name");
        Assertions.assertThat(updatedTeacher.getLastName()).isEqualTo("test2_last_name");
    }

    @DisplayName("JUnit test for delete teacher operation")
    @Test
    public void givenTeacherObject_whenDelete_thenRemovedTeacher() {
        // given
        teacherRepository.save(teacher);

        // when
        teacherRepository.delete(teacher);

        // then
        Optional<Teacher> teacherDb = teacherRepository.findById(teacher.getId());
        Assertions.assertThat(teacherDb).isEmpty();
    }
}