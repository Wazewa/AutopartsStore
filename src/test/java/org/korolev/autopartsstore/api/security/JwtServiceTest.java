package org.korolev.autopartsstore.api.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        byte[] keyBytes = "very-secret-key-for-testing-purposes-12345".getBytes();
        String base64Secret = Base64.getEncoder().encodeToString(keyBytes);

        ReflectionTestUtils.setField(jwtService, "secretKey", base64Secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000L * 60 * 60);
    }

    @Test
    void generateToken_shouldCreateValidToken() {
        User userDetails = new
                User(
                "test@test.ru", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String token = jwtService.generateToken(userDetails);

        assertThat(token).isNotBlank();
        assertThat(jwtService.extractEmail(token)).isEqualTo("test@test.ru");
        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();
    }

    @Test
    void isTokenValid_withExpiredToken_shouldThrowException() {
        Map<String, Object> claims = new HashMap<>();
        SecretKey key = Keys.hmacShaKeyFor("very-secret-key-for-testing-purposes-12345".getBytes());
        String expiredToken = Jwts.builder()
                .claims(claims)
                .subject("test@test.ru")
                .issuedAt(new Date(System.currentTimeMillis() - 10000))
                .expiration(new Date(System.currentTimeMillis() - 5000))
                .signWith(key)
                .compact();

        User userDetails = new User(
                "test@test.ru", "password", List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );

        assertThatThrownBy(() -> jwtService.isTokenValid(expiredToken, userDetails))
                .isInstanceOf(ExpiredJwtException.class);
    }
}