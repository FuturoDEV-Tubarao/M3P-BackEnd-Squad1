package br.com.labfoods.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonInclude;
import br.com.labfoods.enums.RecipeType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "recipe")
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Length(max = 255)
    @NotEmpty
    private String title;

    @Length(max = 255)
    @NotEmpty
    private String description;

    @Length(max = 255)
    @NotEmpty
    private String ingredients;
    
    @NotNull
    private String preparationTime;

    @Length(max = 255)
    @NotEmpty
    private String preparationMethod;

    @Column(nullable = false)
    private RecipeType recipeType;

    @Column(nullable = false)
    private boolean glutenFree;

    @Column(nullable = false)
    private boolean lactoseFree;

    @Length(max = 255)
    @NotEmpty
    private String origin;

    @Length(max = 255)
    private String url;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.REMOVE)
    private List<Vote> votes;

    @Transient
    @DecimalMax(value = "99.99", message = "Vote average must have at most two decimal places")
    private double voteAvg;

    @ManyToOne
    @JoinColumn(name="created_by__contact_id")
    private User createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}