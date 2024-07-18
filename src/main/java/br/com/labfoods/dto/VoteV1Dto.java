package br.com.labfoods.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteV1Dto {
    private UUID id;
    private double note;
    private String title;
    private String feedback;
    private UUID recipeId;
    private UserV2Dto createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}