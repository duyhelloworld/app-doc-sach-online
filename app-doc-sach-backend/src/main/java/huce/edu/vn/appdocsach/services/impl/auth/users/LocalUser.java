package huce.edu.vn.appdocsach.services.impl.auth.users;

import huce.edu.vn.appdocsach.entities.TokenProvider;
import huce.edu.vn.appdocsach.entities.User;

public class LocalUser extends AuthUser {
    public LocalUser(User user) {
        super(user);
    }

    public static LocalUser getInstance(User user) {
        LocalUser authUser = new LocalUser(user);
        if (user == null) {
            return null;
        }
        authUser.setUsername(user.getUsername());
        authUser.setPassword(user.getPassword());
        authUser.setAvatar(user.getAvatar());
        authUser.setEmail(user.getEmail());
        authUser.setRole(user.getRole());
        authUser.setFullname(user.getFullname());
        authUser.setProvider(TokenProvider.LOCAL);
        return authUser;
    }
}
