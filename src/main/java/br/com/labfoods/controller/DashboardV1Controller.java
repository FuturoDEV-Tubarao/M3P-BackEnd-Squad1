package br.com.labfoods.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.labfoods.dto.DashboardV1Dto;
import br.com.labfoods.model.Recipe;
import br.com.labfoods.service.UserService;
import br.com.labfoods.service.RecipeService;

@RestController
@RequestMapping("/api/labfoods/v1/dashboard")
public class DashboardV1Controller {
    
    private UserService contactService;
    private RecipeService recipeService;

    @Autowired
    public DashboardV1Controller(UserService contactService, RecipeService recipeService) {
        this.contactService = contactService;
        this.recipeService = recipeService;
    }

    @GetMapping
    public ResponseEntity<DashboardV1Dto> getDashboard() {
        
        int totalContactsActive = contactService.countByActiveTrue();
        int totalRecipes = recipeService.countBy();
        List<Recipe> top3Recipe = recipeService.findTop3Recipes();

        DashboardV1Dto dashboard = DashboardV1Dto.builder()
            .totalContactUsersActive(totalContactsActive)
            .totalRecipes(totalRecipes)
            .top3Recipe(top3Recipe)
            .build();

        return ResponseEntity.ok().body(dashboard);
    }
}