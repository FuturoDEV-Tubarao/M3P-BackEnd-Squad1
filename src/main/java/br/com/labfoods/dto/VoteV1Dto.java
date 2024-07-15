package br.com.labfoods.dto;

import java.time.LocalDateTime;

import java.util.UUID;
import br.com.labfoods.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteV1Dto {
    private double note;
    private String title;
    private String feedback;
    private UUID recipeId;
    private User createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}