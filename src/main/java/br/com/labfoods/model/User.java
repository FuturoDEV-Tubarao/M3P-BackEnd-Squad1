package br.com.labfoods.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import br.com.labfoods.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Length(max = 255)
    @NotEmpty
    private String name;

    @NotNull
    private Gender gender;

    @Size(min = 11, max = 11)
    @Pattern(regexp = "^[0-9]{11}$", message = "O cpf deve ter 11 dígitos numéricos.")
    @NotEmpty
    @Column(nullable = false, unique = true, updatable = false)
    private String cpf;

    @Past
    @NotNull
    private LocalDate birthDate;

    @Length(max = 255)
    @NotEmpty
    private String email;

    @Length(max = 255)
    @NotEmpty
    private String password;

    private boolean active;

    @OneToOne
    @JoinColumn(name="user_address_id")
    @JsonIgnore
    private UserAddress userAddress;

    @CreatedDate
    private LocalDateTime createdDate;
    
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public User(UUID id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public User(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}