package br.com.labfoods.controller;

import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labfoods/v1/recipe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecipeV1Controller {

    private RecipeService service;
    private ModelMapper mapper;

    @Autowired
    public RecipeV1Controller(RecipeService service, ModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    
    @GetMapping
    @Operation(summary = "Get recipe list.", tags = "RecipeV1Controller")
    public ResponseEntity<List<Recipe>> findAll() {
        List<Recipe> recipes = service.findAll();
        return ResponseEntity.ok().body(recipes);
    }
    
    @GetMapping("{id}")
    @Operation(summary = "Get a recipe.", tags = "RecipeV1Controller")
    public ResponseEntity<Recipe> findById(@PathVariable UUID id) {
        Recipe recipe = service.findById(id);
        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping
    @Operation(summary = "Create a recipe.", tags = "RecipeV1Controller")
    public ResponseEntity<Recipe> save(@RequestBody @Valid RecipeV1Dto dto) {
        Recipe recipe = mapper.map(dto, Recipe.class);
        service.create(recipe);

        return ResponseEntity.ok().body(recipe);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update a recipe.", tags = "RecipeV1Controller")
    public ResponseEntity<Recipe> update(@PathVariable UUID id, @RequestBody @Valid RecipeV1Dto dto) {
        Recipe recipe = service.findById(id);
        recipe = mapper.map(dto, Recipe.class);
        recipe.setId(id);
        service.update(recipe);

        return ResponseEntity.ok().body(recipe);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a recipe.", tags = "RecipeV1Controller")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        service.delete(id);
        
        return ResponseEntity.noContent().build();
    }
        
    @DeleteMapping
    @Operation(summary = "Deleting my all recipe.", tags = "VoteV1Controller")
    public ResponseEntity<String> deleteMyAllRecipes() {
        service.deleteMyAllRecipes();
        
        return ResponseEntity.noContent().build();
    }  
}