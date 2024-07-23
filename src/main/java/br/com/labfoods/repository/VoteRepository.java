package br.com.labfoods.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.labfoods.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, UUID>{

    boolean existsByCreatedById(UUID id);

    void deleteByCreatedById(UUID id);

}