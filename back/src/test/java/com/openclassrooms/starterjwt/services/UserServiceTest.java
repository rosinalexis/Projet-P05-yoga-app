package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setup() {
        user = User.builder()
                .id(1L)
                .firstName("test_firstname")
                .lastName("test_lastname")
                .email("test@test.fr")
                .password("test_password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @DisplayName("JUnit test for find user by id operation")
    @Test
    public void givenUserId_whenFindById_thenReturnUserObject() {
        // given - precondition or setup
        BDDMockito.given(userRepository.findById(1L)).willReturn(Optional.of(user));

        // when - action or the behavior that we are going test
        User foundUser = userService.findById(user.getId());

        // then - verify the output
        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(user.getEmail(), foundUser.getEmail());
        Assertions.assertEquals(user.getFirstName(), foundUser.getFirstName());
        Assertions.assertEquals(user.getLastName(), foundUser.getLastName());
        Assertions.assertEquals(user.getPassword(), foundUser.getPassword());
        Assertions.assertEquals(user.isAdmin(), foundUser.isAdmin());
    }

    @DisplayName("JUnit test for delete user by id operation")
    @Test
    public void givenUserId_whenDeleteUser_thenNothing() {
        // given - precondition or setup
        Long userId = 1L;
        BDDMockito.willDoNothing().given(userRepository).deleteById(userId);

        // when - action or the behavior that we are going test
        userService.delete(userId);

        // then - verify the output
        BDDMockito.verify(userRepository, BDDMockito.times(1)).deleteById(userId);
    }
}