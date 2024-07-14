package br.com.labfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.labfoods.dto.SessionV1Dto;
import br.com.labfoods.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/labfoods/v1/auth")
public class AuthV1Controller {

    private AuthService service;

    @Autowired
    public AuthV1Controller(AuthService service) {
        this.service = service;
    }

    @PostMapping()
    @Operation(summary = "Generate a session.", tags = "AuthV1Controller")
    public ResponseEntity<SessionV1Dto> login() {
        String jwt = service.generateToken();
        
        SessionV1Dto sessionDto = SessionV1Dto.builder().token(jwt).build();

        return ResponseEntity.ok().body(sessionDto);
    }
}