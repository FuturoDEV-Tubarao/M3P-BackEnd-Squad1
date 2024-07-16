package br.com.labfoods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.labfoods.config.service.JwtService;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private JwtService jwtService;

    @Autowired
    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private String getEmailContextHolder(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.info("Fetching email from contextHolder: {}", email);
        return email;
    }

    public String generateToken(){
        String email = getEmailContextHolder();
        LOGGER.info("Generating token for email: {}", email);
        return jwtService.generateToken(email);
    }
}