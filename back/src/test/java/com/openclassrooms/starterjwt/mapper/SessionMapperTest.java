package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.TeacherService;
import com.openclassrooms.starterjwt.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionMapperTest {

    private SessionMapper sessionMapper;
    private TeacherService teacherService;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        teacherService = mock(TeacherService.class);
        userService = mock(UserService.class);
        sessionMapper = Mappers.getMapper(SessionMapper.class);
        sessionMapper.teacherService = teacherService;
        sessionMapper.userService = userService;
    }

    private SessionDto createSessionDto() {
        SessionDto sessionDto = new SessionDto();
        sessionDto.setDescription("Session_Test_Description");
        sessionDto.setTeacher_id(1L);
        List<Long> users = new java.util.ArrayList<>();
        users.add(1L);
        users.add(2L);
        sessionDto.setUsers(users);
        sessionDto.setName("Session_Test_Name");
        sessionDto.setDate(new Date());
        return sessionDto;
    }

    private Session createSession() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        User user1 = new User();
        user1.setId(1L);

        User user2 = new User();
        user2.setId(2L);

        Session session = new Session();
        session.setName("Session_Test_Name");
        session.setDate(new Date());
        session.setDescription("Session_Test_Description");
        session.setTeacher(teacher);
        List<User> users = new java.util.ArrayList<>();
        users.add(user1);
        users.add(user2);
        session.setUsers(users);
        return session;
    }

    @DisplayName("Convert SessionDto to Session should map fields correctly")
    @Test
    public void givenSessionDto_whenConvertToEntity_thenEntityShouldMatch() {
        // Given
        SessionDto sessionDto = createSessionDto();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        User user1 = new User();
        user1.setId(1L);
        User user2 = new User();
        user2.setId(2L);

        when(teacherService.findById(1L)).thenReturn(teacher);
        when(userService.findById(1L)).thenReturn(user1);
        when(userService.findById(2L)).thenReturn(user2);

        // When
        Session session = sessionMapper.toEntity(sessionDto);

        // Then
        assertEquals("Session_Test_Name", session.getName());
        assertEquals(sessionDto.getDate().getTime(), session.getDate().getTime());
        assertEquals("Session_Test_Description", session.getDescription());
        assertEquals(teacher, session.getTeacher());
        List<User> expected = new java.util.ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        assertEquals(expected, session.getUsers());
    }

    @DisplayName("Convert Session to SessionDto should map fields correctly")
    @Test
    public void givenSession_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        Session session = createSession();

        // When
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Then
        assertEquals("Session_Test_Name", sessionDto.getName());
        assertEquals(session.getDate().getTime(), sessionDto.getDate().getTime());
        assertEquals("Session_Test_Description", sessionDto.getDescription());
        assertEquals(1L, sessionDto.getTeacher_id());
        List<Long> expected = new java.util.ArrayList<>();
        expected.add(1L);
        expected.add(2L);
        assertEquals(expected, sessionDto.getUsers());
    }
}