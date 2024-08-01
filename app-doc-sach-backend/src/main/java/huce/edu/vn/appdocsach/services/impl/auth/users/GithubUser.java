package huce.edu.vn.appdocsach.services.impl.auth.users;

import java.util.Map;

import huce.edu.vn.appdocsach.entities.TokenProvider;

public class GithubUser extends SupportedOAuth2User {

    public GithubUser(Map<String, Object> attributes) {
        super(attributes, TokenProvider.GITHUB);
    }

    @Override
    public String getEmail() {
        return getAttribute("email");
    }

    @Override
    @SuppressWarnings("null")
    public String getUsername() {
        return getAttribute("id").toString() + "_" + getAttribute("login");
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
