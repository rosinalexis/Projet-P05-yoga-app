package com.openclassrooms.starterjwt.security.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDetailsImplTest {

    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        userDetails = UserDetailsImpl.builder()
                .id(1L)
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .admin(true)
                .password("testPassword")
                .build();
    }

    @DisplayName("Given a UserDetailsImpl object, when getAuthorities is called, then it should return an empty collection")
    @Test
    void givenUserDetailsImpl_whenGetAuthorities_thenReturnEmptyCollection() {
        // when
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        // then
        assertThat(authorities).isNotNull();
        assertThat(authorities).isEmpty();
    }

    @DisplayName("Given a UserDetailsImpl object, when isAccountNonExpired is called, then it should return true")
    @Test
    void givenUserDetailsImpl_whenIsAccountNonExpired_thenReturnTrue() {
        // when
        boolean isAccountNonExpired = userDetails.isAccountNonExpired();

        // then
        assertThat(isAccountNonExpired).isTrue();
    }

    @DisplayName("Given a UserDetailsImpl object, when isAccountNonLocked is called, then it should return true")
    @Test
    void givenUserDetailsImpl_whenIsAccountNonLocked_thenReturnTrue() {
        // when
        boolean isAccountNonLocked = userDetails.isAccountNonLocked();

        // then
        assertThat(isAccountNonLocked).isTrue();
    }

    @DisplayName("Given a UserDetailsImpl object, when isCredentialsNonExpired is called, then it should return true")
    @Test
    void givenUserDetailsImpl_whenIsCredentialsNonExpired_thenReturnTrue() {
        // when
        boolean isCredentialsNonExpired = userDetails.isCredentialsNonExpired();

        // then
        assertThat(isCredentialsNonExpired).isTrue();
    }

    @DisplayName("Given a UserDetailsImpl object, when isEnabled is called, then it should return true")
    @Test
    void givenUserDetailsImpl_whenIsEnabled_thenReturnTrue() {
        // when
        boolean isEnabled = userDetails.isEnabled();

        // then
        assertThat(isEnabled).isTrue();
    }

    @DisplayName("Given two UserDetailsImpl objects with the same ID, when compared, then they should be equal")
    @Test
    void givenUserDetailsWithSameId_whenEquals_thenReturnTrue() {
        // given
        UserDetailsImpl userDetailsSame = UserDetailsImpl.builder()
                .id(1L)
                .username("testUser")
                .firstName("Test")
                .lastName("User")
                .admin(true)
                .password("testPassword")
                .build();

        // when & then
        assertThat(userDetails).isEqualTo(userDetailsSame);
    }

    @DisplayName("Given two UserDetailsImpl objects with different IDs, when compared, then they should not be equal")
    @Test
    void givenUserDetailsWithDifferentId_whenEquals_thenReturnFalse() {
        // given
        UserDetailsImpl userDetailsDifferent = UserDetailsImpl.builder()
                .id(2L)
                .username("differentUser")
                .firstName("Different")
                .lastName("User")
                .admin(false)
                .password("differentPassword")
                .build();

        // when & then
        assertThat(userDetails).isNotEqualTo(userDetailsDifferent);
    }

    @DisplayName("Given two UserDetailsImpl objects with different IDs, when comparing hashCodes, then they should not be equal")
    @Test
    void givenUserDetailsWithDifferentId_whenHashCode_thenReturnDifferentHashCode() {
        // given
        UserDetailsImpl userDetailsDifferent = UserDetailsImpl.builder()
                .id(2L)
                .username("differentUser")
                .firstName("Different")
                .lastName("User")
                .admin(false)
                .password("differentPassword")
                .build();

        // when & then
        assertThat(userDetails.hashCode()).isNotEqualTo(userDetailsDifferent.hashCode());
    }
}
