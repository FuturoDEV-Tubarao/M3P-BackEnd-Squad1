package br.com.labfoods.dto;

import java.time.LocalDateTime;
import br.com.labfoods.enums.RecipeType;
import br.com.labfoods.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeV1Dto {
    private String title;
    private String description;
    private String ingredients;
    private int preparationTime;
    private String preparationMethod;
    private RecipeType recipeType;
    private boolean glutenFree;
    private boolean lactoseFree;
    private String origin;
    private String url;
    private User createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}