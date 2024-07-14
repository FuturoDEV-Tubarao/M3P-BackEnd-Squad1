package br.com.labfoods.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.labfoods.config.security.JwtService;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    private JwtService jwtService;

    @Autowired
    public AuthService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    private String getEmailContextHolder(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public String generateToken(){
        String email = getEmailContextHolder();
        return jwtService.generateToken(email);
    }
}