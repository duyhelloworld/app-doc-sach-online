package huce.edu.vn.appdocsach.services.auth.users;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AuthUser implements UserDetails, OAuth2User {
    private Map<String, Object> attributes;
    private User user;

    public AuthUser(User user) {
        this.user = user;
    }
    
    public AuthUser(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().name()));
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

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return user.getProvider().name();
    }

    public void setUsername(String username) {
        if (this.user != null) {
            this.user.setUsername(username);
        }
    }

    public void setEmail(String email) {
        if (user != null) {
            this.user.setEmail(email);
        }
    }

    public void setFullname(String fullname) {
        if (user != null) {
            this.user.setFullname(fullname);
        }
    }

    public void setAvatar(String avatar) {
        if (user != null) {
            this.user.setAvatar(avatar);
        }
    }

    public void setProvider(TokenProvider provider) {
        if (user != null) {
            this.user.setProvider(provider);
        }
    }

    public void setRole(Role role) {
        if (user != null) {
            this.user.setRole(role);
        }
    }

    public void setPassword(String password) {
        if (user != null) {
            this.user.setPassword(password);
        }
    }

    public Role getRole() {
        return user.getRole();
    }

    public TokenProvider getProvider() {
        return user.getProvider();
    }

    // Oauth2
    public String getEmail() {
        return user.getEmail();
    }

    public String getFullname() {
        return user.getFullname();
    }

    public String getAvatar() {
        return user.getAvatar();
    }
}
