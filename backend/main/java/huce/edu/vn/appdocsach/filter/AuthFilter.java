package huce.edu.vn.appdocsach.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import huce.edu.vn.appdocsach.services.auth.AuthUserService;
import huce.edu.vn.appdocsach.services.auth.JwtService;
import huce.edu.vn.appdocsach.utils.AppLogger;
import huce.edu.vn.appdocsach.utils.NamingUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private JwtService jwtService;

    private AppLogger<AuthFilter> logger = new AppLogger<>(AuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        String token = jwtService.getTokenFromRequest(request);
        
        if (token != null && jwtService.isValidToken(token) 
            && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.onStart(Thread.currentThread(), NamingUtil.hideLetter(token));
            String username = jwtService.getUsername(token);            
            UserDetails userDetails = authUserService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
