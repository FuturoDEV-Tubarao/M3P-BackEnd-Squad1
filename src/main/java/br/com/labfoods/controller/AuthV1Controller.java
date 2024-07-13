package br.com.labfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.labfoods.config.security.JwtService;
import br.com.labfoods.dto.LoginV1Dto;
import br.com.labfoods.dto.SessionV1Dto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labfoods/v1/auth")
public class AuthV1Controller {

    private JwtService service;

    @Autowired
    public AuthV1Controller(JwtService service) {
        this.service = service;
    }

    @PostMapping()
    @Operation(summary = "Generate a session.", tags = "AuthV1Controller")
    public ResponseEntity<SessionV1Dto> login(@RequestBody @Valid LoginV1Dto dto) {
        String jwt = service.generateToken(dto.getEmail());
        
        SessionV1Dto sessionDto = SessionV1Dto.builder().token(jwt).build();

        return ResponseEntity.ok().body(sessionDto);
    }
}