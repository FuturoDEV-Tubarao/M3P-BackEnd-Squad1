package br.com.labfoods.dto;

import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import br.com.labfoods.enums.Gender;
import br.com.labfoods.model.UserAddress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserV1Dto {
    private UUID id;
    private String name;
    private Gender gender;
    private String cpf;
    private LocalDate birthDate;
    private String email;
    private String password;
    private boolean active;
    private UserAddress userAddress;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}