package br.com.labfoods.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.labfoods.model.Vote;
import br.com.labfoods.repository.VoteRepository;
import br.com.labfoods.utils.exceptions.BusinessException;
import br.com.labfoods.utils.exceptions.NotFoundException;

@Service
public class VoteService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);

    private VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository){
        this.repository = repository;
    }

    public List<Vote> findAll() {
        LOGGER.info("Listing all votes");

        List<Vote> votes = repository.findAll();

        return Optional.ofNullable(votes)
            .orElseThrow(NotFoundException::new);
    }

    public Vote findById(UUID id) {
        LOGGER.info("Listing vote by id: {}", id);

        return repository.findById(id)
            .orElseThrow(NotFoundException::new);
    }

    public void save(Vote vote){

        if(isNew(vote)){
            LOGGER.info("Saving vote");
            vote.setCreatedDate(LocalDateTime.now());

            // Cada usuário poderá votar em uma receita
            if(repository.existsByCreatedById(vote.getCreatedByContactId())) {
                throw new BusinessException("contact","Can't vote in more than one recipe");
            }
        }else {
            LOGGER.info("Updating vote");
            vote.setLastModifiedDate(LocalDateTime.now());
        }

        //e) Não pode votar nas suas próprias receitas;
        if(vote.getRecipe().getCreatedBy().getId() == vote.getCreatedByContactId()){
            throw new BusinessException("contact","Can't vote in your own recipe");
        }

        //com nota de 0 a 5 (variando de 0,5 em 0,5), e deixar um feedback. Os usuários não poderão votar na sua própria receita.
        if (vote.getNote() % 0.5 != 0) {
            throw new BusinessException("note","Must be a multiple of 0.5");
        }

        repository.save(vote);
    }

    public void delete(UUID id) {
        LOGGER.info("Deleting vote by id: {}", id);

        if (!repository.existsById(id)) {
            throw new NotFoundException();
        }

        repository.deleteById(id);
    }

    private boolean isNew(Vote vote) {
        return vote.getId() == null;
    }

}