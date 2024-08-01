package huce.edu.vn.appdocsach.services.impl.auth;

import java.time.LocalDateTime;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import huce.edu.vn.appdocsach.entities.Role;
import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAccessTokenService;
import huce.edu.vn.appdocsach.services.impl.auth.users.ClaimType;
import huce.edu.vn.appdocsach.services.impl.auth.users.ClaimValues;
import huce.edu.vn.appdocsach.utils.DateTimeUtils;
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
public class AccessTokenService implements IAccessTokenService {

    @Value("${application.auth.jwt.secret}")
    private String secretKey;

    @Value("${application.auth.jwt.expirationMin}")
    private int expirationMin;

    @Value("${application.auth.jwt.issuer}")
    private String issuer;

    @Override
    public String getAccessToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (StringUtils.hasText(header) 
            && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    @Override
    public String buildAccessToken(User user) {
        LocalDateTime now = LocalDateTime.now();
        return Jwts.builder()
                .claim(ClaimType.USERNAME.name(), user.getUsername())
                .claim(ClaimType.EMAIL.name(), user.getEmail())
                .claim(ClaimType.ROLE.name(), user.getRole())
                .issuer(issuer)
                .issuedAt(DateTimeUtils.getDate(now))
                .expiration(DateTimeUtils.getDate(now.plusMinutes(expirationMin)))
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact(); 
    }

    @Override
    public boolean isValidAccessToken(String token) {
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
    public ClaimValues extractClaimValues(String token) {
        Claims claims = extractAllClaims(token);
        return ClaimValues.builder()
            .username(claims.get(ClaimType.USERNAME.name(), String.class))
            .email(claims.get(ClaimType.EMAIL.name(), String.class))
            .role(Role.valueOf(claims.get(ClaimType.ROLE.name(), String.class)))
            .build();
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

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }        
}