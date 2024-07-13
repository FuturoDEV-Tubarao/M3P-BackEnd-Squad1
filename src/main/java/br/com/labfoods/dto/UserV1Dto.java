package br.com.labfoods.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import br.com.labfoods.enums.Gender;
import br.com.labfoods.model.UserAddress;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserV1Dto {
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