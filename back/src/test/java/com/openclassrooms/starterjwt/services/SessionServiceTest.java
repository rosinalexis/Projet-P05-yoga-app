package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.exception.BadRequestException;
import com.openclassrooms.starterjwt.exception.NotFoundException;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private SessionService sessionService;

    private Session session;
    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("test_firstname")
                .lastName("test_lastname")
                .email("test@test.com")
                .password("test_password")
                .admin(false)
                .build();

        session = Session.builder()
                .id(1L)
                .name("Test Session")
                .users(new ArrayList<>())
                .build();
    }

    @DisplayName("JUnit test for create session operation")
    @Test
    void givenSessionObject_whenCreate_thenReturnSavedSession() {
        // given
        BDDMockito.given(sessionRepository.save(any(Session.class))).willReturn(session);

        // when
        Session savedSession = sessionService.create(session);

        // then
        assertThat(savedSession).isNotNull();
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @DisplayName("JUnit test for delete session operation")
    @Test
    void givenSessionId_whenDelete_thenNothing() {
        // given
        Long sessionId = 1L;
        BDDMockito.willDoNothing().given(sessionRepository).deleteById(sessionId);

        // when
        sessionService.delete(sessionId);

        // then
        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @DisplayName("JUnit test for find all sessions operation")
    @Test
    void whenFindAll_thenReturnSessionList() {
        // given
        List<Session> sessions = new ArrayList<>();
        sessions.add(session);

        BDDMockito.given(sessionRepository.findAll()).willReturn(sessions);

        // when
        List<Session> sessionList = sessionService.findAll();

        // then
        assertThat(sessionList).isNotNull();
        assertThat(sessionList.size()).isEqualTo(1);
    }

    @DisplayName("JUnit test for get session by id operation")
    @Test
    void givenSessionId_whenGetById_thenReturnSession() {
        // given
        BDDMockito.given(sessionRepository.findById(1L)).willReturn(Optional.of(session));

        // when
        Session foundSession = sessionService.getById(1L);

        // then
        assertThat(foundSession).isNotNull();
    }

    @DisplayName("JUnit test for update session operation")
    @Test
    void givenSessionObject_whenUpdate_thenReturnUpdatedSession() {
        // given
        BDDMockito.given(sessionRepository.save(any(Session.class))).willReturn(session);

        // when
        Session updatedSession = sessionService.update(1L, session);

        // then
        assertThat(updatedSession).isNotNull();
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @DisplayName("JUnit test for participate in session operation")
    @Test
    void givenSessionAndUserId_whenParticipate_thenUserAddedToSession() {
        // given
        BDDMockito.given(sessionRepository.findById(1L)).willReturn(Optional.of(session));
        BDDMockito.given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when
        sessionService.participate(1L, 1L);

        // then
        verify(sessionRepository, times(1)).save(session);
    }

    @DisplayName("JUnit test for participate in session when user already participates")
    @Test
    void givenSessionAndUserId_whenParticipateAndUserAlreadyParticipates_thenThrowException() {
        // given
        session.getUsers().add(user);
        BDDMockito.given(sessionRepository.findById(1L)).willReturn(Optional.of(session));
        BDDMockito.given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when & then
        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L));
    }

    @DisplayName("JUnit test for no longer participate in session operation")
    @Test
    void givenSessionAndUserId_whenNoLongerParticipate_thenUserRemovedFromSession() {
        // given
        session.getUsers().add(user);
        BDDMockito.given(sessionRepository.findById(1L)).willReturn(Optional.of(session));

        // when
        sessionService.noLongerParticipate(1L, 1L);

        // then
        verify(sessionRepository, times(1)).save(session);
    }

    @DisplayName("JUnit test for no longer participate in session when user not participates")
    @Test
    void givenSessionAndUserId_whenNoLongerParticipateAndUserNotParticipates_thenThrowException() {
        // given
        BDDMockito.given(sessionRepository.findById(1L)).willReturn(Optional.of(session));

        // when & then
        assertThrows(BadRequestException.class, () -> sessionService.noLongerParticipate(1L, 2L));
    }

    @DisplayName("JUnit test for participate in session with invalid session id")
    @Test
    void givenInvalidSessionId_whenParticipate_thenThrowNotFoundException() {
        // given
        BDDMockito.given(sessionRepository.findById(anyLong())).willReturn(Optional.empty());
        BDDMockito.given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when & then
        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    @DisplayName("JUnit test for participate in session with invalid user id")
    @Test
    void givenInvalidUserId_whenParticipate_thenThrowNotFoundException() {
        // given
        BDDMockito.given(sessionRepository.findById(1L)).willReturn(Optional.of(session));
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> sessionService.participate(1L, 1L));
    }

    @DisplayName("JUnit test for no longer participate in session with invalid session id")
    @Test
    void givenInvalidSessionId_whenNoLongerParticipate_thenThrowNotFoundException() {
        // given
        BDDMockito.given(sessionRepository.findById(anyLong())).willReturn(Optional.empty());

        // when & then
        assertThrows(NotFoundException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }

    @DisplayName("JUnit test for create session with invalid session data")
    @Test
    void givenInvalidSessionObject_whenCreate_thenThrowBadRequestException() {
        // given
        BDDMockito.given(sessionRepository.save(any(Session.class))).willThrow(new NumberFormatException());

        // when & then
        assertThrows(NumberFormatException.class, () -> sessionService.create(session));
    }

    @DisplayName("JUnit test for participate in session with invalid session data")
    @Test
    void givenInvalidSessionData_whenParticipate_thenThrowNumberFormatException() {
        // given
        session.getUsers().add(user);
        BDDMockito.given(sessionRepository.findById(anyLong())).willReturn(Optional.of(session));
        BDDMockito.given(userRepository.findById(anyLong())).willReturn(Optional.of(user));

        // when & then
        assertThrows(BadRequestException.class, () -> sessionService.participate(1L, 1L));
    }

    @DisplayName("JUnit test for no longer participate in session with invalid session data")
    @Test
    void givenInvalidSessionData_whenNoLongerParticipate_thenThrowNumberFormatException() {
        // given
        BDDMockito.given(sessionRepository.findById(anyLong())).willThrow(new NumberFormatException());

        // when & then
        assertThrows(NumberFormatException.class, () -> sessionService.noLongerParticipate(1L, 1L));
    }
}
