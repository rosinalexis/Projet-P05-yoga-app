package com.openclassrooms.starterjwt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
public class SessionControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private UserRepository userRepository;

    private Session session;

    private Teacher teacher;

    private User user;

    @BeforeEach
    void setUp() {
        sessionRepository.deleteAll();
        teacherRepository.deleteAll();
        userRepository.deleteAll();
        
        teacher = Teacher.builder()
                .firstName("test_teacher")
                .lastName("test_teacher")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        teacherRepository.save(teacher);

        user = User.builder()
                .lastName("test_lastname")
                .firstName("test_firstname")
                .password("test_password")
                .admin(false)
                .email("email@email.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        session = Session.builder()
                .name("Test Session")
                .date(new Date())
                .teacher(teacher)
                .description("Test Description")
                .build();
    }

    @DisplayName("JUnit test for findById operation")
    @Test
    void givenSessionId_whenFindById_thenReturnSessionDto() throws Exception {
        // given
        sessionRepository.save(session);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/{id}", session.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(session.getId()))
                .andExpect(jsonPath("$.name").value(session.getName()));
    }

    @DisplayName("JUnit test for findById operation - session not found")
    @Test
    void givenInvalidSessionId_whenFindById_thenReturnNotFound() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for findById operation - NumberFormatException")
    @Test
    void givenInvalidSessionIdFormat_whenFindById_thenReturnBadRequest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for findAll operation")
    @Test
    void whenFindAll_thenReturnSessionDtoList() throws Exception {
        // given
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);
        sessionRepository.saveAll(sessions);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(session.getId()))
                .andExpect(jsonPath("$[0].name").value(session.getName()));
    }

    @DisplayName("JUnit test for create session operation")
    @Test
    void givenSessionDto_whenCreate_thenReturnSessionDto() throws Exception {
        // given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test DTO Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(teacher.getId());
        sessionDto.setDescription("Test DTO Description");

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(sessionDto.getName()));
    }

    @DisplayName("JUnit test for create session operation - invalid input")
    @Test
    void givenInvalidSessionDto_whenCreate_thenReturnBadRequest() throws Exception {
        // given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("");

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for update session operation")
    @Test
    void givenSessionDto_whenUpdate_thenReturnUpdatedSessionDto() throws Exception {
        // given
        sessionRepository.save(session);

        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("Test DTO Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(teacher.getId());
        sessionDto.setDescription("Test DTO Description");

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/{id}", session.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(sessionDto.getName()))
                .andExpect(jsonPath("$.description").value(sessionDto.getDescription()));
    }

    @DisplayName("JUnit test for update session operation - invalid input")
    @Test
    void givenInvalidSessionDto_whenUpdate_thenReturnBadRequest() throws Exception {
        // given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("");

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for update session operation - NumberFormatException")
    @Test
    void givenInvalidSessionIdFormat_whenUpdate_thenReturnBadRequest() throws Exception {
        // given
        SessionDto sessionDto = new SessionDto();
        sessionDto.setName("");

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for delete session operation")
    @Test
    void givenSessionId_whenDelete_thenReturnOk() throws Exception {
        // given
        sessionRepository.save(session);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}", session.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for delete session operation - session not found")
    @Test
    void givenInvalidSessionId_whenDelete_thenReturnNotFound() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for delete session operation - NumberFormatException")
    @Test
    void givenInvalidSessionIdFormat_whenDelete_thenReturnBadRequest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}", "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for participate operation")
    @Test
    void givenSessionIdAndUserId_whenParticipate_thenReturnOk() throws Exception {

        // given
        userRepository.save(user);
        sessionRepository.save(session);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/{id}/participate/{userId}", session.getId(), user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for participate operation - NumberFormatException")
    @Test
    void givenInvalidSessionIdFormat_whenParticipate_thenReturnBadRequest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/{id}/participate/{userId}", "invalid", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for participate operation - NumberFormatException for userId")
    @Test
    void givenInvalidUserIdFormat_whenParticipate_thenReturnBadRequest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/{id}/participate/{userId}", 1L, "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for no longer participate operation")
    @Test
    void givenSessionIdAndUserId_whenNoLongerParticipate_thenReturnOk() throws Exception {
        // given
        userRepository.save(user);
        List<User> users = new ArrayList<>();
        users.add(user);
        session.setUsers(users);
        sessionRepository.save(session);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}/participate/{userId}", session.getId(), user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for no longer participate operation - NumberFormatException")
    @Test
    void givenInvalidSessionIdFormat_whenNoLongerParticipate_thenReturnBadRequest() throws Exception {

        //given
        userRepository.save(user);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}/participate/{userId}", "invalid", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for no longer participate operation - NumberFormatException for userId")
    @Test
    void givenInvalidUserIdFormat_whenNoLongerParticipate_thenReturnBadRequest() throws Exception {
        sessionRepository.save(session);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}/participate/{userId}", session.getId(), "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }
}
