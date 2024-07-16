package br.com.labfoods.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.labfoods.model.User;
import br.com.labfoods.repository.UserRepository;
import br.com.labfoods.utils.exceptions.BusinessException;
import br.com.labfoods.utils.exceptions.ConflictException;
import br.com.labfoods.utils.exceptions.NotFoundException;
import br.com.labfoods.utils.exceptions.UnauthorizedException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository repository;
    // private RecipeService recipeService;

    @Autowired
    public UserService(UserRepository repository){
        this.repository = repository;
        // this.recipeService = recipeService;
    }

	public int countByActiveTrue() {
        LOGGER.info("Counting active users");
        return repository.countByActiveTrue();
    }

    public List<User> findAll() {
        LOGGER.info("Listing all users");
        
        return Optional.ofNullable(repository.findAll())
        .orElseThrow(NotFoundException::new);
    }

    public User findById(UUID id) {
        LOGGER.info("Listing user by id: {}", id);
    
        return repository.findById(id)
            .orElseThrow(NotFoundException::new);
    }

    public User findByEmail(String email) {
        LOGGER.info("Listing user by email: {}", email);

        return Optional.ofNullable(repository.findByEmail(email))
        .orElseThrow(UnauthorizedException::new);
    }

    public void create(User user) {
        LOGGER.info("Creating a user");
        user.setCreatedDate(LocalDateTime.now());
        
        userValidation(user);

		String password = user.getPassword();
		user.setPassword(encodePassword(password));

		repository.save(user);
    }

    public void update(User user){
        LOGGER.info("Updating a user");
        user.setLastModifiedDate(LocalDateTime.now());
    
        userValidation(user);

		String password = user.getPassword();
		user.setPassword(encodePassword(password));

		repository.save(user);
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting user by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        // // Não permite deletar usuário que tenha receitas cadastradas; 
        // if(recipeService.existsByCreatedById(id)) {
        //     throw new BusinessException("user","Can't delete someone with recipes associated");
        // }

        repository.deleteById(id);
    }

    public boolean existsByEmail(String email){
        return repository.existsByEmail(email);
    }

    public boolean checkPassword(String password, String encodedPassword){
        LOGGER.info("Checking password");
        return passwordEncoder().matches(password, encodedPassword);
    }

    public String encodePassword(String password){
        LOGGER.info("Encoding password");
        return passwordEncoder().encode(password);
    }

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    private void userValidation(User user){

        //Não permite cadastrar usuários com o mesmo CPF; 
        User userUsedCpf = repository.findByCpf(user.getCpf());
        if(userUsedCpf != null && userUsedCpf.getId() != user.getId() && userUsedCpf.getCpf().equals(user.getCpf())) {
            throw new ConflictException("cpf");
        }

        //Não permite alterar o cpf
        User userChangedCpf = repository.findByCpf(user.getCpf());
        if(userChangedCpf != null && userChangedCpf.getId() == user.getId() && !userChangedCpf.getCpf().equals(user.getCpf())) {
            throw new BusinessException("cpf", "It is not allowed to change the CPF.");
        }

        //Não permite cadastrar usuários com o mesmo email; 
        User userUsedEmail = repository.findByEmail(user.getEmail());
        if(userUsedEmail != null && userUsedEmail.getId() != user.getId() && userUsedEmail.getEmail().equals(user.getEmail())) {
            throw new ConflictException("email");
        }
    }

    public User userLogged() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findById(user.getId());
    }
}