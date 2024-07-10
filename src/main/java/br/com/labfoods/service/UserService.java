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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserRepository repository;
    private RecipeService recipeService;

    @Autowired
    public UserService(UserRepository repository, RecipeService recipeService){
        this.repository = repository;
        this.recipeService = recipeService;
    }

	public int countByActiveTrue() {
        LOGGER.info("Counting active users");
        return repository.countByActiveTrue();
    }

    public List<User> findAll() {
        LOGGER.info("Listing all users");

        List<User> users = repository.findAll();
        Optional.ofNullable(users)
            .orElseThrow(NotFoundException::new);

        return users;
    }

    public User findById(UUID id) {
        LOGGER.info("Listing user by id: {}", id);

        User user = repository.findById(id)
            .orElseThrow(NotFoundException::new);

        return user;
    }

    public void save(User user){

        if(isNew(user)){
            LOGGER.info("Saving user");
            user.setCreatedDate(LocalDateTime.now());

            //TODO Verificar regra para usuários existentes, nao funciona desta forma.
            
            //Não permite cadastrar usuários com o mesmo CPF; 
            boolean cpfInUse = repository.existsByCpf(user.getCpf());
            if(cpfInUse) {
                throw new ConflictException("CPF");
            }

            //Não permite cadastrar usuários com o mesmo email; 
            boolean emailInUse = existsByEmail(user.getEmail());
            if(emailInUse) {
                throw new ConflictException("email");
            }
        }else {
            LOGGER.info("Updating user");
            user.setLastModifiedDate(LocalDateTime.now());
        }

        if(user == null || user.getEmail() == null) {
            throw new BusinessException("email", "Email is required");
        }

        if(user == null || user.getPassword() == null) {
            throw new BusinessException("password", "Password is required");
        }

		String password = user.getPassword();
		user.setPassword(encodePassword(password));

		repository.save(user);
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting user by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        // Não permite deletar usuário que tenha receitas cadastradas; 
        if(recipeService.existsByCreatedById(id)) {
            throw new BusinessException("user","Can't delete someone with recipes associated");
        }

        repository.deleteById(id);
    }
    
    private boolean isNew(User user) {
        return user.getId() == null;
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
}