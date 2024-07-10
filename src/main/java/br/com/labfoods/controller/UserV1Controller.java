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
import java.util.stream.Collectors;

import br.com.labfoods.dto.UserV1Dto;
import br.com.labfoods.model.User;
import br.com.labfoods.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labfoods/v1/user")
public class UserV1Controller {

    private UserService service;
    private ModelMapper mapper;

    @Autowired
    public UserV1Controller(UserService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = service.findAll();

        List<User> securityUsers = users.stream()
            .map(user -> new User(user.getId(), user.getName(), user.getGender(), user.isActive()))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok().body(securityUsers);
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody @Valid UserV1Dto dto) {
        User user = mapper.map(dto, User.class);
        service.save(user);

        return ResponseEntity.ok().body(user.getName());
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody @Valid UserV1Dto dto) {
        User user = service.findById(id);
        user = mapper.map(dto, User.class);
        user.setId(id);
        service.save(user);

        User securityUser = new User(user.getId(), user.getName(), user.getGender(), user.isActive());
        
        return ResponseEntity.ok().body(securityUser);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        
        return ResponseEntity.noContent().build();
    }    
}