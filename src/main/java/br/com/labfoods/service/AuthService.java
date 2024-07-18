package br.com.labfoods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.labfoods.config.security.CustomUserDetails;
import br.com.labfoods.config.service.JwtService;
import br.com.labfoods.dto.SessionV1Dto;
import br.com.labfoods.model.User;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private JwtService jwtService;

    @Autowired
    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public SessionV1Dto createSession(){
        LOGGER.info("Generating a session.");

        LOGGER.info("Fetching email from contextHolder.");
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = user.getUsername();

        LOGGER.info("Generating token for email: {}", email);
        String jwt = jwtService.generateToken(email);

        return SessionV1Dto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(email)
            .token(jwt)
            .build();
    }
}