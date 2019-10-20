package com.angkorsuntrix.demosynctrix.config;

import com.angkorsuntrix.demosynctrix.security.UserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {
        return new SpringSecurityAuditAwareImpl();
    }
}

class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        return Optional.ofNullable(principal.getId());

        // could consider to use this style
        /**
         *        return  Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
         *                 .filter(Authentication::isAuthenticated)
         *                 .filter(auth -> !(auth instanceof AnonymousAuthenticationToken))
         *                 .map(Authentication::getPrincipal)
         *                 .filter(UserPrincipal.class::isInstance)
         *                 .map(UserPrincipal.class::cast)
         *                 .map(UserPrincipal::getId);
         */
    }
}
