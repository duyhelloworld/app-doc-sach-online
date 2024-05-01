package huce.edu.vn.appdocsach.services.impl.auth.users;

import java.util.Map;
import java.util.Optional;

import huce.edu.vn.appdocsach.entities.TokenProvider;

public class GithubUser extends AuthUser {
    public GithubUser(Map<String, Object> attributes) {
        super(attributes);
        this.setProvider(TokenProvider.GITHUB);
    }

    @Override
    public String getEmail() {
        return getAttribute("email");
    }

    @Override
    public String getUsername() {
        return Optional.ofNullable(getEmail())
            .orElse(getAttribute("login"));
    }

    @Override
    public String getFullname() {
        return getAttribute("name");
    }

    @Override
    public String getAvatar() {
        return getAttribute("avatar_url");
    }
}
