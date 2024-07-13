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
import br.com.labfoods.repository.RecipeRepository;
import br.com.labfoods.utils.exceptions.NotFoundException;

@Service
public class RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private RecipeRepository repository;

    @Autowired
    public RecipeService(RecipeRepository repository){
        this.repository = repository;
    }

    public List<Recipe> findAll() {
        LOGGER.info("Listing all recipes");

        List<Recipe> recipes = repository.findAll();

        return Optional.ofNullable(recipes)
            .orElseThrow(NotFoundException::new);
    }

    public Recipe findById(UUID id) {
        LOGGER.info("Listing recipe by id: {}", id);

        return repository.findById(id)
            .orElseThrow(NotFoundException::new);
    }

    public void save(Recipe recipe){

        if(isNew(recipe)){
            LOGGER.info("Saving recipe");
            recipe.setCreatedDate(LocalDateTime.now());
        }else {
            LOGGER.info("Updating recipe");
            recipe.setLastModifiedDate(LocalDateTime.now());
        }

        repository.save(recipe);
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting recipe by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        repository.deleteById(id);
    }

    private boolean isNew(Recipe recipe) {
        return recipe.getId() == null;
    }

    public boolean existsByCreatedById(UUID id) {
       return repository.existsByCreatedById(id);
    }

    public int countBy() {
        LOGGER.info("Counting active users");
        return repository.countBy();
    }

    public List<Recipe> findTop3Recipes() {
        LOGGER.info("Listing top 3 recipes");
        return repository.findTop3Recipes();
    }
}