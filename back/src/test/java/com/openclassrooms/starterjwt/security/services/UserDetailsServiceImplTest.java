package com.openclassrooms.starterjwt.security.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .password("testPassword")
                .build();
    }

    @DisplayName("JUnit test for given a valid username when loadUserByUsername is called then it should return UserDetails")
    @Test
    void givenValidUsername_whenLoadUserByUsername_thenReturnUserDetails() {
        // given
        BDDMockito.given(userRepository.findByEmail(user.getEmail())).willReturn(Optional.of(user));

        // when
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

        // then
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(user.getEmail());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
        Assertions.assertThat(((UserDetailsImpl) userDetails).getId()).isEqualTo(user.getId());
        assertThat(((UserDetailsImpl) userDetails).getFirstName()).isEqualTo(user.getFirstName());
        assertThat(((UserDetailsImpl) userDetails).getLastName()).isEqualTo(user.getLastName());
    }

    @DisplayName("JUnit test for given an invalid username when loadUserByUsername is called then it should throw UsernameNotFoundException")
    @Test
    void givenInvalidUsername_whenLoadUserByUsername_thenThrowUsernameNotFoundException() {
        // given
        String invalidEmail = "invalid@test.com";
        BDDMockito.given(userRepository.findByEmail(invalidEmail)).willReturn(Optional.empty());

        // when & then
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(invalidEmail));
    }
}
