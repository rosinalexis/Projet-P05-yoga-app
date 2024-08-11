package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.Session;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:cleanup.sql")
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class SessionRepositoryTest {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private Session session;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = Teacher.builder()
                .firstName("test_first_name")
                .lastName("test_last_name")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        teacherRepository.save(teacher);

        session = Session.builder()
                .name("Test Session")
                .date(new Date())
                .description("Test Description")
                .teacher(teacher)
                .build();
    }

    @DisplayName("JUnit test for save session operation")
    @Test
    void givenSessionObject_whenSave_thenReturnSavedSession() {
        // when
        Session savedSession = sessionRepository.save(session);

        // then
        Assertions.assertThat(savedSession).isNotNull();
        Assertions.assertThat(savedSession.getId()).isNotNull();
        Assertions.assertThat(savedSession.getName()).isEqualTo(session.getName());
        Assertions.assertThat(savedSession.getDate()).isEqualTo(session.getDate());
        Assertions.assertThat(savedSession.getDescription()).isEqualTo(session.getDescription());
    }

    @DisplayName("JUnit test for get all sessions operation")
    @Test
    void givenSessions_whenFindAll_thenListIsReturned() {
        // given
        Session anotherSession = Session.builder()
                .name("Another Session")
                .date(new Date())
                .description("Another Description")
                .teacher(teacher)
                .build();
        sessionRepository.save(session);
        sessionRepository.save(anotherSession);

        // when
        List<Session> sessionList = sessionRepository.findAll();

        // then
        Assertions.assertThat(sessionList).isNotNull();
        Assertions.assertThat(sessionList.size()).isGreaterThanOrEqualTo(2);
    }

    @DisplayName("JUnit test for find Session by id operation")
    @Test
    void givenSessionObject_whenFindById_thenReturnSession() {
        // given
        Session savedSession = sessionRepository.save(session);

        // when
        Session foundSession = sessionRepository.findById(savedSession.getId()).orElse(null);

        // then
        Assertions.assertThat(foundSession).isNotNull();
        Assertions.assertThat(foundSession.getName()).isEqualTo(session.getName());
        Assertions.assertThat(foundSession.getDate()).isEqualTo(session.getDate());
        Assertions.assertThat(foundSession.getDescription()).isEqualTo(session.getDescription());
    }

    @DisplayName("JUnit test for update session operation")
    @Test
    void givenSessionObject_whenUpdateSession_thenReturnUpdatedSession() {
        // given
        Session savedSession = sessionRepository.save(session);

        // when
        savedSession.setName("Updated Session");
        savedSession.setDescription("Updated Description");
        Session updatedSession = sessionRepository.save(savedSession);

        // then
        Assertions.assertThat(updatedSession).isNotNull();
        Assertions.assertThat(updatedSession.getName()).isEqualTo("Updated Session");
        Assertions.assertThat(updatedSession.getDescription()).isEqualTo("Updated Description");
    }

    @DisplayName("JUnit test for delete session operation")
    @Test
    void givenSessionObject_whenDelete_thenRemovedSession() {
        // given
        Session savedSession = sessionRepository.save(session);

        // when
        sessionRepository.delete(savedSession);

        // then
        Optional<Session> foundSession = sessionRepository.findById(savedSession.getId());
        Assertions.assertThat(foundSession).isEmpty();
    }
}