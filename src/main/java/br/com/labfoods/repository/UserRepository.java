package br.com.labfoods.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.labfoods.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    
    int countByActiveTrue();

    boolean existsByCpf(String cpf);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}