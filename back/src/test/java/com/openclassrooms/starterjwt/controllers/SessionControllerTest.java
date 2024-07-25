package com.openclassrooms.starterjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.mapper.SessionMapper;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.services.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private SessionMapper sessionMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Session session;
    private SessionDto sessionDto;

    @BeforeEach
    void setUp() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);

        session = Session.builder()
                .id(1L)
                .name("Test Session")
                .date(new Date())
                .teacher(teacher)
                .description("Test Description")
                .build();

        sessionDto = new SessionDto();
        sessionDto.setId(1L);
        sessionDto.setName("Test Session");
        sessionDto.setDate(new Date());
        sessionDto.setTeacher_id(1L);
        sessionDto.setDescription("Test Description");
    }

    @DisplayName("JUnit test for findById operation")
    @Test
    void givenSessionId_whenFindById_thenReturnSessionDto() throws Exception {
        // given
        BDDMockito.given(sessionService.getById(anyLong())).willReturn(session);
        BDDMockito.given(sessionMapper.toDto(any(Session.class))).willReturn(sessionDto);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sessionDto.getId()))
                .andExpect(jsonPath("$.name").value(sessionDto.getName()));
    }

    @DisplayName("JUnit test for findById operation - session not found")
    @Test
    void givenInvalidSessionId_whenFindById_thenReturnNotFound() throws Exception {
        // given
        BDDMockito.given(sessionService.getById(anyLong())).willReturn(null);

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
        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos.add(sessionDto);
        BDDMockito.given(sessionService.findAll()).willReturn(sessions);
        BDDMockito.given(sessionMapper.toDto(sessions)).willReturn(sessionDtos);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/session")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sessionDto.getId()))
                .andExpect(jsonPath("$[0].name").value(sessionDto.getName()));
    }

    @DisplayName("JUnit test for create session operation")
    @Test
    void givenSessionDto_whenCreate_thenReturnSessionDto() throws Exception {
        // given
        BDDMockito.given(sessionService.create(any(Session.class))).willReturn(session);
        BDDMockito.given(sessionMapper.toEntity(any(SessionDto.class))).willReturn(session);
        BDDMockito.given(sessionMapper.toDto(any(Session.class))).willReturn(sessionDto);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sessionDto.getId()))
                .andExpect(jsonPath("$.name").value(sessionDto.getName()));
    }

    @DisplayName("JUnit test for create session operation - invalid input")
    @Test
    void givenInvalidSessionDto_whenCreate_thenReturnBadRequest() throws Exception {
        // given
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
        BDDMockito.given(sessionService.update(anyLong(), any(Session.class))).willReturn(session);
        BDDMockito.given(sessionMapper.toEntity(any(SessionDto.class))).willReturn(session);
        BDDMockito.given(sessionMapper.toDto(any(Session.class))).willReturn(sessionDto);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/session/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sessionDto)))
                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sessionDto.getId()))
                .andExpect(jsonPath("$.name").value(sessionDto.getName()));
    }

    @DisplayName("JUnit test for update session operation - invalid input")
    @Test
    void givenInvalidSessionDto_whenUpdate_thenReturnBadRequest() throws Exception {
        // given
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
        BDDMockito.given(sessionService.getById(anyLong())).willReturn(session);

        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());

        verify(sessionService, times(1)).delete(anyLong());
    }

    @DisplayName("JUnit test for delete session operation - session not found")
    @Test
    void givenInvalidSessionId_whenDelete_thenReturnNotFound() throws Exception {
        // given
        BDDMockito.given(sessionService.getById(anyLong())).willReturn(null);

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
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/session/{id}/participate/{userId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());

        verify(sessionService, times(1)).participate(anyLong(), anyLong());
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
        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}/participate/{userId}", 1L, 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isOk());

        verify(sessionService, times(1)).noLongerParticipate(anyLong(), anyLong());
    }

    @DisplayName("JUnit test for no longer participate operation - NumberFormatException")
    @Test
    void givenInvalidSessionIdFormat_whenNoLongerParticipate_thenReturnBadRequest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}/participate/{userId}", "invalid", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for no longer participate operation - NumberFormatException for userId")
    @Test
    void givenInvalidUserIdFormat_whenNoLongerParticipate_thenReturnBadRequest() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/session/{id}/participate/{userId}", 1L, "invalid")
                        .contentType(MediaType.APPLICATION_JSON))
                // then
                .andExpect(status().isBadRequest());
    }
}
