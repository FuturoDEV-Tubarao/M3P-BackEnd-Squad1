package br.com.labfoods.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import br.com.labfoods.config.security.CustomUserDetails;
import br.com.labfoods.config.service.JwtService;
import br.com.labfoods.dto.SessionV1Dto;
import br.com.labfoods.model.User;

class AuthServiceTest {

    @Mock
    private JwtService jwtService;
    
    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Should_CreateSession_When_Called() {
        // Arrange
        CustomUserDetails userDetails = getCustomUserDetails();
        String jwt = "JWT";
        String email = userDetails.getUsername();

        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(jwtService.generateToken(email)).thenReturn(jwt);

        // Act
        SessionV1Dto result = authService.createSession();

        // Assert
        assertNotNull(result);
        assertEquals(userDetails.getId(), result.getId());
        assertEquals(userDetails.getName(), result.getName());
        assertEquals(email, result.getEmail());
        assertEquals(jwt, result.getToken());

        verify(jwtService, times(1)).generateToken(email);
    }

    private CustomUserDetails getCustomUserDetails(){
        return new CustomUserDetails(getUser());
    }

    private User getUser(){
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setName("Usuário");
        user.setEmail("email@labfoods.com.br");

        return user;
    }
}