package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.services.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserDetails userDetails;

    @MockBean
    private Authentication authentication;

    @MockBean
    private SecurityContext securityContext;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
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

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setFirstName("test_firstname");
        userDto.setLastName("test_lastname");
        userDto.setEmail("test@test.fr");
        userDto.setPassword("test_password");
        userDto.setAdmin(false);
        userDto.setCreatedAt(LocalDateTime.now());
        userDto.setUpdatedAt(LocalDateTime.now());

        SecurityContextHolder.setContext(securityContext);
    }

    @DisplayName("JUnit test for find a user by id operation")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserDto() throws Exception {
        // given
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(user);
        given(userMapper.toDto(user)).willReturn(userDto);

        // when
        ResultActions response = mockMvc.perform(get("/api/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.email", CoreMatchers.is(user.getEmail())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(user.getLastName())))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(user.getFirstName())))
                .andExpect(jsonPath("$.admin", CoreMatchers.is(user.isAdmin())));
    }

    @DisplayName("JUnit test for find a user by id but not found operation")
    @Test
    public void givenUserId_whenGetUserById_thenReturnEmpty() throws Exception {
        // given
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(null);

        // when
        ResultActions response = mockMvc.perform(get("/api/user/{id}", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("JUnit test for find a user with invalid id operation")
    @Test
    public void givenInvalidUserId_whenGetUserById_thenReturn400() throws Exception {
        // given
        String invalidUserId = "a";

        // when
        ResultActions response = mockMvc.perform(get("/api/user/{id}", invalidUserId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("JUnit test for delete a user with id operation")
    @Test
    public void givenUserId_whenDeleteUser_thenReturn200() throws Exception {
        // given
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(user);
        willDoNothing().given(userService).delete(userId);
        given(userDetails.getUsername()).willReturn(user.getEmail());
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(userDetails);

        // when
        ResultActions response = mockMvc.perform(delete("/api/user/{id}", userId));

        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("JUnit test for delete a user with id but unauthorized operation")
    @Test
    public void givenUserId_whenDeleteUser_thenReturn401() throws Exception {
        // given
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(user);
        willDoNothing().given(userService).delete(userId);
        given(userDetails.getUsername()).willReturn(null);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(userDetails);

        // when
        ResultActions response = mockMvc.perform(delete("/api/user/{id}", userId));

        // then
        response.andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @DisplayName("JUnit test for delete not found user operation")
    @Test
    public void givenUserId_whenDeleteUser_thenReturn404() throws Exception {
        // given
        Long userId = 1L;
        given(userService.findById(userId)).willReturn(null);
        willDoNothing().given(userService).delete(userId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/user/{id}", userId));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("JUnit test for delete user with invalid id operation")
    @Test
    public void givenInvalidUserId_whenDeleteUser_thenReturn400() throws Exception {
        // given
        String invalidUserId = "a";

        // when
        ResultActions response = mockMvc.perform(delete("/api/user/{id}", invalidUserId));

        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }
}