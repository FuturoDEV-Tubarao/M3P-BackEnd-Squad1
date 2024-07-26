package br.com.labfoods.config.security.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import br.com.labfoods.config.service.BasicService;

class BasicServiceTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private BasicService basicService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Should_GetEmail_When_RequestWithValidCredentials() {
        // Arrange
        String authorizationHeader = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";
        String expectedEmail = "username";

        when(request.getHeader("Authorization")).thenReturn(authorizationHeader);

        // Act
        String email = basicService.getEmail(request);

        // Assert
        assertEquals(expectedEmail, email);
    }

    @Test
    void Should_GetPassword_When_RequestWithValidCredentials() {
        // Arrange
        String authorizationHeader = "Basic dXNlcm5hbWU6cGFzc3dvcmQ=";
        String expectedPassword = "password";

        when(request.getHeader("Authorization")).thenReturn(authorizationHeader);

        // Act
        String password = basicService.getPassword(request);

        // Assert
        assertEquals(expectedPassword, password);
    }

    @Test
    void Should_CheckPassword_ReturnTrue_When_PasswordsMatch() {
        // Arrange
        String passwordAuth = "password";
        String passwordDB = "$2a$10$aGV7jLzTM.jRd4TNWqWmwOwuAmCQXYlxczVa9w3PO1f9oTfld7mm6";

        when(passwordEncoder.matches(passwordAuth, passwordDB)).thenReturn(true);

        // Act
        boolean result = basicService.checkPassword(passwordAuth, passwordDB);

        // Assert
        assertTrue(result);
    }

    @Test
    void Should_CheckPassword_ReturnFalse_When_PasswordsDoNotMatch() {
        // Arrange
        String passwordAuth = "password";
        String passwordDB = "password";

        when(passwordEncoder.matches(passwordAuth, passwordDB)).thenReturn(false);

        // Act
        boolean result = basicService.checkPassword(passwordAuth, passwordDB);

        // Assert
        assertFalse(result);
    }

    @Test
    void Should_EncodePassword_When_PasswordProvided() {
        // Arrange
        String password = "password";
        String encodedPassword = "$2a$10$5/cViqyzMtObISdXKf.rh.xXnAZKfvd6GHS6jCNEtr76at5K7cOy6";

        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);

        // Act
        String result = basicService.passwordEncode(password);

        // Assert
        assertNotNull(result);
    }
}