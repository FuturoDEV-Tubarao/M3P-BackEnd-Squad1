package br.com.labfoods.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.labfoods.model.Recipe;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>{

    int countBy();

    @Query("SELECT DISTINCT v.recipe, MAX(v.note) FROM Vote v GROUP BY v.recipe ORDER BY MAX(v.note) DESC")
    List<Recipe> findTop3Recipes();

    boolean existsByCreatedById(UUID id);

}