package br.com.labfoods.dto;

import java.util.List;
import br.com.labfoods.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardV1Dto {
    private Integer totalContactUsersActive;
    private Integer totalRecipes;
    private List<Recipe> top3Recipe;
}