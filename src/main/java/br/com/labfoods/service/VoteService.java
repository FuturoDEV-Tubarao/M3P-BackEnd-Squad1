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
import br.com.labfoods.model.Vote;
import br.com.labfoods.repository.VoteRepository;
import br.com.labfoods.utils.exceptions.BusinessException;
import br.com.labfoods.utils.exceptions.NotFoundException;

@Service
public class VoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);

    private VoteRepository repository;
    private RecipeService recipeService;
    private UserService userService;

    @Autowired
    public VoteService(VoteRepository repository, RecipeService recipeService, UserService userService){
        this.repository = repository;
        this.recipeService = recipeService;
        this.userService = userService;
    }

    public List<Vote> findAll() {
        LOGGER.info("Listing all votes");
    
        List<Vote> votes = repository.findAll();
        votes.forEach(vote -> {
            vote.setCreatedBy(getSecurityUser(vote));
            vote.getRecipe().setCreatedBy(getSecurityUser(vote.getRecipe()));
        });
    
        return Optional.ofNullable(votes)
            .orElseThrow(NotFoundException::new);
    }

    public Vote findById(UUID id) {
        LOGGER.info("Listing vote by id: {}", id);
    
        return repository.findById(id)
            .map(vote -> {
                vote.setCreatedBy(getSecurityUser(vote));
                vote.getRecipe().setCreatedBy(getSecurityUser(vote.getRecipe()));
                return vote;
            })
            .orElseThrow(NotFoundException::new);
    }

    public void create(Vote vote){
        LOGGER.info("Creating a vote");

        vote.setCreatedBy(userService.userLogged());
        vote.setCreatedDate(LocalDateTime.now());
        
        fetchRecipe(vote);

        voteValidation(vote);

        repository.save(vote);
        vote.setCreatedBy(getSecurityUser(vote));
        vote.getRecipe().setCreatedBy(getSecurityUser(vote.getRecipe()));
    }

    public void update(Vote vote){
        LOGGER.info("Updating a vote");

        vote.setCreatedBy(userService.userLogged());
        vote.setLastModifiedDate(LocalDateTime.now());

        fetchRecipe(vote);

        voteValidation(vote);

        repository.save(vote);
        vote.setCreatedBy(getSecurityUser(vote));
        vote.getRecipe().setCreatedBy(getSecurityUser(vote.getRecipe()));
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting vote by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        repository.deleteById(id);
    }
    
    private void fetchRecipe(Vote vote) {
        Recipe recipe = recipeService.findById(vote.getRecipeId());
        User securityUser = new User(recipe.getCreatedBy().getId(), recipe.getCreatedBy().getName());
        recipe.setCreatedBy(securityUser);
        vote.setRecipe(recipe);
    }

    private void voteValidation(Vote vote){
        //Não permite votar nas suas próprias receitas;
        if(vote.getRecipe().getCreatedBy().getId() == vote.getCreatedBy().getId()){
            throw new BusinessException("contact","Can't vote in your own recipe");
        }

        //Não permite nota diferente de 0 a 5 (variando de 0,5 em 0,5).
        if (vote.getNote() % 0.5 != 0) {
            throw new BusinessException("note","Must be a multiple of 0.5");
        }
    }

    private User getSecurityUser(Vote vote){
        return new User(vote.getCreatedBy().getId(), vote.getCreatedBy().getName());
    }

    private User getSecurityUser(Recipe recipe){
        return new User(recipe.getCreatedBy().getId(), recipe.getCreatedBy().getName());
    }
}