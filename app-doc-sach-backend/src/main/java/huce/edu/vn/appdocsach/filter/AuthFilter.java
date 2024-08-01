package huce.edu.vn.appdocsach.filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import huce.edu.vn.appdocsach.entities.User;
import huce.edu.vn.appdocsach.enums.ResponseCode;
import huce.edu.vn.appdocsach.exception.AppException;
import huce.edu.vn.appdocsach.repositories.database.UserRepo;
import huce.edu.vn.appdocsach.services.abstracts.auth.IAccessTokenService;
import huce.edu.vn.appdocsach.services.abstracts.auth.IRefreshTokenService;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import huce.edu.vn.appdocsach.utils.NamingUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final IAccessTokenService accessTokenService;

    private final IRefreshTokenService refreshTokenService;

    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
                
        String token = accessTokenService.getAccessToken(request);
        
        if (token != null && accessTokenService.isValidAccessToken(token)) {
            log.info("Start doFilterInternal with input : {}", NamingUtil.hideLetter(token));
            
            String username = accessTokenService.extractClaimValues(token).getUsername();            
            User user = userRepo.findByUsername(username).orElse(null);
            
            if (user != null && refreshTokenService.verifyExpiration(user.getRefreshToken())) {
            
                UserDetails userDetails = AuthenticatedUser.of(user);
                var authenticationToken = 
                    new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            
            } else {
                throw new AppException(ResponseCode.AUTH_SESSION_INVALID);
            }
        }
        filterChain.doFilter(request, response);
    }
}
