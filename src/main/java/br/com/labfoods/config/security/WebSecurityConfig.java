package br.com.labfoods.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    protected static final String[] NO_AUTH = {
        "/v1/auth",
        "/api/labfoods/v1/user"
    };     
  
    private CustomBasicAuthenticationFilter customBasicAuthenticationFilter;

    @Autowired
    public WebSecurityConfig (CustomBasicAuthenticationFilter customBasicAuthenticationFilter) {
        this.customBasicAuthenticationFilter = customBasicAuthenticationFilter;
    }

    // JWT Authentication
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(request -> request
                .requestMatchers(NO_AUTH)
                .permitAll()
                .anyRequest()
                .authenticated())
            .sessionManagement(manger -> manger
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(customBasicAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}