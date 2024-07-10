package br.com.labfoods.config.security;

import java.io.IOException;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import br.com.labfoods.model.User;
import br.com.labfoods.repository.UserRepository;
import br.com.labfoods.utils.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomBasicAuthenticationFilter.class);

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";

    private UserRepository repository;

    @Autowired
    public CustomBasicAuthenticationFilter(UserRepository repository){
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        if(isBasicAuthentication(request)){
            LOGGER.info("Basic authentication");

            String[] credentials = decodeBase64(getHeader(request)
                                        .replace(BASIC, ""))
                                        .split(":");
            String email = credentials[0];
            String password = credentials[1];

            LOGGER.warn("criptografada: " + passwordEncoder().encode(password));

            User user = repository.findByEmail(email);

            if(user == null){
                throw new UnauthorizedException("email");
            }

            boolean valid = checkPassword(password, user.getPassword());            

            if(!valid){
                throw new UnauthorizedException("password");
            }

            setAuthentication(user);
        }

        filterChain.doFilter(request, response);
    }



    private void setAuthentication(User user){
        Authentication authentication = createAuthenticationToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Authentication createAuthenticationToken(User user){
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        return new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
    }

    private boolean isBasicAuthentication(HttpServletRequest request){
        String header = getHeader(request);
        return header != null && header.startsWith(BASIC);
    }

    private String getHeader(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION);
    }

    private String decodeBase64(String replace){
        byte[] decodeBytes = Base64.getDecoder().decode(replace);
        return new String(decodeBytes);
    }

    private boolean checkPassword(String passwordAuth, String passwordDB){
        return passwordEncoder().matches(passwordAuth, passwordDB);
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}