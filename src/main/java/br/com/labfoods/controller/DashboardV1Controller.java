package br.com.labfoods.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.labfoods.model.Recipe;
import br.com.labfoods.model.User;
import br.com.labfoods.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import br.com.labfoods.service.RecipeService;

@RestController
@RequestMapping("/api/labfoods/v1/dashboard")
public class DashboardV1Controller {
    
    private UserService userService;
    private RecipeService recipeService;

    @Autowired
    public DashboardV1Controller(UserService userService, RecipeService recipeService) {
        this.userService = userService;
        this.recipeService = recipeService;
    }

    @Operation(summary = "Get user list.", tags = "DashboardV1Controller")
    @GetMapping("users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.findAll();
        
        return ResponseEntity.ok().body(users);
    }

    @Operation(summary = "Get recipe list.", tags = "DashboardV1Controller")
    @GetMapping("recipes")
    public ResponseEntity<List<Recipe>> getRecipes() {
        List<Recipe> recipes = recipeService.findAll();

        return ResponseEntity.ok().body(recipes);
    }
    
    @Operation(summary = "Get a active users.", tags = "DashboardV1Controller")
    @GetMapping ("users/active")
    public ResponseEntity<Integer> getActiveUsers() {        
        int totalActiveUsers = userService.countByActiveTrue();

        return ResponseEntity.ok().body(totalActiveUsers);
    }

    @Operation(summary = "Get a total recipes.", tags = "DashboardV1Controller")
    @GetMapping("recipes/total")
    public ResponseEntity<Integer> getTotalRecipes() {        
        int totalRecipes = recipeService.countBy();

        return ResponseEntity.ok().body(totalRecipes);
    }
}