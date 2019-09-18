package com.angkorsuntrix.demosynctrix;

import com.angkorsuntrix.demosynctrix.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            Authentication auth = AuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (ExpiredJwtException e) {
            final String message = "token expired";
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
        }
        chain.doFilter(request, response);
    }
}
