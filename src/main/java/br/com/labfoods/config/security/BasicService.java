package br.com.labfoods.config.security;

import java.util.Base64;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class BasicService {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    public String getEmail(HttpServletRequest request){
        String[] credentials = getCredentials(request);
        return credentials[0];
    }

    public String getPassword(HttpServletRequest request){
        String[] credentials = getCredentials(request);
        return credentials[1];
    }

    public boolean checkPassword(String passwordAuth, String passwordDB){
        return passwordEncoder().matches(passwordAuth, passwordDB);
    }

    public String passwordEncode(String password) {
        return passwordEncoder().encode(password);
    }

    private String[] getCredentials(HttpServletRequest request){
        return decodeBase64(
                 request
                 .getHeader(AUTHORIZATION)
                 .replace(BASIC, "")
             ).split(":");
     }

    private String decodeBase64(String replace){
        byte[] decodeBytes = Base64.getDecoder().decode(replace);
        return new String(decodeBytes);
    }

    private PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}