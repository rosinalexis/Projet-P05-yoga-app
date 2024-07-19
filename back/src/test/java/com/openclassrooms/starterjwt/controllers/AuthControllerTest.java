package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserRepository userRepository;

    private User user;
    private UserDetailsImpl userDetails;
    private JwtResponse jwtResponse;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .id(1L)
                .email("test@test.com")
                .firstName("Test")
                .lastName("User")
                .password("test_password")
                .admin(false)
                .build();

        userDetails = UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .admin(user.isAdmin())
                .password(user.getPassword())
                .build();

        jwtResponse = new JwtResponse(
                "fake-jwt-token",
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isAdmin()
        );

    }

    @DisplayName("JUnit test for authenticate user with valid credentials")
    @Test
    public void givenValidCredentials_whenAuthenticateUser_thenReturnJwtResponse() throws Exception {
        // given
        String loginRequestJson = "{ \"email\": \"test@test.com\", \"password\": \"test_password\" }";

        Authentication authentication = Mockito.mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(authentication);
        given(jwtUtils.generateJwtToken(authentication)).willReturn(jwtResponse.getToken());
        given(authentication.getPrincipal()).willReturn(userDetails);
        given(userRepository.findByEmail(user.getEmail())).willReturn(java.util.Optional.of(user));

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequestJson));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(jwtResponse.getToken()))
                .andExpect(jsonPath("$.type").value("Bearer"))
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getEmail()))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(user.getLastName()))
                .andExpect(jsonPath("$.admin").value(user.isAdmin()))
                .andDo(print());
    }
    
    @DisplayName("JUnit test for register user with valid details")
    @Test
    public void givenValidDetails_whenRegisterUser_thenReturnSuccess() throws Exception {
        // given
        String signUpRequestJson = "{ \"email\": \"newuser@test.com\", \"password\": \"123456\", \"firstName\": \"Test_User\", \"lastName\": \"test_mana\" }";

        given(userRepository.existsByEmail("newuser@test.com")).willReturn(false);
        given(userRepository.save(any(User.class))).willReturn(user);

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signUpRequestJson));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"))
                .andDo(print());
    }

    @DisplayName("JUnit test for register user when email is already taken")
    @Test
    public void givenExistingEmail_whenRegisterUser_thenReturnBadRequest() throws Exception {
        // given
        String signUpRequestJson = "{ \"email\": \"test@test.com\", \"password\": \"password\", \"firstName\": \"Test\", \"lastName\": \"User\" }";

        given(userRepository.existsByEmail("test@test.com")).willReturn(true);

        // when
        ResultActions response = mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(signUpRequestJson));

        // then
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"))
                .andDo(print());
    }
}
