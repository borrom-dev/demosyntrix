package com.angkorsuntrix.demosynctrix.security;

import com.angkorsuntrix.demosynctrix.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserDetailsServiceImpl detailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) { // even if no text that's find validateToken should fail in that case itself
                Long userId = tokenProvider.getUserFromJwt(jwt);
                UserDetails userDetails = detailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) { // since u already have request as parameter better name this as "extractJwt" or "getJwt"

        final String bearerToken = request.getHeader("Authorization"); // use constant org.springframework.http.HttpHeaders.AUTHORIZATION

        /**        could be like this
         *         return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
         *                 .filter(header -> header.startsWith("Bearer "))
         *                 .map(header -> header.substring(7))
         *
         */

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) { // just startWith is enough
            return bearerToken.substring(7);
        }
        return null; // better do not use nulls at all (especially never return them
    }
}
