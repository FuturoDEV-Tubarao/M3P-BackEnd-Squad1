package br.com.labfoods.config.security.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import br.com.labfoods.config.service.JwtService;
import java.util.Date;
import java.util.Calendar;

class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    void Should_ReturnFalse_When_TokenNotExpired() {
         // Arrange
         String email = "email@labfoods.com.br";
         String token = jwtService.generateToken(email);

         // Act
         Boolean result = jwtService.isTokenExpired(token);
 
         // Assert
         assertFalse(result);
    }

    @Test
    void Should_ReturnUsername_When_ExtractingUsernameFromToken() {
        // Arrange
        String email = "email@labfoods.com.br";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbEBsYWJmb29kcy5jb20uYnIiLCJpYXQiOjE3MjE5NTUwMzYsImV4cCI6MTcyMTk1NTkzNn0.HYrOrHr8rQGLtEHrHH3GouYPBayZj2ehBJWQET0im3I";

        // Act
        String result = jwtService.extractUsername(token);

        // Assert
        assertEquals(email, result);
    }
}