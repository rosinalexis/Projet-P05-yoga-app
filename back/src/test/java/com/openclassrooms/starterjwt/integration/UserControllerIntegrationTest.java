package com.openclassrooms.starterjwt.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.starterjwt.mapper.UserMapper;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        user = User.builder()
                .lastName("test_lastname")
                .firstName("test_firstname")
                .password("test_password")
                .admin(false)
                .email("email@email.com")
                .build();
    }

    @DisplayName("JUnit test for find a user by id operation")
    @Test
    public void givenUserId_whenGetUserById_thenReturnUserDto() throws Exception {
        // given
        Long userId = 1L;
        userRepository.save(user);

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


}
