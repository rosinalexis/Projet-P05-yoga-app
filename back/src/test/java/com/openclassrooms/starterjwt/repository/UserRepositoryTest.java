package com.openclassrooms.starterjwt.repository;

import com.openclassrooms.starterjwt.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:cleanup.sql")
@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
 

    // Junit test for save user operation
    @Test
    public void givenUserObject_whenSave_thenReturnSavedUser() {

        //given - precondition or setup
        User user = User.builder()
                .firstName("test_firstname")
                .lastName("test_lastname")
                .email("test@test.fr")
                .password("test_password")
                .admin(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // when - action or the behaviour that we are going to test
        User savedUser = userRepository.save(user);

        //then -verify the output
        Assertions.assertThat(savedUser).isNotNull();
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);
        Assertions.assertThat(savedUser.getFirstName()).isEqualTo("test_firstname");
        Assertions.assertThat(savedUser.getLastName()).isEqualTo("test_lastname");
        Assertions.assertThat(savedUser.getEmail()).isEqualTo("test@test.fr");
        Assertions.assertThat(savedUser.getPassword()).isEqualTo("test_password");
    }

}
