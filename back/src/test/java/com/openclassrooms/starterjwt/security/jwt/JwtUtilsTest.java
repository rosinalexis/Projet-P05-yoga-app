package com.openclassrooms.starterjwt.security.jwt;

import com.openclassrooms.starterjwt.security.services.UserDetailsImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    @InjectMocks
    private JwtUtils jwtUtils;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("JUnit test for validateJwtToken method with expired token")
    @Test
    void givenExpiredToken_whenValidateJwtToken_thenReturnFalse() {
        // given
        String expiredToken = Jwts.builder()
                .setSubject("testUser")
                .setIssuedAt(new Date(System.currentTimeMillis() - 10000))
                .setExpiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(SignatureAlgorithm.HS512, "testSecret")
                .compact();

        // when
        boolean isValid = jwtUtils.validateJwtToken(expiredToken);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("JUnit test for validateJwtToken method with malformed token")
    @Test
    void givenMalformedToken_whenValidateJwtToken_thenReturnFalse() {
        // given
        String malformedToken = "malformed.token";

        // when
        boolean isValid = jwtUtils.validateJwtToken(malformedToken);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("JUnit test for validateJwtToken method with unsupported token")
    @Test
    void givenUnsupportedToken_whenValidateJwtToken_thenReturnFalse() {
        // given
        String unsupportedToken = "unsupported.token";

        // when
        boolean isValid = jwtUtils.validateJwtToken(unsupportedToken);

        // then
        assertThat(isValid).isFalse();
    }

    @DisplayName("JUnit test for validateJwtToken method with empty claims")
    @Test
    void givenTokenWithEmptyClaims_whenValidateJwtToken_thenReturnFalse() {
        // given
        String emptyClaimsToken = "empty.claims.token";

        // when
        boolean isValid = jwtUtils.validateJwtToken(emptyClaimsToken);

        // then
        assertThat(isValid).isFalse();
    }
}
