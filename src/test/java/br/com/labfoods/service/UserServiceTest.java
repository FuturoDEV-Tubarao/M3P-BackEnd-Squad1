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
import br.com.labfoods.model.User;
import br.com.labfoods.model.UserAddress;
import br.com.labfoods.repository.RecipeRepository;
import br.com.labfoods.repository.UserRepository;
import br.com.labfoods.utils.exceptions.BusinessException;
import br.com.labfoods.utils.exceptions.NotFoundException;
import java.util.Optional;
import java.time.LocalDate;
import java.time.LocalDateTime;

class UserServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Should_ReturnUsers_When_FindAll() {
        // Arrange
        List<User> users = getUsers();

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertEquals(users.size(), result.size());
        assertEquals(users.stream().findFirst().get().getId(), result.stream().findFirst().get().getId());
        assertEquals(users.stream().findFirst().get().getName(), result.stream().findFirst().get().getName());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    void Should_ReturnEmpty_When_UsersNotFound() {
        // Arrange
        List<User> users = new ArrayList<>();

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<User> result = userService.findAll();

        // Assert
        assertEquals(users.size(), result.size());
        assertNotNull(result);

        verify(userRepository, times(1)).findAll();
    }
    
    @Test
    void Should_ReturnUser_When_FindById() {
        // Arrange
        UUID id = UUID.randomUUID();
        User user = getUser();


        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        User result = userService.findById(id);

        // Assert
        assertNotNull(user);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());

        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void Should_ThrowException_When_UserNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.findById(id));

        // Assert
        assertEquals("register not found.", exception.getMessage());
        verify(userRepository, times(1)).findById(any());
    }

    @Test
    void Should_DeleteUser_When_DeleteById() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(true);
        when(recipeService.existsByCreatedById(id)).thenReturn(false);

        // Act
        userService.delete(id);

        // Assert
        verify(userRepository, times(1)).existsById(any());
        verify(userRepository, times(1)).deleteById(any());
        verify(recipeService, times(1)).existsByCreatedById(any());
    }

    @Test
    void Should_NotFoundException_When_DeleteUserNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(false);

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> userService.delete(id));

        // Assert
        assertEquals("register not found.", exception.getMessage());
        verify(userRepository, times(1)).existsById(any());
        verify(recipeService, never()).existsByCreatedById(any());
        verify(userRepository, never()).deleteById(any());
    }

    
    @Test
    void Should_BusinessException_When_DeleteUserWithRecipe() {
        // Arrange
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(true);
        when(recipeService.existsByCreatedById(id)).thenReturn(true);

        // Act
        BusinessException exception = assertThrows(BusinessException.class, () -> userService.delete(id));

        // Assert
        assertEquals("Can't delete someone with recipes associated.", exception.getMessage());
        verify(userRepository, times(1)).existsById(any());
        verify(recipeService, times(1)).existsByCreatedById(any());
        verify(userRepository, never()).deleteById(any());
    }

    private List<User> getUsers(){
        User user = getUser();

        List<User> users = new ArrayList<>();
        users.add(user);

        return users;
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