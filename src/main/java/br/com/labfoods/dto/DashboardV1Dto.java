package br.com.labfoods.dto;

import java.util.List;
import br.com.labfoods.model.Recipe;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardV1Dto {
    private Integer totalContactUsersActive;
    private Integer totalRecipes;
    private List<Recipe> top3Recipe;
}