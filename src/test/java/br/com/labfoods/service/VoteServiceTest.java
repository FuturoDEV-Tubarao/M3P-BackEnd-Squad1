package br.com.labfoods.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import br.com.labfoods.enums.Gender;
import br.com.labfoods.enums.RecipeType;
import br.com.labfoods.model.Recipe;
import br.com.labfoods.model.User;
import br.com.labfoods.model.UserAddress;
import br.com.labfoods.model.Vote;
import br.com.labfoods.repository.VoteRepository;
import br.com.labfoods.utils.exceptions.NotFoundException;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

class VoteServiceTest {

    @Mock
    private VoteRepository voteRepository;

    @InjectMocks
    private VoteService voteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Should_ReturnVotes_When_FindAll() {
        // Arrange
        List<Vote> votes = getVotes();

        when(voteRepository.findAll()).thenReturn(votes);

        // Act
        List<Vote> result = voteService.findAll();

        // Assert
        assertEquals(votes.size(), result.size());
        assertEquals(votes.stream().findFirst().get().getId(), result.stream().findFirst().get().getId());
        assertEquals(votes.stream().findFirst().get().getTitle(), result.stream().findFirst().get().getTitle());

        verify(voteRepository, times(1)).findAll();
    }

    @Test
    void Should_ReturnEmpty_When_VotesNotFound() {
        // Arrange
        List<Vote> votes = new ArrayList<>();

        when(voteRepository.findAll()).thenReturn(votes);

        // Act
        List<Vote> result = voteService.findAll();

        // Assert
        assertEquals(votes.size(), result.size());
        assertNotNull(result);

        verify(voteRepository, times(1)).findAll();
    }
    
    @Test
    void Should_ReturnVote_When_FindById() {
        // Arrange
        UUID id = UUID.randomUUID();
        Vote vote = getVote();


        when(voteRepository.findById(id)).thenReturn(Optional.of(vote));

        // Act
        Vote result = voteService.findById(id);

        // Assert
        assertNotNull(vote);
        assertEquals(vote.getId(), result.getId());
        assertEquals(vote.getTitle(), result.getTitle());

        verify(voteRepository, times(1)).findById(any());
    }

    @Test
    void Should_ThrowException_When_VoteNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(voteRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> voteService.findById(id));

        // Assert
        assertEquals("register not found.", exception.getMessage());
        verify(voteRepository, times(1)).findById(any());
    }

    @Test
    void Should_DeleteVote_When_DeleteById() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(voteRepository.existsById(id)).thenReturn(true);

        // Act
        voteService.delete(id);

        // Assert
        verify(voteRepository, times(1)).existsById(any());
        verify(voteRepository, times(1)).deleteById(any());
    }

    @Test
    void Should_ThrowException_When_DeleteVoteNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(voteRepository.existsById(id)).thenReturn(false);

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> voteService.delete(id));

        // Assert
        assertEquals("register not found.", exception.getMessage());
        verify(voteRepository, times(1)).existsById(any());
        verify(voteRepository, never()).deleteById(any());
    }

    private List<Vote> getVotes(){
        Vote vote = getVote();

        List<Vote> votes = new ArrayList<>();
        votes.add(vote);

        return votes;
    }

    private Vote getVote(){
        Vote vote = new Vote();

        vote.setId(UUID.randomUUID());
        vote.setTitle("Vote 1");
        vote.setNote(10);
        vote.setRecipe(getRecipe());
        vote.setCreatedBy(getUser());
        vote.setCreatedDate(LocalDateTime.now());

        return vote;
    }

    private Recipe getRecipe(){
        Recipe recipe = new Recipe();

        recipe.setId(UUID.randomUUID());
        recipe.setDescription("Recipe 1");
        recipe.setCreatedBy(getUser());
        recipe.setCreatedDate(LocalDateTime.now());
        recipe.setGlutenFree(false);
        recipe.setLactoseFree(false);
        recipe.setIngredients("Ingredients");
        recipe.setOrigin("Brazil");
        recipe.setPreparationMethod("PreparationMethod");
        recipe.setPreparationTime("1 min");
        recipe.setRecipeType(RecipeType.BREAKFAST);

        return recipe;
    }

    private User getUser(){
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setName("Usuário 1");
        user.setGender(Gender.FEMALE);
        user.setActive(true);
        user.setBirthDate(LocalDate.now());
        user.setCpf("93637803682");
        user.setCreatedDate(LocalDateTime.now());
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setUserAddress(getUserAddress());

        return user;
    }

    private UserAddress getUserAddress(){
        UserAddress userAddress = new UserAddress();

        userAddress.setId(UUID.randomUUID());
        userAddress.setZipCode("88708319");
        userAddress.setStreet("Av. Joaõ Manoel Galdino da Luz");
        userAddress.setNumberAddress(666);
        userAddress.setNeighborhood("Vila Esperança");
        userAddress.setCity("Tubarão");
        userAddress.setState("Santa Catarina");
        userAddress.setDdd("48");
        userAddress.setIbge("4218707");
        userAddress.setSiafi("8367");

        return userAddress;
    }
}