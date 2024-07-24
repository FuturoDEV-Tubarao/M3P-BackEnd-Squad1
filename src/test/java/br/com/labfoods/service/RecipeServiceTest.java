package br.com.labfoods.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Optional;
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
import br.com.labfoods.repository.RecipeRepository;
import br.com.labfoods.utils.exceptions.NotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;

class RecipeServiceTest {

    @Mock
    private RecipeRepository recipRepository;

    @InjectMocks
    private RecipeService recipeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Should_ReturnRecipes_When_FindAll() {
        // Arrange
        List<Recipe> recipes = getRecipes();

        when(recipeService.findAll()).thenReturn(recipes);

        // Act
        List<Recipe> result = recipeService.findAll();

        // Assert
        assertEquals(recipes.size(), result.size());
        assertEquals(recipes.stream().findFirst().get().getId(), result.stream().findFirst().get().getId());
        assertEquals(recipes.stream().findFirst().get().getDescription(), result.stream().findFirst().get().getDescription());

        verify(recipRepository, times(1)).findAll();
    }

    @Test
    void Should_ReturnEmpty_When_RecipesNotFound() {
        // Arrange
        List<Recipe> recipes = new ArrayList<>();

        when(recipeService.findAll()).thenReturn(recipes);

        // Act
        List<Recipe> result = recipeService.findAll();

        // Assert
        assertEquals(recipes.size(), result.size());
        assertNotNull(result);

        verify(recipRepository, times(1)).findAll();
    }
    
    @Test
    void Should_ReturnRecipe_When_FindById() {
        // Arrange
        UUID id = UUID.randomUUID();
        Recipe recipe = getRecipe();


        when(recipRepository.findById(id)).thenReturn(Optional.of(recipe));

        // Act
        Recipe result = recipeService.findById(id);

        // Assert
        assertNotNull(recipe);
        assertEquals(recipe.getId(), result.getId());
        assertEquals(recipe.getDescription(), result.getDescription());

        verify(recipRepository, times(1)).findById(id);
    }

    @Test
    void Should_ThrowException_When_RecipeNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(recipRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> recipeService.findById(id));

        // Assert
        assertEquals("register not found.", exception.getMessage());
        verify(recipRepository, times(1)).findById(id);
    }

    @Test
    void Should_DeleteRecipe_When_DeleteById() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(recipRepository.existsById(id)).thenReturn(true);

        // Act
        recipeService.delete(id);

        // Assert
        verify(recipRepository, times(1)).existsById(any());
        verify(recipRepository, times(1)).deleteById(any());
    }

    @Test
    void Should_ThrowException_When_DeleteRecipeNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(recipRepository.existsById(id)).thenReturn(false);

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> recipeService.delete(id));

        // Assert
        assertEquals("register not found.", exception.getMessage());
        verify(recipRepository, times(1)).existsById(any());
        verify(recipRepository, never()).deleteById(any());
    }

    private List<Recipe> getRecipes(){
        Recipe recipe = getRecipe();

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);

        return recipes;
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