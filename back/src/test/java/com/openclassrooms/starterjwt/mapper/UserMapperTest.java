package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    public void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @DisplayName("Convert User to UserDto should match expected values")
    @Test
    public void givenUser_whenConvertToDto_thenDtoShouldMatch() {
        // Given
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("test_password");
        user.setAdmin(false);

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

    @DisplayName("Convert UserDto to User should match expected values")
    @Test
    public void givenUserDto_whenConvertToEntity_thenEntityShouldMatch() {
        // Given
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@test.com");
        userDto.setFirstName("Test");
        userDto.setLastName("User");
        userDto.setAdmin(false);
        userDto.setPassword("test_password");

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
}