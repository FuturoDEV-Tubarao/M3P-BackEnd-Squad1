package br.com.labfoods.config.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

import br.com.labfoods.model.User;

public class UserPrincipal {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    private UserPrincipal(User user){
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public static UserPrincipal create(User user){
        return new UserPrincipal(user);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}