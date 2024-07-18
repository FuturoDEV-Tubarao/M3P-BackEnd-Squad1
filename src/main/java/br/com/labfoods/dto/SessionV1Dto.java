package br.com.labfoods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionV1Dto {

    private UUID id;
    private String name;
    private String email;
    private String token;
}
