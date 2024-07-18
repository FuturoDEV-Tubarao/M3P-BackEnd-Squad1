package br.com.labfoods.dto;


import java.time.LocalDateTime;
import org.hibernate.validator.constraints.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserV2Dto {
    private UUID id;
    private String name;
    private boolean active;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}