package huce.edu.vn.appdocsach.services.abstracts.auth;

import huce.edu.vn.appdocsach.entities.User;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtService {
    String getTokenFromRequest(HttpServletRequest request);

    String buildToken(User user);

    boolean isValidToken(String token);

    String getUsername(String token);
}
