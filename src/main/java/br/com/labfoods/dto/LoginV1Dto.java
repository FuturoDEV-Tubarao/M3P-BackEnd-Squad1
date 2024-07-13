package br.com.labfoods.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginV1Dto {

    private String email;
    private String password;
    
}
