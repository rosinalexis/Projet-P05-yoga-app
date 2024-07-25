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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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

    @DisplayName("Convert SessionDtoList to SessionList should map fields correctly")
    @Test
    public void givenSessionDtoList_whenConvertToEntity_thenEntityShouldMatch() {
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

        List<SessionDto> sessionDtoList = new ArrayList<>();
        sessionDtoList.add(sessionDto);

        // When
        List<Session> session = sessionMapper.toEntity(sessionDtoList);

        // Then
        assertEquals("Session_Test_Name", session.get(0).getName());
        assertEquals(sessionDto.getDate().getTime(), session.get(0).getDate().getTime());
        assertEquals("Session_Test_Description", session.get(0).getDescription());
        assertEquals(teacher, session.get(0).getTeacher());
        List<User> expected = new java.util.ArrayList<>();
        expected.add(user1);
        expected.add(user2);
        assertEquals(expected, session.get(0).getUsers());
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

    @DisplayName("Convert SessionList to SessionDtoList should map fields correctly")
    @Test
    public void givenSessionList_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        Session session = createSession();
        List<Session> sessionList = new ArrayList<>();
        sessionList.add(session);

        // When
        List<SessionDto> sessionDto = sessionMapper.toDto(sessionList);

        // Then
        assertEquals("Session_Test_Name", sessionDto.get(0).getName());
        assertEquals(session.getDate().getTime(), sessionDto.get(0).getDate().getTime());
        assertEquals("Session_Test_Description", sessionDto.get(0).getDescription());
        assertEquals(1L, sessionDto.get(0).getTeacher_id());
        List<Long> expected = new java.util.ArrayList<>();
        expected.add(1L);
        expected.add(2L);
        assertEquals(expected, sessionDto.get(0).getUsers());
    }

    @Test
    @DisplayName("Test toEntity with null dto list")
    void givenNullDtoList_whenToEntity_thenReturnNull() {
        // Given

        // When
        List<Session> result = sessionMapper.toEntity((List<SessionDto>) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test toDto with null entity list")
    void givenNullEntityList_whenToDto_thenReturnNull() {
        // Given

        // When
        List<SessionDto> result = sessionMapper.toDto((List<Session>) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test toEntity with null SessionDto")
    void givenNullSessionDto_whenToEntity_thenReturnNull() {
        // Given

        // When
        Session result = sessionMapper.toEntity((SessionDto) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test toDto with null Session")
    void givenNullSession_whenToDto_thenReturnNull() {
        // Given

        // When
        SessionDto result = sessionMapper.toDto((Session) null);

        // Then
        assertNull(result);
    }

    @Test
    @DisplayName("Test toEntity with SessionDto having null fields")
    void givenSessionDtoWithNullFields_whenToEntity_thenHandleNulls() {
        // Given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName(null);
        sessionDto.setDate(null);
        sessionDto.setDescription(null);
        sessionDto.setTeacher_id(null);
        sessionDto.setUsers(null);

        when(teacherService.findById(anyLong())).thenReturn(null);
        when(userService.findById(anyLong())).thenReturn(null);

        // When
        Session result = sessionMapper.toEntity(sessionDto);

        // Then
        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getDate());
        assertNull(result.getDescription());
        assertNull(result.getTeacher());
        assertTrue(result.getUsers().isEmpty());
    }

    @Test
    @DisplayName("Test toDto with Session having null fields")
    void givenSessionWithNullFields_whenToDto_thenHandleNulls() {
        // Given
        Session session = Session.builder()
                .id(1L)
                .name(null)
                .date(null)
                .description(null)
                .teacher(null)
                .users(null)
                .build();

        // When
        SessionDto result = sessionMapper.toDto(session);

        // Then
        assertNotNull(result);
        assertNull(result.getName());
        assertNull(result.getDate());
        assertNull(result.getDescription());
        assertNull(result.getTeacher_id());
        assertTrue(result.getUsers().isEmpty());
    }
}