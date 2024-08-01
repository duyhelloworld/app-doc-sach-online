package huce.edu.vn.appdocsach.services.impl.auth.users;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import huce.edu.vn.appdocsach.entities.TokenProvider;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class SupportedOAuth2User implements OAuth2User {
   
    private Map<String, Object> attributes;
    private TokenProvider tokenProvider;
   
    public SupportedOAuth2User(Map<String, Object> attributes, TokenProvider tokenProvider) {
        this.attributes = attributes;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return tokenProvider.name();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    public abstract String getUsername();

    public abstract String getAvatar();

    public abstract String getEmail();

    public abstract String getFullname();
}
