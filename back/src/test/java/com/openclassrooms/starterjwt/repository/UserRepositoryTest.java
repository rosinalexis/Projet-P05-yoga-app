package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:cleanup.sql")
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void setUp() {
        this.user = User.builder()
                .firstName("test_firstname")
                .lastName("test_lastname")
                .email("test@test.fr")
                .password("test_password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // Junit test for save user operation
    @DisplayName("Junit test for save a user operation")
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {

        //given - precondition or setup
        // when - action or the behaviour that we are going to test
        User savedUser = userRepository.save(user);

        //then -verify the output
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo("test_firstname");
        Assertions.assertThat(savedUser.getLastName()).isEqualTo("test_lastname");
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("test@test.fr");
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("test_password");
        Assertions.assertThat(savedUser.isAdmin()).isFalse();
        Assertions.assertThat(savedUser.getCreatedAt()).isNotNull();
        Assertions.assertThat(savedUser.getUpdatedAt()).isNotNull();
    }

    //JUnit test for all users operations
    @DisplayName("Junit test for get all users operation")
    @Test
    public void givenUserList_whenFindAll_thenUserListIsReturned() {

        //given - precondition or setup
        User user1 = User.builder()
                .firstName("test1_firstname")
                .lastName("test1_lastname")
                .email("test1@test.fr")
                .password("test1_password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        userRepository.save(user1);

        // when - action or the behaviour that we are going to test
        List<User> userList = userRepository.findAll();

        //then -verify the output
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    //JUnit test for get user by id operation
    @DisplayName("Junit test for find user by id operation")
    @Test
    public void givenUserObject_whenFindById_thenReturnUser() {
        //given - precondition  or setup
        userRepository.save(user);

        //when - action or the behavior that we are going test
        User userDb = userRepository.findById(user.getId()).get();


        //then -verify the output
        Assertions.assertThat(userDb).isNotNull();
    }

    //JUnit test for find User by Email
    @DisplayName("Junit test for find user by Email operation")
    @Test
    public void givenUserObject_whenFindByEmail_thenReturnUser() {
        //given - precondition  or setup
        userRepository.save(user);

        //when - action or the behavior that we are going test
        User userDb = userRepository.findByEmail(user.getEmail()).get();


        //then -verify the output
        Assertions.assertThat(userDb).isNotNull();
    }

    //JUnit test for check if User/Email already exist
    @DisplayName("Junit test for check is user/email already exist operation")
    @Test
    public void givenUserObject_whenExistByEmail_thenReturnTrue() {
        //given - precondition  or setup
        userRepository.save(user);

        //when - action or the behavior that we are going test
        Boolean isEmailExisted = userRepository.existsByEmail(user.getEmail());

        //then -verify the output
        Assertions.assertThat(isEmailExisted).isTrue();
    }

    //JUnit test for update User operation
    @DisplayName("Junit test for update user operation")
    @Test
    public void givenUserObject_whenUpdateUser_thenReturnUpdatedUser() {
        //given - precondition  or setup
        userRepository.save(user);

        //when - action or the behavior that we are going test
        User saveduser = userRepository.findById(user.getId()).get();
        saveduser.setEmail("test_updated@test.fr");
        saveduser.setPassword("test_updated_password");

        User updatedUser = userRepository.save(saveduser);

        //then -verify the output
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("test_updated@test.fr");
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("test_updated_password");
    }

    //JUnit test for delete User operation
    @DisplayName("Junit test for delete user operation")
    @Test
    public void givenUserObject_whenDelete_thenRemoveUser() {
        //given - precondition  or setup
        userRepository.save(user);

        //when - action or the behavior that we are going test
        userRepository.delete(user);

        //then -verify the output
        Optional<User> userOptional = userRepository.findById(user.getId());
        Assertions.assertThat(userOptional).isEmpty();
    }
}
