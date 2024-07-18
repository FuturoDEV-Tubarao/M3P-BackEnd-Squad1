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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.labfoods.dto.VoteV1Dto;
import br.com.labfoods.model.Vote;
import br.com.labfoods.service.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labfoods/v1/vote")
public class VoteV1Controller {

    private VoteService service;
    private ModelMapper mapper;

    @Autowired
    public VoteV1Controller(VoteService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    
    @GetMapping
    @Operation(summary = "Get vote list.", tags = "VoteV1Controller")
    public ResponseEntity<List<Vote>> findAll() {
        List<Vote> votes = service.findAll();
        return ResponseEntity.ok().body(votes);
    }
    
    @GetMapping("{id}")
    @Operation(summary = "Get a vote.", tags = "VoteV1Controller")
    public ResponseEntity<Vote> findById(@PathVariable UUID id) {
        Vote vote = service.findById(id);
        return ResponseEntity.ok().body(vote);
    }

    @PostMapping
    @Operation(summary = "Create a vote.", tags = "VoteV1Controller")
    public ResponseEntity<Vote> save(@RequestBody @Valid VoteV1Dto dto) {
        Vote vote = mapper.map(dto, Vote.class);
        service.create(vote);

        return ResponseEntity.ok().body(vote);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a vote.", tags = "VoteV1Controller")
    public ResponseEntity<Vote> update(@PathVariable UUID id, @RequestBody @Valid VoteV1Dto dto, @RequestHeader("Authorization") String authorization) {
        Vote vote = service.findById(id);
        vote = mapper.map(dto, Vote.class);
        vote.setId(id);
        
        service.update(vote);

        return ResponseEntity.ok().body(vote);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a vote.", tags = "VoteV1Controller")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        
        return ResponseEntity.noContent().build();
    }    
}