package huce.edu.vn.appdocsach.services.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.services.auth.users.AuthUser;
import huce.edu.vn.appdocsach.services.auth.users.ClaimType;
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
public class JwtService {

    @Value("${application.jwt.secret}")
    private String secretKey;

    @Value("${application.jwt.expiration}")
    private int expiration;

    @Value("${application.jwt.issuer}")
    private String issuer;

    public String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public String buildToken(AuthUser authUser) {
        return buildToken(authUser.getUsername(), authUser.getEmail(), authUser.getRole());
    }

    public String buildToken(User user) {
        return buildToken(user.getUsername(), user.getEmail(), user.getRole());
    }

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

    public String getUsername(String token) {
        return getStringValue(extractAllClaims(token).get(ClaimType.USERNAME.name()));
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
                .signWith(getSignInKey(), Jwts.SIG.HS512)
                .compact();
    }

    private String getStringValue(Object obj) {
        return obj != null ? String.valueOf(obj) : "";
    }

    private Date getDate(LocalDateTime dateTime) {
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
