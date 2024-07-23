package br.com.labfoods.controller;

import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.labfoods.dto.UserV1Dto;
import br.com.labfoods.model.User;
import br.com.labfoods.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/labfoods/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserV1Controller {

    private UserService service;
    private ModelMapper mapper;

    @Autowired
    public UserV1Controller(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @Operation(summary = "Get user list.", tags = "UserV1Controller")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = service.findAll();

        return ResponseEntity.ok().body(users);
    }
        
    @GetMapping("{id}")
    @Operation(summary = "Get a user.", tags = "UserV1Controller")
    public ResponseEntity<User> findById(@PathVariable UUID id) {
        User user = service.findById(id);
        
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    @Operation(summary = "Create a user.", tags = "UserV1Controller")
    public ResponseEntity<User> save(@RequestBody @Valid UserV1Dto dto) {
        User user = mapper.map(dto, User.class);
        user = service.create(user);

        return ResponseEntity.ok().body(user);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a user.", tags = "UserV1Controller")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody @Valid UserV1Dto dto) {
        User user = service.findById(id);
        user = mapper.map(dto, User.class);
        user.setId(id);
        user = service.update(user);
        
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a user.", tags = "UserV1Controller")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        
        return ResponseEntity.noContent().build();
    }    
}