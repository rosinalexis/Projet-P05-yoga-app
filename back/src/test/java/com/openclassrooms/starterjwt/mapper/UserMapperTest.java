package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {

        userMapper = Mappers.getMapper(UserMapper.class);

        user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("test_password");
        user.setAdmin(false);

        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setAdmin(false);
        userDto.setPassword("test_password");
    }

    @DisplayName("JUnit test for convert User to UserDto should match expected values")
    @Test
    public void givenUser_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        UserDto expectedDto = new UserDto();
        expectedDto.setId(user.getId());
        expectedDto.setEmail(user.getEmail());
        expectedDto.setFirstName(user.getFirstName());
        expectedDto.setLastName(user.getLastName());
        expectedDto.setPassword(user.getPassword());
        expectedDto.setAdmin(user.isAdmin());

        // When
        UserDto resultDto = userMapper.toDto(user);

        // Then
        assertEquals(expectedDto, resultDto);
    }

    @DisplayName("JUnit test for convert UserList to UserDtoList should match expected values")
    @Test
    public void givenUserList_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        List<User> userList = new ArrayList<>();
        userList.add(user);

        UserDto expectedDto = new UserDto();
        expectedDto.setId(user.getId());
        expectedDto.setEmail(user.getEmail());
        expectedDto.setFirstName(user.getFirstName());
        expectedDto.setLastName(user.getLastName());
        expectedDto.setPassword(user.getPassword());
        expectedDto.setAdmin(user.isAdmin());


        List<UserDto> expectedDtos = new ArrayList<>();
        expectedDtos.add(expectedDto);


        // When
        List<UserDto> resultDtos = userMapper.toDto(userList);

        // Then
        assertEquals(expectedDtos, resultDtos);
    }

    @DisplayName("JUnit test for convert UserDto to User should match expected values")
    @Test
    public void givenUserDto_whenConvertToEntity_thenEntityShouldMatch() {
        // Given
        User expectedUser = new User();
        expectedUser.setId(userDto.getId());
        expectedUser.setEmail(userDto.getEmail());
        expectedUser.setFirstName(userDto.getFirstName());
        expectedUser.setLastName(userDto.getLastName());
        expectedUser.setAdmin(userDto.isAdmin());
        expectedUser.setPassword(userDto.getPassword());

        // When
        User resultUser = userMapper.toEntity(userDto);

        // Then
        assertEquals(expectedUser, resultUser);
    }

    @DisplayName("JUnit test for convert UserDtoList to UserList should match expected values")
    @Test
    public void givenUserDtoList_whenConvertToEntity_thenEntityListShouldMatch() {
        // Given
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(userDto);

        User expectedUser = new User();
        expectedUser.setId(userDto.getId());
        expectedUser.setEmail(userDto.getEmail());
        expectedUser.setFirstName(userDto.getFirstName());
        expectedUser.setLastName(userDto.getLastName());
        expectedUser.setAdmin(userDto.isAdmin());
        expectedUser.setPassword(userDto.getPassword());

        List<User> expectedUserList = new ArrayList<>();
        expectedUserList.add(expectedUser);

        // When
        List<User> resultUsers = userMapper.toEntity(userDtoList);

        // Then
        assertEquals(expectedUserList, resultUsers);
    }
}