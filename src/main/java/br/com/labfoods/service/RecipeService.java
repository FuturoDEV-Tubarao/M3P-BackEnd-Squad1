package br.com.labfoods.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.labfoods.model.Recipe;
import br.com.labfoods.model.User;
import br.com.labfoods.repository.RecipeRepository;
import br.com.labfoods.utils.exceptions.NotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private RecipeRepository repository;
    private UserService userService;

    @Autowired
    public RecipeService(RecipeRepository repository, UserService userService){
        this.repository = repository;
        this.userService = userService;
    }

    public List<Recipe> findAll() {
        LOGGER.info("Listing all recipes");

        List<Recipe> recipes = repository.findAll();

        recipes.forEach(recipe -> {
            User securityUser = new User(recipe.getCreatedBy().getId(), recipe.getCreatedBy().getName());
            recipe.setCreatedBy(securityUser);
        });

        return Optional.ofNullable(recipes)
            .orElseThrow(NotFoundException::new);
    }

    @Transactional
    public Recipe findById(UUID id) {
        LOGGER.info("Listing recipe by id: {}", id);
        Recipe recipe = repository.findById(id).orElseThrow(NotFoundException::new);
        User securityUser = new User(recipe.getCreatedBy().getId(), recipe.getCreatedBy().getName());
        recipe.setCreatedBy(securityUser);

        return recipe;
    }

    public void create(Recipe recipe){
        recipe.setCreatedBy(userService.userLogged());

        LOGGER.info("Creating a recipe");
        recipe.setCreatedDate(LocalDateTime.now());

        repository.save(recipe);
    }

    public void update(Recipe recipe){
        LOGGER.info("Updating a recipe");
        recipe.setLastModifiedDate(LocalDateTime.now());
    
        repository.save(recipe);
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting recipe by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        repository.deleteById(id);
    }

    public boolean existsByCreatedById(UUID id) {
       return repository.existsByCreatedById(id);
    }

    public int countBy() {
        LOGGER.info("Counting active users");
        return repository.countBy();
    }
}