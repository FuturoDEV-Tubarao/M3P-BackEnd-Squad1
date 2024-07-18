package br.com.labfoods.dto;

import java.time.LocalDateTime;
import org.hibernate.validator.constraints.UUID;
import br.com.labfoods.enums.RecipeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeV1Dto {
    private UUID id;
    private String title;
    private String description;
    private String ingredients;
    private String preparationTime;
    private String preparationMethod;
    private RecipeType recipeType;
    private boolean glutenFree;
    private boolean lactoseFree;
    private String origin;
    private String url;
    private UserV2Dto createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}