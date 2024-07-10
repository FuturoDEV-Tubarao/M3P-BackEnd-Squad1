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
import br.com.labfoods.dto.RecipeV1Dto;
import br.com.labfoods.model.Recipe;
import br.com.labfoods.service.RecipeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labfoods/v1/recipe")
public class RecipeV1Controller {

    private RecipeService service;
    private ModelMapper mapper;

    @Autowired
    public RecipeV1Controller(RecipeService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    
    @GetMapping
    public ResponseEntity<List<Recipe>> findAll() {
        List<Recipe> recipe = service.findAll();
        return ResponseEntity.ok().body(recipe);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Recipe> findByCodigo(@PathVariable UUID id) {
        Recipe recipe = service.findById(id);
        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping
    public ResponseEntity<Recipe> save(@RequestBody @Valid RecipeV1Dto dto) {
        Recipe recipe = mapper.map(dto, Recipe.class);
        service.save(recipe);

        return ResponseEntity.ok().body(recipe);
    }

    @PutMapping("{id}")
    public ResponseEntity<Recipe> update(@PathVariable UUID id, @RequestBody @Valid RecipeV1Dto dto) {
        Recipe recipe = service.findById(id);
        recipe = mapper.map(dto, Recipe.class);
        recipe.setId(id);
        service.save(recipe);

        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        
        return ResponseEntity.noContent().build();
    }    
}