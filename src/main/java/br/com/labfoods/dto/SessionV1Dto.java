package br.com.labfoods.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SessionV1Dto {

    private String token;
    
}
