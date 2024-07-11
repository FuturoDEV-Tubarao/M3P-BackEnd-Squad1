package br.com.labfoods.model;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Max(10)
    @Positive
    @NotNull
    private double note;

    @Length(max = 255)
    @NotEmpty
    private String feedback;

    @NotNull
    @ManyToOne
    @JoinColumn(name="recipe_id")
    @JsonIgnore
    private Recipe recipe;

    @Transient
    public UUID getRecipeId() {
        return (this.recipe != null) ? this.recipe.getId() : null;
    }
    
    @NotNull
    @ManyToOne
    @JoinColumn(name="created_by__contact_id")
    @JsonIgnore
    private User createdBy;

    @Transient
    public UUID getCreatedByContactId() {
        return (this.createdBy != null) ? this.createdBy.getId() : null;
    }

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}