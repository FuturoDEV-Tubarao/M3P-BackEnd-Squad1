package br.com.labfoods.dto;

import java.time.LocalDateTime;
import br.com.labfoods.enums.RecipeType;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

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
    private UUID createdByContactId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}