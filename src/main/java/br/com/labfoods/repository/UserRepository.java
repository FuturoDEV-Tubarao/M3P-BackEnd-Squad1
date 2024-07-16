package br.com.labfoods.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.labfoods.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>{
    
    int countByActiveTrue();

    boolean existsByCpf(String cpf);

    User findByCpf(String cpf);

    boolean existsByEmail(String email);

    User findByEmail(String email);
}