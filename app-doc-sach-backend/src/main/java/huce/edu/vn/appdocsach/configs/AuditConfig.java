package huce.edu.vn.appdocsach.configs;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditor")
public class AuditConfig implements AuditorAware<String> {

    @Bean
    @Primary
    AuditorAware<String> auditor() {
        return new AuditConfig();
    }
    
    @NonNull
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(extractAuditor());
    }

    public static String extractAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        try {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
            return authenticatedUser.getUsername();
        } catch (ClassCastException e) {
            log.error("Error when cast " + authentication.getPrincipal() + " to AuthUser", e);
            return null;
        }
    }
}
