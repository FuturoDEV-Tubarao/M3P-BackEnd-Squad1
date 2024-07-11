package br.com.labfoods.dto;

import java.time.LocalDateTime;

import java.util.UUID;
import br.com.labfoods.model.Recipe;
import lombok.Data;

@Data
public class VoteV1Dto {
    private double note;
    private String feedback;
    private Recipe recipe;
    private UUID createdByContactId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}