package br.com.labfoods.config.security;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.labfoods.config.service.BasicService;
import br.com.labfoods.config.service.JwtService;
import br.com.labfoods.utils.exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private static final String AUTHORIZATION = "Authorization";
    private static final String BASIC = "Basic ";
    private static final String BEARER = "Bearer ";

    private JwtService jwtService;
    private BasicService basicService;
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    public CustomAuthenticationFilter(JwtService jwtService, BasicService basicService, UserDetailsServiceImpl userDetailsServiceImpl){
        this.jwtService = jwtService;
        this.basicService = basicService;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException{

        if(isBasicAuthentication(request)){
            LOGGER.info("Basic authentication");

            String email = basicService.getEmail(request);
            String password = basicService.getPassword(request);
            
            if(email != null && password != null){

                LOGGER.warn("Password: " + basicService.passwordEncode(password));

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
                boolean validPassword = basicService.checkPassword(password, userDetails.getPassword());            

                if(!validPassword){
                    throw new UnauthorizedException("password");
                }

                Authentication authentication = createAuthenticationToken(userDetails, null);
                setAuthentication(authentication);
            }
            
        } else if (isJwtAuthentication(request)){
            LOGGER.info("JWT authentication");

            String token = getToken(request);
            String email = jwtService.extractUsername(token);

            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){

                LOGGER.warn("JWT: " + token);

                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(email);
                boolean validToken = jwtService.validateToken(token, userDetails);    


                if(!validToken){
                    throw new UnauthorizedException("token");
                }

                Authentication authentication = createAuthenticationToken(userDetails, request);
                setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Authentication createAuthenticationToken(UserDetails userPrincipal, HttpServletRequest request){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());

        if (request != null){ 
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        }

         return authToken;
    }

    private void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String getHeader(HttpServletRequest request){
        return request.getHeader(AUTHORIZATION);
    }

    private boolean isBasicAuthentication(HttpServletRequest request){
        String header = getHeader(request);
        return header != null && header.startsWith(BASIC);
    }
    
    private boolean isJwtAuthentication(HttpServletRequest request){
        String header = getHeader(request);
        return header != null && header.startsWith(BEARER);
    }

    private String getToken(HttpServletRequest request) {
        String header = getHeader(request);
        return header.substring(7);
    }
}