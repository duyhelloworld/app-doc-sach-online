package huce.edu.vn.appdocsach.services.impl.auth;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.constants.AuthContants;
import huce.edu.vn.appdocsach.entities.RefreshToken;
import huce.edu.vn.appdocsach.services.abstracts.auth.IRefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class RefreshTokenService implements IRefreshTokenService {

    @Value("${application.auth.refresh.expirationDay}")
    private Integer expRefreshToken;

    // private final String REFRESH_TOKEN_COOKIE_KEY = "refresh_token";
    
    @Override
    public String getRefreshToken(HttpServletRequest request) {
        // Cookie cookie = WebUtils.getCookie(request, REFRESH_TOKEN_COOKIE_KEY);
        // return cookie == null ? null : cookie.getValue();\
        return request.getHeader(AuthContants.REFRESH_TOKEN_HEADER_NAME);
    }

    @Override
    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public boolean verifyExpiration(RefreshToken refreshToken) {
        return refreshToken.getExpireAt().isAfter(LocalDateTime.now());
    }

    @Override
    public LocalDateTime getNewExpiredFromNow() {
        return LocalDateTime.now().plusDays(expRefreshToken);
    }

    @Override
    public void cleanUp() {
        
    }
}
