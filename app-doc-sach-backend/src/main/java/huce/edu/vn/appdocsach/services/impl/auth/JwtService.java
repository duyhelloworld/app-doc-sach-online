package huce.edu.vn.appdocsach.services.impl.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.services.abstracts.auth.IJwtService;
import huce.edu.vn.appdocsach.services.impl.auth.users.ClaimType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtService implements IJwtService {

    @Value("${application.jwt.secret}")
    private String secretKey;

    @Value("${application.jwt.expiration}")
    private int expiration;

    @Value("${application.jwt.issuer}")
    private String issuer;

    @Override
    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    public String buildToken(User user) {
        return buildToken(user.getUsername(), user.getEmail(), user.getRole());
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            getParser().parseSignedClaims(token);
            return true;
        } catch (UnsupportedJwtException e) {
            log.error("Token type invalid : " + e.getMessage());
        } catch (JwtException e) {
            log.error("Token invalid : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("Jwt is null / whitespace");
        }
        return false;
    }

    @Override
    public String getUsername(String token) {
        Object obj = extractAllClaims(token).get(ClaimType.USERNAME.name());
        if (obj == null) {
            throw new AppException(ResponseCode.USERNAME_NOT_FOUND);
        }
        return obj.toString();
    }

    private JwtParser getParser() {
        return Jwts
            .parser()
            .verifyWith(getSignInKey())
            .requireIssuer(issuer)
            .build();
    }

    private Claims extractAllClaims(String token) {
        try {
            return getParser()
                .parseSignedClaims(token)
                .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    private String buildToken(String username, String email, Role role) {
        return Jwts
                .builder()
                .claim(ClaimType.USERNAME.name(), username)
                .claim(ClaimType.EMAIL.name(), email)
                .claim(ClaimType.ROLE.name(), role)
                .issuer(issuer)
                .issuedAt(getDate(LocalDateTime.now()))
                .expiration(getDate(LocalDateTime.now().plusDays(expiration)))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    private Date getDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
