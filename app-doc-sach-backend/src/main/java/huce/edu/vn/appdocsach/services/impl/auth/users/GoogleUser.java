package huce.edu.vn.appdocsach.services.impl.auth.users;

import java.util.Map;

import huce.edu.vn.appdocsach.entities.TokenProvider;

public class GoogleUser extends AuthUser {
    public GoogleUser(Map<String, Object> attributes) {
        super(attributes);
        this.setProvider(TokenProvider.GOOGLE);
    }

    @Override
    public String getEmail() {
        return getAttribute("email");
    }

    @Override
    public String getUsername() {
        return getAttribute("email");
    }

    @Override
    public String getFullname() {
        return getAttribute("name");
    }

    @Override
    public String getAvatar() {
        return getAttribute("picture");
    }
}
