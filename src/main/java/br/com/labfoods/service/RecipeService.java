package br.com.labfoods.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import br.com.labfoods.config.security.CustomUserDetails;
import br.com.labfoods.model.Recipe;
import br.com.labfoods.model.User;
import br.com.labfoods.model.Vote;
import br.com.labfoods.repository.RecipeRepository;
import br.com.labfoods.repository.UserRepository;
import br.com.labfoods.repository.VoteRepository;
import br.com.labfoods.utils.exceptions.NotFoundException;
import br.com.labfoods.utils.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;

@Service
public class RecipeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeService.class);

    private RecipeRepository repository;
    private UserRepository userRepository;
    private VoteRepository voteRepository;

    @Autowired
    public RecipeService(RecipeRepository repository, UserRepository userRepository, VoteRepository voteRepository){
        this.repository = repository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

    public List<Recipe> findAll() {
        LOGGER.info("Listing all recipes");

        List<Recipe> recipes = repository.findAll();

        recipes.forEach(recipe -> {
            recipe.setCreatedBy(getSecurityUser(recipe));

            if (recipe.getVotes() != null && !recipe.getVotes().isEmpty()){
                double voteSum = recipe.getVotes()
                    .stream()
                    .mapToDouble(Vote::getNote)
                    .sum();
                double voteAvg = (voteSum == 0 ? null : voteSum / recipe.getVotes().size());
                recipe.setVoteAvg(voteAvg);
                recipe.getVotes().forEach(vote -> vote.setCreatedBy(getSecurityUser(recipe)));
            }   
        });
        
        return Optional.ofNullable(recipes)
            .orElseThrow(NotFoundException::new);
    }

    public Recipe findById(UUID id) {
        LOGGER.info("Listing recipe by id: {}", id);
        
        return repository.findById(id)
            .map(recipe -> {
                recipe.setCreatedBy(getSecurityUser(recipe));
        
                if (recipe.getVotes() != null && !recipe.getVotes().isEmpty()){
                    double voteSum = recipe.getVotes()
                        .stream()
                        .mapToDouble(Vote::getNote)
                        .sum();
                    double voteAvg = (voteSum == 0 ? null : voteSum / recipe.getVotes().size());
                    recipe.setVoteAvg(voteAvg);
                    recipe.getVotes().forEach(vote -> vote.setCreatedBy(getSecurityUser(recipe)));
                }
                
                return recipe;
            })
            .orElseThrow(NotFoundException::new);
    }

    public void create(Recipe recipe){
        LOGGER.info("Creating a recipe");

        recipe.setCreatedBy(userLogged());
        recipe.setCreatedDate(LocalDateTime.now());
        recipe.setCreatedBy(getSecurityUser(recipe));        

        repository.save(recipe);
    }

    public void update(Recipe recipe){
        LOGGER.info("Updating a recipe");

        recipe.setLastModifiedDate(LocalDateTime.now());
        recipe.setCreatedBy(getSecurityUser(recipe));
    
        repository.save(recipe);
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting recipe by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        repository.deleteById(id);
    }
    
    @Transactional
    public void deleteMyAllRecipes() {
        UUID userId = userLogged().getId();

        LOGGER.info("Deleting all recipe by userId: {}", userId);

        if (!repository.existsByCreatedById(userId)) {
            throw new NotFoundException();
        }

        repository.deleteByCreatedById(userId);
        voteRepository.deleteByCreatedById(userId);
    }
    

    public boolean existsByCreatedById(UUID id) {
       return repository.existsByCreatedById(id);
    }

    public int countBy() {
        LOGGER.info("Counting active users");
        return repository.countBy();
    }

    private User getSecurityUser(Recipe recipe){
        return new User(recipe.getCreatedBy().getId(), recipe.getCreatedBy().getName());
    }

    private User userLogged() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findByEmail(user.getUsername());
    }

    private User findByEmail(String email) {
        LOGGER.info("Listing user by email: {}", email);

        return Optional.ofNullable(userRepository.findByEmail(email)).orElseThrow(UnauthorizedException::new);
    }
}