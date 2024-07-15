package br.com.labfoods.config.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import br.com.labfoods.model.User;

public class CustomUserDetails extends User implements UserDetails {
    private static final String ADMIN = "ADMIN";

    private String username;
    private String password;
    private UUID id;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user) {
        //Roles
        List<GrantedAuthority> auths = Arrays.asList(new SimpleGrantedAuthority(ADMIN));

        this.username = user.getEmail();
        this.password = user.getPassword();
        this.id = user.getId();
        this.authorities = auths;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}