package huce.edu.vn.appdocsach.services.impl.auth.users;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import huce.edu.vn.appdocsach.entities.User;
import lombok.Getter;

@Getter
public class AuthenticatedUser implements UserDetails {

    private User user;

    public AuthenticatedUser(User user) {
        this.user = user;
    }

    public static AuthenticatedUser of(User user) {
        return new AuthenticatedUser(user);
    }

    public Integer getUserId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
